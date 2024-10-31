//package com.skincareMall.skincareMall.service.order;
//
//import com.skincareMall.skincareMall.entity.*;
//import com.skincareMall.skincareMall.model.order.request.CartOrderRequest;
//import com.skincareMall.skincareMall.model.order.request.DirectlyOrderRequest;
//import com.skincareMall.skincareMall.model.order.response.OrderItemResponse;
//import com.skincareMall.skincareMall.model.order.response.OrderResponse;
//import com.skincareMall.skincareMall.model.payment_process.response.PaymentProcessResponse;
//import com.skincareMall.skincareMall.model.product.response.ProductResponse;
//import com.skincareMall.skincareMall.repository.*;
//import com.skincareMall.skincareMall.utils.Utilities;
//import com.skincareMall.skincareMall.validation.ValidationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class OrderServiceImpl {
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private ValidationService validationService;
//    @Autowired
//    private PaymentMethodRepository paymentMethodRepository;
//    @Autowired
//    private PaymentProcessRepository paymentProcessRepository;
//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private CartRepository cartRepository;
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//
//    @Transactional
//    public Map<String, Object> directlyCheckout(User user, DirectlyOrderRequest request) {
//        // Validasi input
//        validationService.validate(request);
//
//        // Mencari produk dan metode pembayaran
//        Product product = productRepository.findById(request.getProductId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));
//
//        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));
//
//        // Cek ketersediaan stok
//        if (product.getStok() == 0) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stok kosong");
//        }
//        if (product.getStok() < request.getQuantity()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Jumlah quantity order melebihi dari jumlah stok yang tersedia");
//        }
//
//        // Membuat order baru
//        Order order = new Order();
//        order.setId(UUID.randomUUID().toString());
//        order.setUser(user);
//        order.setOrderStatus("Menunggu Pembayaran");
//        order.setDescription(request.getDescription());
//        order.setShippingAddress(request.getShippingAddress());
//        order.setShippingCost(BigDecimal.valueOf(20000));
//        order.setTax(BigDecimal.valueOf(0.05)); // Pajak diatur ke 5%
//        orderRepository.save(order);
//
//        // Membuat order item
//        Long quantity = request.getQuantity();
//        BigDecimal price = product.getPrice();
//        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
//
//        OrderItem orderItem = new OrderItem();
//        orderItem.setId(UUID.randomUUID().toString());
//        orderItem.setTotalPrice(totalPrice);
//        orderItem.setQuantity(quantity);
//        orderItem.setOrder(order);
//        orderItem.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
//        orderItem.setExpiredAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
//        orderItem.setProduct(product);
//        orderItemRepository.save(orderItem);
//
//        // Menghitung total order termasuk pajak
//        BigDecimal taxAmount = totalPrice.multiply(order.getTax());
//        order.setTotalPrice(totalPrice.add(taxAmount).add(order.getShippingCost()));
//        order.setTax(taxAmount);
//        orderRepository.save(order);
//
//        // Membuat proses pembayaran jika metode pembayaran ada
//        if (request.getPaymentMethodId() != null) {
//            PaymentProcess paymentProcess = new PaymentProcess();
//            paymentProcess.setOrder(order);
//            paymentProcess.setPaymentStatus("Belum dibayar");
//            paymentProcess.setPaymentMethod(paymentMethod);
//            paymentProcess.setPaymentCode(Utilities.generatePaymentCode(12));
//            paymentProcessRepository.save(paymentProcess);
//        }
//
//        // Mengembalikan respons dengan order ID
//        Map<String, Object> response = new HashMap<>();
//        response.put("orderId", order.getId());
//        return response;
//    }
//
//
//    @Transactional
//    public Map<String, Object> checkoutFromCart(User user, CartOrderRequest cartOrderRequest) {
//
//        validationService.validate(cartOrderRequest);
//
//
//        PaymentMethod paymentMethod = paymentMethodRepository.findById(cartOrderRequest.getPaymentMethodId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));
//
//        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));
//
//        List<CartItem> cartItems = cartItemRepository.findByCartAndIsActive(cart, true);
//
//        if (cartItems == null || cartItems.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tidak ada item di dalam keranjang");
//        }
//
//
//        Order order = new Order();
//        order.setId(UUID.randomUUID().toString());
//        order.setUser(user);
//        order.setShippingAddress(cartOrderRequest.getShippingAddress());
//        order.setShippingCost(BigDecimal.valueOf(20000));
//        order.setDescription(cartOrderRequest.getDescription());
//        order.setOrderStatus("Menunggu pembayaran");
//        order.setTax(BigDecimal.ZERO);
//        orderRepository.save(order);
//
//
//        BigDecimal overallTotal = BigDecimal.ZERO;
//        for (CartItem cartItem : cartItems) {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setId(UUID.randomUUID().toString());
//            orderItem.setOrder(order);
//            orderItem.setCreatedAt(Utilities.changeFormatToTimeStamp());
//            orderItem.setExpiredAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
//
//            BigDecimal itemSubTotal = cartItem.getTotal();
//            overallTotal = overallTotal.add(itemSubTotal);
//
//            orderItem.setProduct(cartItem.getProduct());
//            orderItem.setQuantity(cartItem.getQuantity());
//            orderItem.setTotalPrice(itemSubTotal);
//
//            orderItemRepository.save(orderItem);
//        }
//
//
//        BigDecimal taxRate = BigDecimal.valueOf(0.05);
//        BigDecimal taxAmount = overallTotal.multiply(taxRate);
//
//        // Mengupdate total order
//        order.setTotalPrice(overallTotal.add(taxAmount).add(order.getShippingCost()));
//        order.setTax(taxAmount);
//        orderRepository.save(order);
//
//        // Membuat payment process jika metode pembayaran ada
//        if (cartOrderRequest.getPaymentMethodId() != null) {
//            PaymentProcess paymentProcess = new PaymentProcess();
//            paymentProcess.setOrder(order);
//            paymentProcess.setPaymentCode(Utilities.generatePaymentCode(12));
//            paymentProcess.setPaymentMethod(paymentMethod);
//            paymentProcess.setPaymentStatus("Belum dibayar");
//            paymentProcessRepository.save(paymentProcess);
//        }
//
//        // Mempersiapkan response
//        Map<String, Object> response = new HashMap<>();
//        response.put("orderId", order.getId());
//
//        return response;
//    }
//
//
//    @Transactional(readOnly = true)
//    public List<OrderResponse> getAllOrders(User user) {
//        List<Order> orders = orderRepository.findByUserUsernameUser(user.getUsernameUser());
//
//        return orders.stream().map(order ->
//                {
//                    PaymentMethod paymentMethod = paymentMethodRepository.findById(order.getPaymentProcess().getPaymentMethod().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metode pembayaran tidak ditemukan"));
//                    return OrderResponse.builder()
//                            .orderId(order.getId())
//                            .orderStatus(order.getOrderStatus())
//
//                            .payment(
//
//                                    PaymentProcessResponse.builder()
//                                            .paymentStatus(order.getPaymentProcess().getPaymentStatus())
//                                            .paymentCode(order.getPaymentProcess().getPaymentCode())
//                                            .paidDate(order.getPaymentProcess().getPaidDate())
//                                            .paymentMethodId(order.getPaymentProcess().getId())
//                                            .paymentMethodName(paymentMethod.getName())
//                                            .totalPaid(order.getPaymentProcess().getTotalPaid())
//                                            .build())
//                            .tax(order.getTax())
//                            .shippingAddress(order.getShippingAddress())
//                            .description(order.getDescription())
//                            .shippingCost(order.getShippingCost())
//                            .finalPrice(order.getTotalPrice())
//
//
//                            .orderItems(order.getOrderItems().stream().map(
//                                            orderItem ->
//                                                    OrderItemResponse
//                                                            .builder()
//                                                            .createdAt(orderItem.getCreatedAt())
//                                                            .product(ProductResponse.builder()
//                                                                    .productId(orderItem.getProduct().getId())
//                                                                    .ingredient(orderItem.getProduct().getIngredient())
//                                                                    .originalPrice(orderItem.getProduct().getOriginalPrice())
//                                                                    .brandName(orderItem.getProduct().getName())
//                                                                    .productName(orderItem.getProduct().getName())
//                                                                    .stok(orderItem.getProduct().getStok())
//                                                                    .categoryName(orderItem.getProduct().getCategoryItem().getName())
//                                                                    .price(orderItem.getProduct().getPrice())
//                                                                    .productDescription(orderItem.getProduct().getDescription())
//                                                                    .size(orderItem.getProduct().getSize())
//                                                                    .isPromo(orderItem.getProduct().getIsPromo())
//                                                                    .ingredient(orderItem.getProduct().getIngredient())
//                                                                    .isPopularProduct(orderItem.getProduct().getIsPopularProduct())
//                                                                    .thumbnailImage(orderItem.getProduct().getThumbnailImage())
//                                                                    .bpomCode(orderItem.getProduct().getBpomCode())
//                                                                    .discount(orderItem.getProduct().getDiscount())
//                                                                    .build())
//                                                            .expiredAt(orderItem.getExpiredAt())
//                                                            .price(orderItem.getTotalPrice())
//                                                            .quantity(orderItem.getQuantity())
//                                                            .build())
//                                    .collect(Collectors.toList()))
//                            .build();
//                })
//                .toList();
//
//    }
//
//    @Transactional(readOnly = true)
//    public List<OrderResponse> getAllPendingOrders(User user) {
//        List<Order> orders = orderRepository.findByUserUsernameUserAndOrderStatus(user.getUsernameUser(), "Menunggu pembayaran");
//
//        return orders.stream().map(order -> OrderResponse.builder()
//                        .orderId(order.getId())
//                        .orderStatus(order.getOrderStatus())
//                        .payment(PaymentProcessResponse.builder()
//                                .paymentStatus(order.getPaymentProcess().getPaymentStatus())
//                                .paymentCode(order.getPaymentProcess().getPaymentCode())
//                                .paidDate(order.getPaymentProcess().getPaidDate())
//                                .paymentMethodId(order.getPaymentProcess().getId())
//                                .totalPaid(order.getPaymentProcess().getTotalPaid())
//                                .build())
//                        .tax(order.getTax())
//                        .shippingAddress(order.getShippingAddress())
//                        .description(order.getDescription())
//                        .shippingCost(order.getShippingCost())
//                        .finalPrice(order.getTotalPrice())
//
//                        .orderItems(order.getOrderItems().stream().map(
//                                        orderItem ->
//                                                OrderItemResponse
//                                                        .builder()
//                                                        .createdAt(orderItem.getCreatedAt())
//                                                        .product(ProductResponse.builder()
//                                                                .productId(orderItem.getProduct().getId())
//                                                                .ingredient(orderItem.getProduct().getIngredient())
//                                                                .originalPrice(orderItem.getProduct().getOriginalPrice())
//                                                                .brandName(orderItem.getProduct().getName())
//                                                                .productName(orderItem.getProduct().getName())
//                                                                .stok(orderItem.getProduct().getStok())
//                                                                .categoryName(orderItem.getProduct().getCategoryItem().getName())
//                                                                .price(orderItem.getProduct().getPrice())
//                                                                .productDescription(orderItem.getProduct().getDescription())
//                                                                .size(orderItem.getProduct().getSize())
//                                                                .isPromo(orderItem.getProduct().getIsPromo())
//                                                                .ingredient(orderItem.getProduct().getIngredient())
//                                                                .isPopularProduct(orderItem.getProduct().getIsPopularProduct())
//                                                                .thumbnailImage(orderItem.getProduct().getThumbnailImage())
//                                                                .bpomCode(orderItem.getProduct().getBpomCode())
//                                                                .discount(orderItem.getProduct().getDiscount())
//                                                                .build())
//                                                        .expiredAt(orderItem.getExpiredAt())
//                                                        .price(orderItem.getTotalPrice())
//                                                        .quantity(orderItem.getQuantity())
//                                                        .build())
//                                .collect(Collectors.toList()))
//                        .build())
//                .toList();
//
//    }
//
//    @Transactional
//    public void cancelOrder(User user, String orderId) {
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order tidak ditemukan"));
//        if (order.getPaymentProcess().getPaymentStatus().equals("Lunas") || order.getOrderStatus().equals("Selesai")) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gagal membatalkan, order telah selesai");
//        }
//
//        order.getPaymentProcess().setPaymentStatus("Dibatalkan");
//        order.getPaymentProcess().setPaymentCode(null);
//        order.getPaymentProcess().setPaidDate(null);
//        order.getPaymentProcess().setTotalPaid(null);
//        order.setOrderStatus("Dibatalkan");
//        orderRepository.save(order);
//    }
//
//    @Transactional(readOnly = true)
//    public List<OrderResponse> getAllCanceledOrders(User user) {
//        List<Order> orders = orderRepository.findByUserUsernameUserAndOrderStatus(user.getUsernameUser(), "Dibatalkan");
//        return orders.stream().map(order -> OrderResponse.builder()
//                        .orderId(order.getId())
//                        .orderStatus(order.getOrderStatus())
//                        .payment(PaymentProcessResponse.builder()
//                                .paymentStatus(order.getPaymentProcess().getPaymentStatus())
//                                .paymentCode(order.getPaymentProcess().getPaymentCode())
//                                .paidDate(order.getPaymentProcess().getPaidDate())
//                                .paymentMethodId(order.getPaymentProcess().getId())
//                                .totalPaid(order.getPaymentProcess().getTotalPaid())
//                                .build())
//                        .tax(order.getTax())
//                        .shippingAddress(order.getShippingAddress())
//                        .description(order.getDescription())
//                        .shippingCost(order.getShippingCost())
//                        .finalPrice(order.getTotalPrice())
//
//                        .orderItems(order.getOrderItems().stream().map(
//                                        orderItem ->
//                                                OrderItemResponse
//                                                        .builder()
//                                                        .createdAt(orderItem.getCreatedAt())
//                                                        .product(ProductResponse.builder()
//                                                                .productId(orderItem.getProduct().getId())
//                                                                .ingredient(orderItem.getProduct().getIngredient())
//                                                                .originalPrice(orderItem.getProduct().getOriginalPrice())
//                                                                .brandName(orderItem.getProduct().getName())
//                                                                .productName(orderItem.getProduct().getName())
//                                                                .stok(orderItem.getProduct().getStok())
//                                                                .categoryName(orderItem.getProduct().getCategoryItem().getName())
//                                                                .price(orderItem.getProduct().getPrice())
//                                                                .productDescription(orderItem.getProduct().getDescription())
//                                                                .size(orderItem.getProduct().getSize())
//                                                                .isPromo(orderItem.getProduct().getIsPromo())
//                                                                .ingredient(orderItem.getProduct().getIngredient())
//                                                                .isPopularProduct(orderItem.getProduct().getIsPopularProduct())
//                                                                .thumbnailImage(orderItem.getProduct().getThumbnailImage())
//                                                                .bpomCode(orderItem.getProduct().getBpomCode())
//                                                                .discount(orderItem.getProduct().getDiscount())
//                                                                .build())
//                                                        .expiredAt(orderItem.getExpiredAt())
//                                                        .price(orderItem.getTotalPrice())
//                                                        .quantity(orderItem.getQuantity())
//                                                        .build())
//                                .collect(Collectors.toList()))
//                        .build())
//                .toList();
//    }
//
//    @Transactional(readOnly = true)
//    public List<OrderResponse> getAllCompletedOrders(User user) {
//        List<Order> orders = orderRepository.findByUserUsernameUserAndOrderStatus(user.getUsernameUser(), "Selesai");
//
//        return orders.stream().map(order -> OrderResponse.builder()
//                        .orderId(order.getId())
//                        .orderStatus(order.getOrderStatus())
//                        .payment(PaymentProcessResponse.builder()
//                                .paymentStatus(order.getPaymentProcess().getPaymentStatus())
//                                .paymentCode(order.getPaymentProcess().getPaymentCode())
//                                .paidDate(order.getPaymentProcess().getPaidDate())
//                                .paymentMethodId(order.getPaymentProcess().getId())
//                                .totalPaid(order.getPaymentProcess().getTotalPaid())
//                                .build())
//                        .tax(order.getTax())
//                        .shippingAddress(order.getShippingAddress())
//                        .description(order.getDescription())
//                        .shippingCost(order.getShippingCost())
//                        .finalPrice(order.getTotalPrice())
//
//                        .orderItems(order.getOrderItems().stream().map(
//                                        orderItem ->
//                                                OrderItemResponse
//                                                        .builder()
//                                                        .createdAt(orderItem.getCreatedAt())
//                                                        .product(ProductResponse.builder()
//                                                                .productId(orderItem.getProduct().getId())
//                                                                .ingredient(orderItem.getProduct().getIngredient())
//                                                                .originalPrice(orderItem.getProduct().getOriginalPrice())
//                                                                .brandName(orderItem.getProduct().getName())
//                                                                .productName(orderItem.getProduct().getName())
//                                                                .stok(orderItem.getProduct().getStok())
//                                                                .categoryName(orderItem.getProduct().getCategoryItem().getName())
//                                                                .price(orderItem.getProduct().getPrice())
//                                                                .productDescription(orderItem.getProduct().getDescription())
//                                                                .size(orderItem.getProduct().getSize())
//                                                                .isPromo(orderItem.getProduct().getIsPromo())
//                                                                .ingredient(orderItem.getProduct().getIngredient())
//                                                                .isPopularProduct(orderItem.getProduct().getIsPopularProduct())
//                                                                .thumbnailImage(orderItem.getProduct().getThumbnailImage())
//                                                                .bpomCode(orderItem.getProduct().getBpomCode())
//                                                                .discount(orderItem.getProduct().getDiscount())
//                                                                .build())
//                                                        .expiredAt(orderItem.getExpiredAt())
//                                                        .price(orderItem.getTotalPrice())
//                                                        .quantity(orderItem.getQuantity())
//                                                        .build())
//                                .collect(Collectors.toList()))
//                        .build())
//                .toList();
//
//    }
//
//
//    @Transactional
//    public void deleteOrder(User user, String orderId) {
//        orderRepository.deleteById(orderId);
//    }
//
//    @Transactional
//    public OrderResponse getDetailOrder(User user, String orderId) {
//
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order tidak ditemukan"));
//        PaymentMethod paymentMethod = paymentMethodRepository.findById(order.getPaymentProcess().getPaymentMethod().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method tidak ditemukan"));
//
//        PaymentProcess paymentProcess = order.getPaymentProcess();
//
//        return OrderResponse.builder()
//                .orderId(order.getId())
//                .orderStatus(order.getOrderStatus())
//                .payment(PaymentProcessResponse.builder()
//                        .totalPaid(paymentProcess.getTotalPaid())
//                        .paymentMethodId(paymentProcess.getId())
//                        .paidDate(paymentProcess.getPaidDate())
//                        .paymentMethodName(paymentMethod.getName())
//                        .paymentCode(paymentProcess.getPaymentCode())
//                        .paymentStatus(paymentProcess.getPaymentStatus())
//                        .build())
//                .finalPrice(order.getTotalPrice())
//                .tax(order.getTax())
//                .shippingCost(order.getShippingCost())
//                .shippingAddress(order.getShippingAddress())
//                .description(order.getDescription())
//                .orderItems(order.getOrderItems().stream().map(orderItem -> {
//
//                    Product product = orderItem.getProduct();
//                    return OrderItemResponse.builder()
//                            .price(orderItem.getTotalPrice())
//                            .expiredAt(orderItem.getExpiredAt())
//                            .createdAt(orderItem.getCreatedAt())
//                            .quantity(orderItem.getQuantity())
//                            .product(ProductResponse.builder()
//                                    .productId(product.getId())
//                                    .productName(product.getName())
//                                    .size(product.getSize())
//                                    .isPromo(product.getIsPromo())
//                                    .price(product.getPrice())
//                                    .stok(product.getStok())
//                                    .isPopularProduct(product.getIsPopularProduct())
//                                    .brandName(product.getBrand().getName())
//                                    .thumbnailImage(product.getThumbnailImage())
//                                    .ingredient(product.getIngredient())
//                                    .discount(product.getDiscount())
//                                    .bpomCode(product.getBpomCode())
//                                    .categoryName(product.getCategoryItem().getName())
//                                    .originalPrice(product.getOriginalPrice())
//                                    .productDescription(product.getDescription())
//                                    .build())
//                            .build();
//                }).toList())
//
//                .build();
//
//    }
//
//
//}
