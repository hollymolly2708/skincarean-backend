package com.skincareMall.skincareMall.service.order;

import com.skincareMall.skincareMall.entity.*;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.model.order.request.CartOrderRequest;
import com.skincareMall.skincareMall.model.order.request.DirectlyOrderRequest;
import com.skincareMall.skincareMall.model.order.response.OrderItemResponse;
import com.skincareMall.skincareMall.model.order.response.OrderResponse;
import com.skincareMall.skincareMall.model.payment_process.response.PaymentProcessResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.repository.*;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.aspectj.weaver.ast.Or;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderService {
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


    @Transactional
    public void directlyCheckout(User user, DirectlyOrderRequest request) {
        validationService.validate(request);

        if (Objects.nonNull(request)) {
            Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));
            PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));
            if (product.getStok() == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stok kosong");
            } else if (product.getStok() < request.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jumlah quantity order melebihi dari jumlah stok yang tersedia");
            } else {
                Long quantity = request.getQuantity();
                BigDecimal price = product.getPrice();


                Order order = new Order();
                order.setUser(user);
                order.setId(UUID.randomUUID().toString());
                order.setOrderStatus("Menunggu Pembayaran");
                order.setDescription(request.getDescription());
                order.setTax(BigDecimal.valueOf(0.05));
                order.setShippingAddress(request.getShippingAddress());
                order.setShippingCost(BigDecimal.valueOf(20000));
                orderRepository.save(order);


                OrderItem orderItem = new OrderItem();
                orderItem.setId(UUID.randomUUID().toString());
                orderItem.setTotalPrice(price.multiply(BigDecimal.valueOf(quantity)));
                orderItem.setQuantity(request.getQuantity());
                orderItem.setOrder(order);
                orderItem.setExpiredAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
                orderItem.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
                orderItem.setProduct(product);
                orderItemRepository.save(orderItem);


                BigDecimal tax = BigDecimal.valueOf(0.05);
                BigDecimal taxAmount = orderItem.getTotalPrice().multiply(tax);
                order.setTotalPrice(orderItem.getTotalPrice().add(taxAmount).add(order.getShippingCost()));
                order.setTax(taxAmount);
                orderRepository.save(order);

                if (request.getPaymentMethodId() != null) {
                    PaymentProcess paymentProcess = new PaymentProcess();
                    paymentProcess.setOrder(order);
                    paymentProcess.setPaymentStatus("Belum dibayar");
                    paymentProcess.setPaymentMethod(paymentMethod);
                    paymentProcess.setPaymentCode(Utilities.generatePaymentCode(12));
                    paymentProcessRepository.save(paymentProcess);
                }
            }

        }
    }

    @Transactional
    public void checkoutFromCart(User user, CartOrderRequest cartOrderRequest) {

        validationService.validate(cartOrderRequest);

        PaymentMethod paymentMethod = paymentMethodRepository.findById(cartOrderRequest.getPaymentMethodId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));


        List<Cart> carts = cartRepository.findByUserUsernameUser(user.getUsernameUser());


        if (Objects.nonNull(carts) && !carts.isEmpty()) {


            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setUser(user);
            order.setTax(BigDecimal.valueOf(0));
            order.setShippingAddress(cartOrderRequest.getShippingAddress());
            order.setShippingCost(BigDecimal.valueOf(20000));
            order.setDescription(cartOrderRequest.getDescription());
            order.setOrderStatus("Menunggu pembayaran");
            orderRepository.save(order);

            AtomicReference<BigDecimal> overallTotal = new AtomicReference<>(BigDecimal.ZERO);


            carts.forEach(cart -> {
                OrderItem orderItem = new OrderItem();

                orderItem.setOrder(order);
                orderItem.setId(UUID.randomUUID().toString());
                orderItem.setCreatedAt(Utilities.changeFormatToTimeStamp());
                orderItem.setExpiredAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
                BigDecimal itemSubTotal = cart.getTotal();

                overallTotal.updateAndGet(currentTotal -> currentTotal.add(itemSubTotal));
                orderItem.setProduct(cart.getProduct());
                orderItem.setQuantity(cart.getQuantity());
                orderItem.setTotalPrice(itemSubTotal);


                orderItemRepository.save(orderItem);


            });


            BigDecimal tax = BigDecimal.valueOf(0.05);
            BigDecimal taxAmount = overallTotal.get().multiply(tax);

            order.setTotalPrice(overallTotal.get().add(taxAmount).add(order.getShippingCost()));
            order.setTax(taxAmount);
            orderRepository.save(order);

            if (cartOrderRequest.getPaymentMethodId() != null) {

                PaymentProcess paymentProcess = new PaymentProcess();
                paymentProcess.setOrder(order);
                paymentProcess.setPaymentCode(Utilities.generatePaymentCode(12));
                paymentProcess.setPaymentMethod(paymentMethod);
                paymentProcess.setPaymentStatus("Belum dibayar");
                paymentProcessRepository.save(paymentProcess);
            }


        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "tidak ada item didalam keranjang");
        }


    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders(User user) {
        List<Order> orders = orderRepository.findByUserUsernameUser(user.getUsernameUser());

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
                                                        .createdAt(orderItem.getCreatedAt())
                                                        .product(ProductResponse.builder()
                                                                .productId(orderItem.getProduct().getId())
                                                                .ingredient(orderItem.getProduct().getIngredient())
                                                                .originalPrice(orderItem.getProduct().getOriginalPrice())
                                                                .brandName(orderItem.getProduct().getName())
                                                                .productName(orderItem.getProduct().getName())
                                                                .stok(orderItem.getProduct().getStok())
                                                                .categoryName(orderItem.getProduct().getCategoryItem().getName())
                                                                .price(orderItem.getProduct().getPrice())
                                                                .productDescription(orderItem.getProduct().getDescription())
                                                                .size(orderItem.getProduct().getSize())
                                                                .isPromo(orderItem.getProduct().getIsPromo())
                                                                .ingredient(orderItem.getProduct().getIngredient())
                                                                .isPopularProduct(orderItem.getProduct().getIsPopularProduct())
                                                                .thumbnailImage(orderItem.getProduct().getThumbnailImage())
                                                                .bpomCode(orderItem.getProduct().getBpomCode())
                                                                .discount(orderItem.getProduct().getDiscount())
                                                                .build())
                                                        .expiredAt(orderItem.getExpiredAt())
                                                        .price(orderItem.getTotalPrice())
                                                        .quantity(orderItem.getQuantity())
                                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .toList();

    }


    @Transactional
    public void deleteOrder(User user, String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public OrderResponse getDetailOrder(User user, String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order tidak ditemukan"));

        PaymentProcess paymentProcess = order.getPaymentProcess();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .payment(PaymentProcessResponse.builder()
                        .totalPaid(paymentProcess.getTotalPaid())
                        .paymentMethodId(paymentProcess.getId())
                        .paidDate(paymentProcess.getPaidDate())
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
                            .expiredAt(orderItem.getExpiredAt())
                            .createdAt(orderItem.getCreatedAt())
                            .quantity(orderItem.getQuantity())
                            .product(ProductResponse.builder()
                                    .productName(product.getName())
                                    .size(product.getSize())
                                    .isPromo(product.getIsPromo())
                                    .price(product.getPrice())
                                    .stok(product.getStok())
                                    .isPopularProduct(product.getIsPopularProduct())
                                    .brandName(product.getBrand().getName())
                                    .thumbnailImage(product.getThumbnailImage())
                                    .ingredient(product.getIngredient())
                                    .discount(product.getDiscount())
                                    .bpomCode(product.getBpomCode())
                                    .categoryName(product.getCategoryItem().getName())
                                    .originalPrice(product.getOriginalPrice())
                                    .productDescription(product.getDescription())
                                    .build())
                            .build();
                }).toList())

                .build();

    }


