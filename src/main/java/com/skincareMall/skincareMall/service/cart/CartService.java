package com.skincareMall.skincareMall.service.cart;

import com.skincareMall.skincareMall.entity.Cart;
import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.repository.CartRepository;
import com.skincareMall.skincareMall.repository.ProductRepository;
import com.skincareMall.skincareMall.repository.UserRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;

    public void addCart(User user, String productId) {

        Cart cartByUsernameUserAndProductId = cartRepository.findByUserUsernameUserAndProductId(user.getUsernameUser(), productId);

        if (Objects.nonNull(cartByUsernameUserAndProductId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produk sudah ada di keranjang");
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));

        Cart cart = Cart.builder()
                .createdAt(Utilities.changeFormatToTimeStamp())
                .lastUpdatedAt(Utilities.changeFormatToTimeStamp())
                .product(product)
                .user(user)
                .isActive(true)
                .build();

        cartRepository.save(cart);

    }

    public List<CartResponse> getAllCarts(User user) {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(cart -> {
            return CartResponse.builder()
                    .cartId(cart.getId())
                    .productId(cart.getProduct().getId())
                    .discount(cart.getProduct().getDiscount())
                    .originalPrice(cart.getProduct().getOriginalPrice())
                    .productName(cart.getProduct().getName())
                    .thumbnailProduct(cart.getProduct().getThumbnailImage())
                    .isActive(cart.getIsActive())
                    .totalPrice(cart.getProduct().getPrice())
                    .build();
        }).toList();
    }

    public void  deleteCart(User user, Long cartId) {
        Cart cart = cartRepository.findByUserUsernameUserAndId(user.getUsernameUser(), cartId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));
        cartRepository.delete(cart);
    }


}
