package com.skincarean.skincarean.controller.cart;

import com.skincarean.skincarean.entity.User;
import com.skincarean.skincarean.model.cart.request.CreateCartRequest;
import com.skincarean.skincarean.model.cart.response.CartResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;
import com.skincarean.skincarean.service.cart.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    @Autowired
    private CartServiceImpl cartServiceImpl;

    @PostMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addToCart(User user, @RequestBody CreateCartRequest createCartRequest) {
        cartServiceImpl.addProductToCart(user, createCartRequest);
        return WebResponse.<String>builder().data("Berhasil menambahkan produk kedalam keranjang").build();
    }

    @GetMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CartResponse> getCart(User user) {
        CartResponse cartResponse = cartServiceImpl.getAllCarts(user);
        return WebResponse.<CartResponse>builder().data(cartResponse).build();
    }

    @GetMapping(path = "/api/carts/active-carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CartResponse> getActiveCart(User user) {
        CartResponse activeCartItem = cartServiceImpl.getActiveCartItem(user);
        return WebResponse.<CartResponse>builder().data(activeCartItem).build();
    }

    @PostMapping(path = "/api/carts/{cartItemId}/plus-quantity")
    public WebResponse<String> plusQuantity(User user, @PathVariable("cartItemId") Long cartItemId) {
        cartServiceImpl.plusQuantity(user, cartItemId);
        return WebResponse.<String>builder().data("Berhasil menambah quantity").build();
    }

    @DeleteMapping(path = "/api/carts/{cartItemId}/minus-quantity")
    public WebResponse<String> minusQuantity(User user, @PathVariable("cartItemId") Long cartItemId) {
        cartServiceImpl.minusQuantity(user, cartItemId);
        return WebResponse.<String>builder().data("Berhasil mengurangi quantity").build();
    }

    @DeleteMapping(path = "/api/carts/{cartItemId}")
    public WebResponse<String> deleteCartItem(User user, @PathVariable("cartItemId") Long cartId) {
        cartServiceImpl.deleteCartItemFromCart(user, cartId);
        return WebResponse.<String>builder().data("Berhasil menghapus item dari keranjang").build();
    }

    @PatchMapping(path = "/api/carts/{cartItemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> activeCartItem(User user, @PathVariable("cartItemId") Long cartItemId) {
        cartServiceImpl.activeCartItem(user, cartItemId);
        return WebResponse.<String>builder().data("Set").build();
    }

    @DeleteMapping(path = "/api/carts")
    public WebResponse<String> deleteAllCartItem(User user) {
        cartServiceImpl.deleteAllCartItems(user);
        return WebResponse.<String>builder().data("Berhasil menghapus semua item dari keranjang").build();
    }

}