//
//    @Transactional
//    public void checkoutFromCart(User user, CartOrderRequest cartOrderRequest) {
//        validationService.validate(cartOrderRequest);
//        Cart cart = cartRepository.find(user.getUsernameUser()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));
//
//        System.out.println(cart.getId());
//        System.out.println(user.getUsernameUser());
//        PaymentMethod paymentMethod = paymentMethodRepository.findById(cartOrderRequest.getPaymentMethodId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak tersedia"));
//
//
//        List<Cart> carts = cartRepository.findByCartId(cart.getId());
//
//        System.out.println(cartRepository.findByCartId(1L));
//
//        BigDecimal tax = cartOrderRequest.getTax();
//        BigDecimal shippingCost = cartOrderRequest.getShippingCost();

//        List<Order> orders = cartItems.stream().map(cartItem -> {
//            Order order = new Order();
//            order.setId(UUID.randomUUID().toString());
//            order.setDescription(cartOrderRequest.getDescription());
//            order.setOrderStatus("Menunggu Pembayaran");
//            order.setShippingCost(cartOrderRequest.getShippingCost());
//            order.setShippingAddress(cartOrderRequest.getShippingAddress());
//            order.setTotalPrice(cartItem
//                    .getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()).add(shippingCost).add(tax)));
//            order.setTax(cartOrderRequest.getTax());
//            order.setUser(user);
//            order.setCreatedAt(Utilities.changeFormatToTimeStamp());
//            order.setExpiredAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
//            order.setProduct(cartItem.getProduct());
//            order.setOrderStatus("Menunggu pembayaran");
//            orderRepository.save(order);
//
//            if (cartOrderRequest.getPaymentMethodId() != null) {
//                PaymentProcess paymentProcess = new PaymentProcess();
//                paymentProcess.setOrder(order);
//                paymentProcess.setPaymentStatus("Belum dibayar");
//                paymentProcess.setPaymentCode(Utilities.generatePaymentCode(12));
//                paymentProcess.setPaymentMethod(paymentMethod);
//                paymentProcessRepository.save(paymentProcess);
//            }
//
//
//            return order;
//        }).toList();
//
//
//        orders.stream().map(
//                order -> {
//                    return OrderResponse.builder().orderStatus(order.getOrderStatus())
//                            .orderId(order.getId())
//                            .expiredAt(order.getExpiredAt())
//                            .createdAt(order.getCreatedAt())
//                            .description(order.getDescription())
//                            .shippingAddress(order.getShippingAddress())
//                            .tax(order.getTax())
//                            .totalPrice(order.getTotalPrice())
//                            .shippingCost(order.getShippingCost())
//                            .quantity(order.getQuantity())
//                            .product(ProductResponse.builder()
//                                    .productId(order.getProduct().getId())
//                                    .price(order.getProduct().getPrice())
//                                    .productDescription(order.getProduct().getDescription())
//                                    .discount(order.getProduct().getDiscount())
//                                    .ingredient(order.getProduct().getIngredient())
//                                    .categoryName(order.getProduct().getCategoryItem().getName())
//                                    .bpomCode(order.getProduct().getBpomCode())
//                                    .brandName(order.getProduct().getBrand().getName())
//                                    .stok(order.getProduct().getStok())
//                                    .isPopularProduct(order.getProduct().getIsPopularProduct())
//                                    .size(order.getProduct().getSize())
//                                    .isPromo(order.getProduct().getIsPromo())
//                                    .originalPrice(order.getProduct().getOriginalPrice())
//                                    .thumbnailImage(order.getProduct().getThumbnailImage())
//                                    .productName(order.getProduct().getName())
//                                    .build())
//                            .payment(PaymentProcessResponse.builder()
//                                    .totalPaid(order.getPaymentProcess().getTotalPaid())
//                                    .paymentMethodId(order.getPaymentProcess().getId())
//                                    .paidDate(order.getPaymentProcess().getPaidDate())
//                                    .paymentCode(order.getPaymentProcess().getPaymentCode())
//                                    .paymentStatus(order.getPaymentProcess().getPaymentStatus())
//                                    .build())
//                            .build();
//
//                }
//        );
//
//        cartItems.stream().map(cartItem -> {
//            Order order = new Order();
//            order.setId(UUID.randomUUID().toString());
//            order.setDescription(cartOrderRequest.getDescription());
//            order.setOrderStatus("Menunggu Pembayaran");
//            order.setShippingCost(cartOrderRequest.getShippingCost());
//            order.setShippingAddress(cartOrderRequest.getShippingAddress());
//            order.setTotalPrice(cartItem
//                    .getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()).add(shippingCost).add(tax)));
//            order.setTax(cartOrderRequest.getTax());
//            order.setUser(user);
//            order.setCreatedAt(Utilities.changeFormatToTimeStamp());
//            order.setExpiredAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
//            order.setProduct(cartItem.getProduct());
//            order.setOrderStatus("Menunggu pembayaran");
//            orderRepository.save(order);
//
//            if (cartOrderRequest.getPaymentMethodId() != null) {
//                PaymentProcess paymentProcess = new PaymentProcess();
//                paymentProcess.setOrder(order);
//                paymentProcess.setPaymentStatus("Belum dibayar");
//                paymentProcess.setPaymentCode(Utilities.generatePaymentCode(12));
//                paymentProcess.setPaymentMethod(paymentMethod);
//                paymentProcessRepository.save(paymentProcess);
//            }
//
//
//            return order;
//        });


}
//
//    @Transactional(readOnly = true)
//    public List<OrderResponse> getAllOrders(User user) {
//        List<Order> orders = orderRepository.findByUserUsernameUser(user.getUsernameUser());
//
//        List<OrderResponse> orderResponses = orders.stream().map(order -> {
//            String productId = order.getProduct().getId();
//            Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));
//            PaymentProcess paymentProcess = paymentProcessRepository.findByOrderId(order.getId());
//
//            ProductResponse productResponse = ProductResponse
//                    .builder()
//                    .productId(product.getId())
//                    .productDescription(product.getDescription())
//                    .price(product.getPrice())
//                    .stok(product.getStok())
//                    .originalPrice(product.getOriginalPrice())
//                    .isPromo(product.getIsPromo())
//                    .categoryName(product.getCategoryItem().getName())
//                    .bpomCode(product.getBpomCode())
//                    .discount(product.getDiscount())
//                    .brandName(product.getBrand().getName())
//                    .thumbnailImage(product.getThumbnailImage())
//                    .size(product.getSize())
//                    .productName(product.getName())
//                    .build();
//
//            PaymentProcessResponse paymentProcessResponse = PaymentProcessResponse.builder()
//                    .paymentMethodId(paymentProcess.getPaymentMethod().getId())
//                    .paymentStatus(paymentProcess.getPaymentStatus())
//                    .paidDate(paymentProcess.getPaidDate())
//                    .paymentCode(paymentProcess.getPaymentCode())
//                    .totalPaid(paymentProcess.getTotalPaid())
//                    .build();
//
//            return OrderResponse.builder()
//                    .product(productResponse)
//                    .orderId(order.getId())
//                    .productId(product.getId())
//                    .orderStatus(order.getOrderStatus())
//                    .totalPrice(order.getTotalPrice())
//                    .shippingAddress(order.getShippingAddress())
//                    .description(order.getDescription())
//                    .tax(order.getTax())
//                    .expiredAt(order.getExpiredAt())
//                    .quantity(order.getQuantity())
//                    .shippingCost(order.getShippingCost())
//                    .payment(paymentProcessResponse)
//                    .createdAt(order.getCreatedAt())
//                    .build();
//        }).toList();
//
//        return orderResponses;
//    }
//
//    @Transactional(readOnly = true)
//    public OrderResponse detailOrderResponse(User user, String orderId) {
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order tidak ditemukan"));
//        String productId = order.getProduct().getId();
//
//        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));
//
//        PaymentProcess paymentProcess = paymentProcessRepository.findByOrderId(orderId);
//
//        ProductResponse productResponse = ProductResponse.builder()
//                .productName(product.getId())
//                .brandName(product.getBrand().getName())
//                .size(product.getSize())
//                .stok(product.getStok())
//                .thumbnailImage(product.getThumbnailImage())
//                .price(product.getPrice())
//                .discount(product.getDiscount())
//                .originalPrice(product.getOriginalPrice())
//                .bpomCode(product.getBpomCode())
//                .categoryName(product.getCategoryItem().getName())
//                .isPromo(product.getIsPromo())
//                .productId(product.getId())
//                .productDescription(product.getDescription())
//                .build();
//
//        PaymentProcessResponse paymentProcessResponse = PaymentProcessResponse.builder()
//
//                .paymentCode(paymentProcess.getPaymentCode())
//                .paidDate(paymentProcess.getPaidDate())
//                .paymentStatus(paymentProcess.getPaymentStatus())
//                .paymentMethodId(paymentProcess.getPaymentMethod().getId())
//                .totalPaid(paymentProcess.getTotalPaid())
//                .build();
//        OrderResponse orderResponse = OrderResponse.builder()
//                .orderId(order.getId())
//                .tax(order.getTax())
//                .quantity(order.getQuantity())
//                .orderStatus(order.getOrderStatus())
//                .description(order.getDescription())
//                .product(productResponse)
//                .shippingCost(order.getShippingCost())
//                .payment(paymentProcessResponse)
//                .productId(product.getId())
//                .expiredAt(order.getExpiredAt())
//                .totalPrice(order.getTotalPrice())
//                .createdAt(order.getCreatedAt())
//                .shippingAddress(order.getShippingAddress())
//                .build();
//
//        return orderResponse;
//
//
//    }
//

//}

