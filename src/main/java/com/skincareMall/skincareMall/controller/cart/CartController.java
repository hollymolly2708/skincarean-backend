package com.skincareMall.skincareMall.controller.cart;

import com.skincareMall.skincareMall.entity.Cart;
import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;


    @PostMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addToCart(User user, @RequestBody CreateCartRequest createCartRequest) {
        cartService.addToCartFromDetailProduct(user, createCartRequest);
        return WebResponse.<String>builder().data("Berhasil menambahkan produk kedalam keranjang").build();
    }

    @GetMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<CartResponse>> getCart(User user) {
        List<CartResponse> cartResponse = cartService.getAllCarts(user);
        return WebResponse.<List<CartResponse>>builder().data(cartResponse).build();
    }

    @PostMapping(path = "/api/carts/{cartId}/plus-quantity")
    public WebResponse<CartResponse> plusQuantity(User user, @PathVariable("cartId") Long cartId) {
        CartResponse cartResponse = cartService.plusQuantity(user, cartId);
        return WebResponse.<CartResponse>builder().data(cartResponse).build();
    }

    @DeleteMapping(path = "/api/carts/{cartId}/minus-quantity")
    public WebResponse<String> minusQuantity(User user, @PathVariable("cartId") Long cartId) {
        cartService.minusQuantity(user, cartId);
        return WebResponse.<String>builder().data("Berhasil mengurangi quantity").build();
    }

    @DeleteMapping(path = "/api/carts/{cartId}")
    public WebResponse<String> deleteCartId(User user, @PathVariable("cartId") Long cartId) {
        cartService.deleteProductFromCart(user, cartId);
        return WebResponse.<String>builder().data("Berhasil menghapus item dari keranjang").build();
    }

    @DeleteMapping(path = "/api/carts")
    public WebResponse<String> deleteAllProductFromCart(User user){
        cartService.deleteAllProductFromCart(user);
        return WebResponse.<String>builder().data("Berhasil menghapus semua item dari keranjang").build();
    }

}
