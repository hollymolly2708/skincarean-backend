package com.skincareMall.skincareMall.controller.cart;

import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.cart.CartService;
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
    public WebResponse<String> addCart(User user, @RequestParam String productId) {
        cartService.addCart(user, productId);
        return WebResponse.<String>builder().data("Berhasil menambahkan produk ke keranjang").build();
    }

    @GetMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<CartResponse>> getAllCarts(User user) {
        List<CartResponse> allCarts = cartService.getAllCarts(user);
        return WebResponse.<List<CartResponse>>builder().data(allCarts).build();
    }

    @DeleteMapping(path = "/api/carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteCart(User user, @RequestParam Long cartId) {
        cartService.deleteCart(user, cartId);
        return WebResponse.<String>builder().data("Keranjang berhasil dihapus").build();
    }
}
