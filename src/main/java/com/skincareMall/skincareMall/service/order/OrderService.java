package com.skincareMall.skincareMall.service.order;

import com.skincareMall.skincareMall.entity.*;
import com.skincareMall.skincareMall.mapper.ProductMapper;
import com.skincareMall.skincareMall.model.order.request.OrderRequest;
import com.skincareMall.skincareMall.model.order.response.OrderResponse;
import com.skincareMall.skincareMall.model.payment_process.response.PaymentProcessResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.repository.OrderRepository;
import com.skincareMall.skincareMall.repository.PaymentMethodRepository;
import com.skincareMall.skincareMall.repository.PaymentProcessRepository;
import com.skincareMall.skincareMall.repository.ProductRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import jdk.jshell.execution.Util;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private PaymentProcessRepository paymentProcessRepository;

    public void createOrder(User user, OrderRequest request) {
        validationService.validate(request);
        System.out.println(request.getPaymentMethodId());
        if (Objects.nonNull(request)) {
            Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is'nt found"));
            PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment Method is'nt found"));
            if (product.getStok() == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "stok product has been empty");
            } else if (product.getStok() < request.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the quantity ordered is more than the available stock");
            } else {
                Long quantity = request.getQuantity();
                BigDecimal price = product.getPrice();
                BigDecimal tax = request.getTax();
                BigDecimal shippingCost = request.getShippingCost();


                Order order = new Order();
                order.setUser(user);
                order.setId(UUID.randomUUID().toString());
                order.setTotalPrice(price.multiply(BigDecimal.valueOf(quantity)).add(tax).add(shippingCost));
                order.setQuantity(request.getQuantity());
                order.setDescription(request.getDescription());
                order.setShippingCost(request.getShippingCost());
                order.setTax(request.getTax());
                order.setShippingAddress(request.getShippingAddress());
                order.setExpiredAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
                order.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
                order.setProduct(product);
                orderRepository.save(order);

                if (request.getPaymentMethodId() != null) {
                    PaymentProcess paymentProcess = new PaymentProcess();
                    paymentProcess.setOrder(order);
                    paymentProcess.setPaymentStatus("Menunggu Pembayaran");
                    paymentProcess.setPaymentMethod(paymentMethod);
                    paymentProcess.setPaymentCode(Utilities.generatePaymentCode(12));
                    paymentProcessRepository.save(paymentProcess);
                }
            }

        }
    }

    public List<OrderResponse> getAllOrders(User user) {
        List<Order> orders = orderRepository.findByUserUsernameUser(user.getUsernameUser());

        List<OrderResponse> orderResponses = orders.stream().map(order -> {
            String productId = order.getProduct().getId();
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is'nt found"));
            PaymentProcess paymentProcess = paymentProcessRepository.findByOrderId(order.getId());

            ProductResponse productResponse = ProductResponse
                    .builder()
                    .productId(product.getId())
                    .productDescription(product.getDescription())
                    .price(product.getPrice())
                    .stok(product.getStok())
                    .originalPrice(product.getOriginalPrice())
                    .isPromo(product.getIsPromo())
                    .category(product.getCategory())
                    .bpomCode(product.getBpomCode())
                    .discount(product.getDiscount())
                    .brands(product.getBrands())
                    .thumbnailImage(product.getThumbnailImage())
                    .size(product.getSize())
                    .productName(product.getName())
                    .build();

            PaymentProcessResponse paymentProcessResponse = PaymentProcessResponse.builder()
                    .paymentMethodId(paymentProcess.getPaymentMethod().getId())
                    .paymentStatus(paymentProcess.getPaymentStatus())
                    .paidDate(paymentProcess.getPaidDate())
                    .paymentCode(paymentProcess.getPaymentCode())
                    .totalPaid(paymentProcess.getTotalPaid())
                    .build();

            return OrderResponse.builder()
                    .product(productResponse)
                    .orderId(order.getId())
                    .productId(product.getId())
                    .totalPrice(order.getTotalPrice())
                    .shippingAddress(order.getShippingAddress())
                    .description(order.getDescription())
                    .tax(order.getTax())
                    .expiredAt(order.getExpiredAt())
                    .quantity(order.getQuantity())
                    .shippingCost(order.getShippingCost())
                    .payment(paymentProcessResponse)
                    .createdAt(order.getCreatedAt())
                    .build();
        }).toList();

        return orderResponses;
    }

    public OrderResponse detailOrderResponse(User user, String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order is'nt found"));
        String productId = order.getProduct().getId();

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is'nt found"));

        PaymentProcess paymentProcess = paymentProcessRepository.findByOrderId(orderId);

        ProductResponse productResponse = ProductResponse.builder()
                .productName(product.getId())
                .brands(product.getBrands())
                .size(product.getSize())
                .stok(product.getStok())
                .thumbnailImage(product.getThumbnailImage())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .originalPrice(product.getOriginalPrice())
                .bpomCode(product.getBpomCode())
                .category(product.getCategory())
                .isPromo(product.getIsPromo())
                .productId(product.getId())
                .productDescription(product.getDescription())
                .build();

        PaymentProcessResponse paymentProcessResponse = PaymentProcessResponse.builder()

                .paymentCode(paymentProcess.getPaymentCode())
                .paidDate(paymentProcess.getPaidDate())
                .paymentStatus(paymentProcess.getPaymentStatus())
                .paymentMethodId(paymentProcess.getPaymentMethod().getId())
                .totalPaid(paymentProcess.getTotalPaid())
                .build();
        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .tax(order.getTax())
                .quantity(order.getQuantity())
                .description(order.getDescription())
                .product(productResponse)
                .shippingCost(order.getShippingCost())
                .payment(paymentProcessResponse)
                .productId(product.getId())
                .expiredAt(order.getExpiredAt())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .shippingAddress(order.getShippingAddress())
                .build();

        return orderResponse;


    }


    public void deleteOrder(User user, String orderId) {
        orderRepository.deleteById(orderId);
    }
}

