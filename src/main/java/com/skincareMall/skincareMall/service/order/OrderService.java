package com.skincareMall.skincareMall.service.order;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.order.request.CartOrderRequest;
import com.skincareMall.skincareMall.model.order.request.DirectlyOrderRequest;
import com.skincareMall.skincareMall.model.order.response.OrderResponse;

import java.util.List;
import java.util.Map;

public interface OrderService {
     Map<String, Object> directlyCheckout(User user, DirectlyOrderRequest request);
     Map<String, Object> checkoutFromCart(User user, CartOrderRequest cartOrderRequest);
     List<OrderResponse> getAllOrders(User user);
     List<OrderResponse> getAllPendingOrders(User user);
     void cancelOrder(User user, String orderId);
     List<OrderResponse> getAllCanceledOrders(User user);
     List<OrderResponse> getAllCompletedOrders(User user);
     void deleteOrder(User user, String productId);
     OrderResponse getDetailOrder(User user, String orderId);
}
