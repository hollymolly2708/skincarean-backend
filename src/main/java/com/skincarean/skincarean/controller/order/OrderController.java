package com.skincarean.skincarean.controller.order;

import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.order.request.CartOrderRequest;
import com.skincarean.skincarean.model.order.request.DirectlyOrderRequest;
import com.skincarean.skincarean.model.order.response.OrderResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;
import com.skincarean.skincarean.service.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @PostMapping(path = "/api/orders/checkout/direct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<Map<String, Object>> addOrder(User user,
                                                     @RequestBody DirectlyOrderRequest directlyOrderRequest
    ) {

        Map<String, Object> stringObjectMap = orderServiceImpl.directlyCheckout(user, directlyOrderRequest);
        return WebResponse.<Map<String, Object>>builder().data(stringObjectMap).isSuccess(true).build();
    }

    @PostMapping(path = "/api/orders/checkout/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<Map<String, Object>> addOrderFromCart(User user, @RequestBody CartOrderRequest cartOrderRequest) {
        Map<String, Object> stringObjectMap = orderServiceImpl.checkoutFromCart(user, cartOrderRequest);

        return WebResponse.<Map<String, Object>>builder().data(stringObjectMap).isSuccess(true).build();
    }


    @GetMapping(path = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<OrderResponse>> getAllOrders(User user) {
        List<OrderResponse> allOrders = orderServiceImpl.getAllOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(allOrders).build();
    }

    @GetMapping(path = "/api/orders/pending-order")
    public WebResponse<List<OrderResponse>> getAllPendingOrders(User user) {
        List<OrderResponse> allOrdersByOrderStatus = orderServiceImpl.getAllPendingOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(allOrdersByOrderStatus).build();
    }

    @GetMapping(path = "/api/orders/complete-order")
    public WebResponse<List<OrderResponse>> getAllCompletedOrders(User user) {
        List<OrderResponse> allOrdersByOrderStatus = orderServiceImpl.getAllCompletedOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(allOrdersByOrderStatus).build();
    }

    @DeleteMapping(path = "/api/orders/{orderId}")
    public WebResponse<String> deleteOrder(User user, @PathVariable("orderId") String orderId) {
        orderServiceImpl.deleteOrder(user, orderId);
        return WebResponse.<String>builder().data("Order has been deleted").build();
    }

    @GetMapping(path = "/api/orders/{orderId}")
    public WebResponse<OrderResponse> detailOrder(User user, @PathVariable("orderId") String orderId) {
        OrderResponse detailOrder = orderServiceImpl.getDetailOrder(user, orderId);
        return WebResponse.<OrderResponse>builder().data(detailOrder).build();
    }

    @DeleteMapping(path = "/api/orders/{orderId}/cancel-order")
    public WebResponse<String> cancelOrder(User user, @PathVariable("orderId") String orderId) {
        orderServiceImpl.cancelOrder(user, orderId);
        return WebResponse.<String>builder().data("Order berhasil dibatalkan").isSuccess(true).build();
    }

    @GetMapping(path = "/api/orders/cancel-order")
    public WebResponse<List<OrderResponse>> getAllCancelOrders(User user) {
        List<OrderResponse> allCanceledOrders = orderServiceImpl.getAllCanceledOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(allCanceledOrders).build();
    }

}
