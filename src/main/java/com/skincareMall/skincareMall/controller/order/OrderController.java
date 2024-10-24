package com.skincareMall.skincareMall.controller.order;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.order.request.CartOrderRequest;
import com.skincareMall.skincareMall.model.order.request.DirectlyOrderRequest;
import com.skincareMall.skincareMall.model.order.response.OrderResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.order.OrderService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/api/orders/checkout/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<Map<String, Object>> addOrder(User user,
                                                     @RequestBody DirectlyOrderRequest directlyOrderRequest
    ) {

        Map<String, Object> stringObjectMap = orderService.directlyCheckout(user, directlyOrderRequest);
        return WebResponse.<Map<String, Object>>builder().data(stringObjectMap).isSuccess(true).build();
    }

    @PostMapping(path = "/api/orders/checkout/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<Map<String, Object>> addOrderFromCart(User user, @RequestBody CartOrderRequest cartOrderRequest) {
        Map<String, Object> stringObjectMap = orderService.checkoutFromCart(user, cartOrderRequest);

        return WebResponse.<Map<String, Object>>builder().data(stringObjectMap).isSuccess(true).build();
    }


    @GetMapping(path = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<OrderResponse>> getAllOrders(User user) {
        List<OrderResponse> allOrders = orderService.getAllOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(allOrders).build();
    }

    @GetMapping(path = "/api/orders/pending-order")
    public WebResponse<List<OrderResponse>> getAllPendingOrders(User user) {
        List<OrderResponse> allOrdersByOrderStatus = orderService.getAllPendingOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(allOrdersByOrderStatus).build();
    }

    @GetMapping(path = "/api/orders/complete-order")
    public WebResponse<List<OrderResponse>> getAllCompletedOrders(User user) {
        List<OrderResponse> allOrdersByOrderStatus = orderService.getAllCompletedOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(allOrdersByOrderStatus).build();
    }

    @DeleteMapping(path = "/api/orders/{orderId}")
    public WebResponse<String> deleteOrder(User user, @PathVariable("orderId") String orderId) {
        orderService.deleteOrder(user, orderId);
        return WebResponse.<String>builder().data("Order has been deleted").build();
    }

    @GetMapping(path = "/api/orders/{orderId}")
    public WebResponse<OrderResponse> detailOrder(User user, @PathVariable("orderId") String orderId) {
        OrderResponse detailOrder = orderService.getDetailOrder(user, orderId);
        return WebResponse.<OrderResponse>builder().data(detailOrder).build();
    }

    @DeleteMapping(path = "/api/orders/{orderId}/cancel-order")
    public WebResponse<String> cancelOrder(User user, @PathVariable("orderId") String orderId) {
        orderService.cancelOrder(user, orderId);
        return WebResponse.<String>builder().data("Order berhasil dibatalkan").isSuccess(true).build();
    }

    @GetMapping(path = "/api/orders/cancel-order")
    public WebResponse<List<OrderResponse>> getAllCancelOrders(User user) {
        List<OrderResponse> allCanceledOrders = orderService.getAllCanceledOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(allCanceledOrders).build();
    }

}
