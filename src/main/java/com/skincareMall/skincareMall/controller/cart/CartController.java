package com.skincareMall.skincareMall.controller.cart;

import com.skincareMall.skincareMall.entity.Cart;
import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.cart.CartService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;


    @PostMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addToCart(User user, @RequestBody CreateCartRequest createCartRequest) {
        cartService.addToCart(user, createCartRequest);
        return WebResponse.<String>builder().data("berhasil menambahkan produk kedalam keranjang").build();
    }

    @GetMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CartResponse> getCart(User user) {
        CartResponse cartResponse = cartService.getAllItemCart(user);
        return WebResponse.<CartResponse>builder().data(cartResponse).build();
    }


    //menghapus satu persatu itemnya dengan mengurangi quantity
    @DeleteMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CartResponse> deleteCart(User user, @RequestParam("cartItemId") Long cartItemId) {
        CartResponse cartResponse = cartService.deleteCart(user, cartItemId);
        return WebResponse.<CartResponse>builder().data(cartResponse).build();
    }


    //menghapus langsung cartItemnya
    @DeleteMapping(path = "/api/carts/cart-item")
    public WebResponse<CartResponse> deleteCartByCartItem(User user, @RequestParam("cartItemId") Long cartItemId) {
        CartResponse cartResponse = cartService.deleteCartByCartItem(user, cartItemId);
        return WebResponse.<CartResponse>builder().data(cartResponse).build();
    }
}
