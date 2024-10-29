package com.skincareMall.skincareMall.service.cart;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;

public interface CartService {
    void addToCartFromDetailProduct(User user, CreateCartRequest request);
    CartResponse getAllCarts(User user);
    void plusQuantity(User user, Long cartItemId);
    void minusQuantity(User user, Long cartItemId);
    void deleteCartItemFromCart(User user, Long cartItemId);
    void deleteAllCartItems(User user);
}
