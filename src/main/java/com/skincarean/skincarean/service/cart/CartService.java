package com.skincarean.skincarean.service.cart;

import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.cart.request.CreateCartRequest;
import com.skincarean.skincarean.model.cart.response.CartResponse;

public interface CartService {
    void addProductToCart(User user, CreateCartRequest request);
    CartResponse getAllCarts(User user);
    void plusQuantity(User user, Long cartItemId);
    void minusQuantity(User user, Long cartItemId);
    void deleteCartItemFromCart(User user, Long cartItemId);
    void deleteAllCartItems(User user);
    void activeCartItem(User user, Long cartItemId);

    CartResponse getActiveCartItem(User user);
}
