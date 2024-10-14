package com.skincareMall.skincareMall.controller.order;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.order.request.CartOrderRequest;
import com.skincareMall.skincareMall.model.order.request.DirectlyOrderRequest;
import com.skincareMall.skincareMall.model.order.response.OrderResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/api/orders/checkout/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addOrder(User user,
                                        @RequestBody DirectlyOrderRequest directlyOrderRequest
    ) {

        orderService.directlyCheckout(user, directlyOrderRequest);
        return WebResponse.<String>builder().data("Order berhasil dibuat").build();
    }

    @PostMapping(path = "/api/orders/checkout/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addOrderFromCart(User user, @RequestBody CartOrderRequest cartOrderRequest) {
        orderService.checkoutFromCart(user, cartOrderRequest);
        return WebResponse.<String>builder().data("Berhasil membuat order").build();
    }


    @GetMapping(path = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<OrderResponse>> getAllOrders(User user) {
        List<OrderResponse> allOrders = orderService.getAllOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(allOrders).build();
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


//    @PostMapping(path = "/api/orders/checkout/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public WebResponse<String> addOrderFromCart(User user,@RequestBody CartOrderRequest cartOrderRequest) {
//        orderService.checkoutFromCart(user, cartOrderRequest);
//        return WebResponse.<String>builder().data("Order berhasil dibuat").build();
//    }
//

//
//    @GetMapping(path = "api/orders")
//    public WebResponse<List<OrderResponse>> getAllOrders(User user) {
//        List<OrderResponse> orders = orderService.getAllOrders(user);
//        return WebResponse.<List<OrderResponse>>builder().data(orders).build();
//    }

//    @GetMapping(path = "/api/orders/{orderId}")
//    public WebResponse<OrderResponse> getDetailOrder(User user, @PathVariable("orderId") String orderId) {
//        OrderResponse orderResponse = orderService.detailOrderResponse(user, orderId);
//        return WebResponse.<OrderResponse>builder().data(orderResponse).build();
//    }
}
