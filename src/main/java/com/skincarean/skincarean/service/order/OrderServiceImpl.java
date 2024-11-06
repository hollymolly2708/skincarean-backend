package com.skincarean.skincarean.service.order;

import com.skincarean.skincarean.entity.*;
import com.skincarean.skincarean.model.order.request.CartOrderRequest;
import com.skincarean.skincarean.model.order.request.DirectlyOrderRequest;
import com.skincarean.skincarean.model.order.response.OrderItemResponse;
import com.skincarean.skincarean.model.order.response.OrderProductResponse;
import com.skincarean.skincarean.model.order.response.OrderProductVariantResponse;
import com.skincarean.skincarean.model.order.response.OrderResponse;
import com.skincarean.skincarean.model.payment_process.response.PaymentProcessResponse;
import com.skincarean.skincarean.repository.*;
import com.skincarean.skincarean.utils.Utilities;
import com.skincarean.skincarean.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private PaymentProcessRepository paymentProcessRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Override
    @Transactional
    public Map<String, Object> directlyCheckout(User user, DirectlyOrderRequest request) {
        // Validasi input
        validationService.validate(request);

        // Mencari produk dan metode pembayaran
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));

        ProductVariant productVariant = productVariantRepository.findByIdAndProductId(request.getProductVariantId(), request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Varian dari produk yang dipilih tidak ditemukan"));


        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));


        // Cek ketersediaan stok
        if (productVariant.getStok() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stok kosong");
        }
        if (productVariant.getStok() < request.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jumlah quantity order melebihi dari jumlah stok yang tersedia");
        }

        // Membuat order baru
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUser(user);
        order.setOrderStatus("Menunggu Pembayaran");
        order.setDescription(request.getDescription());
        order.setShippingAddress(request.getShippingAddress());
        order.setShippingCost(BigDecimal.valueOf(20000));
        order.setTax(BigDecimal.valueOf(0.05)); // Pajak diatur ke 5%
        orderRepository.save(order);

        // Membuat order item
        Long quantity = request.getQuantity();
        BigDecimal price = productVariant.getPrice();
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

        OrderItem orderItem = new OrderItem();
        orderItem.setId(UUID.randomUUID().toString());
        orderItem.setTotalPrice(totalPrice);
        orderItem.setQuantity(quantity);
        orderItem.setProductVariant(productVariant);
        orderItem.setOrder(order);
        orderItem.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        orderItem.setExpiredAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        orderItem.setProduct(product);
        orderItemRepository.save(orderItem);

        // Menghitung total order termasuk pajak
        BigDecimal taxAmount = totalPrice.multiply(order.getTax());
        order.setTotalPrice(totalPrice.add(taxAmount).add(order.getShippingCost()));
        order.setTax(taxAmount);
        orderRepository.save(order);

        // Membuat proses pembayaran jika metode pembayaran ada
        if (request.getPaymentMethodId() != null) {
            PaymentProcess paymentProcess = new PaymentProcess();
            paymentProcess.setOrder(order);
            paymentProcess.setPaymentStatus("Belum dibayar");
            paymentProcess.setPaymentMethod(paymentMethod);
            paymentProcess.setPaymentCode(Utilities.generatePaymentCode(12));
            paymentProcessRepository.save(paymentProcess);
        }

        // Mengembalikan respons dengan order ID
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", order.getId());
        return response;
    }

    @Override
    @Transactional
    public Map<String, Object> checkoutFromCart(User user, CartOrderRequest cartOrderRequest) {

        validationService.validate(cartOrderRequest);


        PaymentMethod paymentMethod = paymentMethodRepository.findById(cartOrderRequest.getPaymentMethodId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));

        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));

        List<CartItem> cartItems = cartItemRepository.findByCartAndIsActive(cart, true);

        if (cartItems == null || cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tidak ada item di dalam keranjang");
        }


        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setUser(user);
        order.setShippingAddress(cartOrderRequest.getShippingAddress());
        order.setShippingCost(BigDecimal.valueOf(20000));
        order.setDescription(cartOrderRequest.getDescription());
        order.setOrderStatus("Menunggu pembayaran");
        order.setTax(BigDecimal.ZERO);
        orderRepository.save(order);


        BigDecimal overallTotal = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(UUID.randomUUID().toString());
            orderItem.setOrder(order);
            orderItem.setCreatedAt(Utilities.changeFormatToTimeStamp());
            orderItem.setExpiredAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000));

            BigDecimal itemSubTotal = cartItem.getTotal();
            overallTotal = overallTotal.add(itemSubTotal);

            orderItem.setProductVariant(cartItem.getProductVariant());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(itemSubTotal);

            orderItemRepository.save(orderItem);
        }


        BigDecimal taxRate = BigDecimal.valueOf(0.05);
        BigDecimal taxAmount = overallTotal.multiply(taxRate);

        // Mengupdate total order
        order.setTotalPrice(overallTotal.add(taxAmount).add(order.getShippingCost()));
        order.setTax(taxAmount);
        orderRepository.save(order);

        // Membuat payment process jika metode pembayaran ada
        if (cartOrderRequest.getPaymentMethodId() != null) {
            PaymentProcess paymentProcess = new PaymentProcess();
            paymentProcess.setOrder(order);
            paymentProcess.setPaymentCode(Utilities.generatePaymentCode(12));
            paymentProcess.setPaymentMethod(paymentMethod);
            paymentProcess.setPaymentStatus("Belum dibayar");
            paymentProcessRepository.save(paymentProcess);
        }

        // Mempersiapkan response
        Map<String, Object> response = new HashMap<>();
        response.put("orderId", order.getId());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders(User user) {
        List<Order> orders = orderRepository.findByUserUsernameUser(user.getUsernameUser());

        return orders.stream().map(order ->
                {
                    PaymentMethod paymentMethod = paymentMethodRepository.findById(order.getPaymentProcess().getPaymentMethod().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));
                    return OrderResponse.builder()
                            .orderId(order.getId())
                            .orderStatus(order.getOrderStatus())

                            .payment(

                                    PaymentProcessResponse.builder()
                                            .paymentStatus(order.getPaymentProcess().getPaymentStatus())
                                            .paymentCode(order.getPaymentProcess().getPaymentCode())
                                            .paidDate(order.getPaymentProcess().getPaidDate())
                                            .paymentMethodId(order.getPaymentProcess().getId())
                                            .paymentMethodName(paymentMethod.getName())
                                            .totalPaid(order.getPaymentProcess().getTotalPaid())
                                            .build())
                            .tax(order.getTax())
                            .shippingAddress(order.getShippingAddress())
                            .description(order.getDescription())
                            .shippingCost(order.getShippingCost())
                            .finalPrice(order.getTotalPrice())


                            .orderItems(order.getOrderItems().stream().map(
                                            orderItem ->
                                                    OrderItemResponse
                                                            .builder()
                                                            .product(OrderProductResponse.builder()
                                                                    .productId(orderItem.getProduct().getId())
                                                                    .brandName(orderItem.getProduct().getName())
                                                                    .productName(orderItem.getProduct().getName())
                                                                    .categoryName(orderItem.getProduct().getCategoryItem().getName())
                                                                    .thumbnailImage(orderItem.getProduct().getThumbnailImage())
                                                                    .build())
                                                            .price(orderItem.getTotalPrice())
                                                            .productVariant(OrderProductVariantResponse.builder()
                                                                    .id(orderItem.getProductVariant().getId())
                                                                    .discount(orderItem.getProductVariant().getDiscount())
                                                                    .originalPrice(orderItem.getProductVariant().getOriginalPrice())
                                                                    .price(orderItem.getProductVariant().getPrice())
                                                                    .thumbnailImageVariant(orderItem.getProductVariant().getThumbnailVariantImage())
                                                                    .size(orderItem.getProductVariant().getSize())
                                                                    .build())
                                                            .quantity(orderItem.getQuantity())
                                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                })
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllPendingOrders(User user) {
        List<Order> orders = orderRepository.findByUserUsernameUserAndOrderStatus(user.getUsernameUser(), "Menunggu pembayaran");

        return orders.stream().map(order -> OrderResponse.builder()
                        .orderId(order.getId())
                        .orderStatus(order.getOrderStatus())
                        .payment(PaymentProcessResponse.builder()
                                .paymentStatus(order.getPaymentProcess().getPaymentStatus())
                                .paymentCode(order.getPaymentProcess().getPaymentCode())
                                .paidDate(order.getPaymentProcess().getPaidDate())
                                .paymentMethodId(order.getPaymentProcess().getId())
                                .totalPaid(order.getPaymentProcess().getTotalPaid())
                                .build())
                        .tax(order.getTax())
                        .shippingAddress(order.getShippingAddress())
                        .description(order.getDescription())
                        .shippingCost(order.getShippingCost())
                        .finalPrice(order.getTotalPrice())

                        .orderItems(order.getOrderItems().stream().map(
                                        orderItem ->
                                                OrderItemResponse
                                                        .builder()
                                                        .product(OrderProductResponse.builder()
                                                                .productId(orderItem.getProduct().getId())
                                                                .brandName(orderItem.getProduct().getName())
                                                                .productName(orderItem.getProduct().getName())
                                                                .categoryName(orderItem.getProduct().getCategoryItem().getName())
                                                                .thumbnailImage(orderItem.getProduct().getThumbnailImage())
                                                                .build())
                                                        .price(orderItem.getTotalPrice())
                                                        .productVariant(OrderProductVariantResponse.builder()
                                                                .size(orderItem.getProductVariant().getSize())
                                                                .price(orderItem.getProductVariant().getPrice())
                                                                .id(orderItem.getProductVariant().getId())
                                                                .thumbnailImageVariant(orderItem.getProductVariant().getThumbnailVariantImage())
                                                                .originalPrice(orderItem.getProductVariant().getOriginalPrice())
                                                                .discount(orderItem.getProductVariant().getDiscount())
                                                                .build())
                                                        .quantity(orderItem.getQuantity())
                                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .toList();

    }

    @Override
    @Transactional
    public void cancelOrder(User user, String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order tidak ditemukan"));
        if (order.getPaymentProcess().getPaymentStatus().equals("Lunas") || order.getOrderStatus().equals("Selesai")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gagal membatalkan, order telah selesai");
        }

        order.getPaymentProcess().setPaymentStatus("Dibatalkan");
        order.getPaymentProcess().setPaymentCode(null);
        order.getPaymentProcess().setPaidDate(null);
        order.getPaymentProcess().setTotalPaid(null);
        order.setOrderStatus("Dibatalkan");
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllCanceledOrders(User user) {
        List<Order> orders = orderRepository.findByUserUsernameUserAndOrderStatus(user.getUsernameUser(), "Dibatalkan");
        return orders.stream().map(order -> OrderResponse.builder()
                        .orderId(order.getId())
                        .orderStatus(order.getOrderStatus())
                        .payment(PaymentProcessResponse.builder()
                                .paymentStatus(order.getPaymentProcess().getPaymentStatus())
                                .paymentCode(order.getPaymentProcess().getPaymentCode())
                                .paidDate(order.getPaymentProcess().getPaidDate())
                                .paymentMethodId(order.getPaymentProcess().getId())
                                .totalPaid(order.getPaymentProcess().getTotalPaid())
                                .build())
                        .tax(order.getTax())
                        .shippingAddress(order.getShippingAddress())
                        .description(order.getDescription())
                        .shippingCost(order.getShippingCost())
                        .finalPrice(order.getTotalPrice())

                        .orderItems(order.getOrderItems().stream().map(
                                        orderItem ->
                                                OrderItemResponse
                                                        .builder()
                                                        .product(OrderProductResponse.builder()
                                                                .productId(orderItem.getProduct().getId())
                                                                .brandName(orderItem.getProduct().getName())
                                                                .productName(orderItem.getProduct().getName())
                                                                .categoryName(orderItem.getProduct().getCategoryItem().getName())
                                                                .thumbnailImage(orderItem.getProduct().getThumbnailImage())
                                                                .build())
                                                        .price(orderItem.getTotalPrice())
                                                        .productVariant(OrderProductVariantResponse.builder()
                                                                .size(orderItem.getProductVariant().getSize())
                                                                .price(orderItem.getProductVariant().getPrice())
                                                                .thumbnailImageVariant(orderItem.getProductVariant().getThumbnailVariantImage())
                                                                .discount(orderItem.getProductVariant().getDiscount())
                                                                .originalPrice(orderItem.getProductVariant().getOriginalPrice())
                                                                .id(orderItem.getProductVariant().getId())
                                                                .build())
                                                        .quantity(orderItem.getQuantity())
                                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllCompletedOrders(User user) {
        List<Order> orders = orderRepository.findByUserUsernameUserAndOrderStatus(user.getUsernameUser(), "Selesai");

        return orders.stream().map(order -> OrderResponse.builder()
                        .orderId(order.getId())
                        .orderStatus(order.getOrderStatus())
                        .payment(PaymentProcessResponse.builder()
                                .paymentStatus(order.getPaymentProcess().getPaymentStatus())
                                .paymentCode(order.getPaymentProcess().getPaymentCode())
                                .paidDate(order.getPaymentProcess().getPaidDate())
                                .paymentMethodId(order.getPaymentProcess().getId())
                                .totalPaid(order.getPaymentProcess().getTotalPaid())
                                .build())
                        .tax(order.getTax())
                        .shippingAddress(order.getShippingAddress())
                        .description(order.getDescription())
                        .shippingCost(order.getShippingCost())
                        .finalPrice(order.getTotalPrice())

                        .orderItems(order.getOrderItems().stream().map(
                                        orderItem ->
                                                OrderItemResponse
                                                        .builder()
                                                        .product(OrderProductResponse.builder()
                                                                .productId(orderItem.getProduct().getId())
                                                                .brandName(orderItem.getProduct().getName())
                                                                .productName(orderItem.getProduct().getName())
                                                                .categoryName(orderItem.getProduct().getCategoryItem().getName())
                                                                .thumbnailImage(orderItem.getProduct().getThumbnailImage())
                                                                .build())
                                                        .price(orderItem.getTotalPrice())
                                                        .productVariant(OrderProductVariantResponse.builder()
                                                                .size(orderItem.getProductVariant().getSize())
                                                                .thumbnailImageVariant(orderItem.getProductVariant().getThumbnailVariantImage())
                                                                .price(orderItem.getProductVariant().getPrice())
                                                                .discount(orderItem.getProductVariant().getDiscount())
                                                                .originalPrice(orderItem.getProductVariant().getOriginalPrice())
                                                                .id(orderItem.getProductVariant().getId())
                                                                .build())
                                                        .quantity(orderItem.getQuantity())
                                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .toList();

    }

    @Override
    @Transactional
    public void deleteOrder(User user, String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional
    public OrderResponse getDetailOrder(User user, String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order tidak ditemukan"));
        PaymentMethod paymentMethod = paymentMethodRepository.findById(order.getPaymentProcess().getPaymentMethod().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method tidak ditemukan"));

        PaymentProcess paymentProcess = order.getPaymentProcess();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .payment(PaymentProcessResponse.builder()
                        .totalPaid(paymentProcess.getTotalPaid())
                        .paymentMethodId(paymentProcess.getId())
                        .paidDate(paymentProcess.getPaidDate())
                        .paymentMethodName(paymentMethod.getName())
                        .paymentCode(paymentProcess.getPaymentCode())
                        .paymentStatus(paymentProcess.getPaymentStatus())
                        .build())
                .finalPrice(order.getTotalPrice())
                .tax(order.getTax())

                .shippingCost(order.getShippingCost())
                .shippingAddress(order.getShippingAddress())
                .description(order.getDescription())
                .orderItems(order.getOrderItems().stream().map(orderItem -> {

                    Product product = orderItem.getProduct();
                    return OrderItemResponse.builder()
                            .price(orderItem.getTotalPrice())
                            .productVariant(OrderProductVariantResponse.builder()
                                    .size(orderItem.getProductVariant().getSize())
                                    .price(orderItem.getProductVariant().getPrice())
                                    .discount(orderItem.getProductVariant().getDiscount())
                                    .thumbnailImageVariant(orderItem.getProductVariant().getThumbnailVariantImage())
                                    .originalPrice(orderItem.getProductVariant().getOriginalPrice())
                                    .id(orderItem.getProductVariant().getId())
                                    .build())
                            .quantity(orderItem.getQuantity())
                            .product(OrderProductResponse.builder()
                                    .productId(product.getId())
                                    .productName(product.getName())
                                    .brandName(product.getBrand().getName())
                                    .thumbnailImage(orderItem.getProductVariant().getThumbnailVariantImage())
                                    .categoryName(product.getCategoryItem().getName())
                                    .build())


                            .build();
                }).toList())

                .build();

    }


}
