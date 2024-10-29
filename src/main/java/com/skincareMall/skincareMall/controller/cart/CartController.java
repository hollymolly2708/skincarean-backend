package com.skincareMall.skincareMall.controller.cart;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.cart.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    @Autowired
    private CartServiceImpl cartServiceImpl;

    @PostMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addToCart(User user, @RequestBody CreateCartRequest createCartRequest) {
        cartServiceImpl.addToCartFromDetailProduct(user, createCartRequest);
        return WebResponse.<String>builder().data("Berhasil menambahkan produk kedalam keranjang").build();
    }

    @GetMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CartResponse> getCart(User user) {
        CartResponse cartResponse = cartServiceImpl.getAllCarts(user);
        return WebResponse.<CartResponse>builder().data(cartResponse).build();
    }

    @PostMapping(path = "/api/carts/{cartId}/plus-quantity")
    public WebResponse<String> plusQuantity(User user, @PathVariable("cartId") Long cartId) {
        cartServiceImpl.plusQuantity(user, cartId);
        return WebResponse.<String>builder().data("Berhasil menambah quantity").build();
    }

    @DeleteMapping(path = "/api/carts/{cartId}/minus-quantity")
    public WebResponse<String> minusQuantity(User user, @PathVariable("cartId") Long cartId) {
        cartServiceImpl.minusQuantity(user, cartId);
        return WebResponse.<String>builder().data("Berhasil mengurangi quantity").build();
    }

    @DeleteMapping(path = "/api/carts/{cartId}")
    public WebResponse<String> deleteCartItem(User user, @PathVariable("cartId") Long cartId) {
        cartServiceImpl.deleteCartItemFromCart(user, cartId);
        return WebResponse.<String>builder().data("Berhasil menghapus item dari keranjang").build();
    }

    @DeleteMapping(path = "/api/carts")
    public WebResponse<String> deleteAllCartItem(User user) {
        cartServiceImpl.deleteAllCartItems(user);
        return WebResponse.<String>builder().data("Berhasil menghapus semua item dari keranjang").build();
    }

}
