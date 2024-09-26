package com.skincareMall.skincareMall.controller.order;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.order.request.OrderRequest;
import com.skincareMall.skincareMall.model.order.response.OrderResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.order.OrderService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping(path = "/api/orders",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addOrder(User user,
                                       @RequestBody OrderRequest orderRequest
    ){

        orderService.createOrder(user, orderRequest);
        return WebResponse.<String>builder().data("Order has been created").build();
    }

    @DeleteMapping(path = "api/orders/{orderId}")
    public WebResponse<String> deleteOrder(User user, @PathVariable("orderId") String orderId){
        orderService.deleteOrder(user,orderId);
        return WebResponse.<String>builder().data("Order has been deleted").build();
    }

    @GetMapping(path = "api/orders")
    public WebResponse<List<OrderResponse>> getAllOrders(User user){
        List<OrderResponse> orders = orderService.getAllOrders(user);
        return WebResponse.<List<OrderResponse>>builder().data(orders).build();
    }
}
