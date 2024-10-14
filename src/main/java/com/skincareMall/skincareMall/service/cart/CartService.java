package com.skincareMall.skincareMall.service.cart;

import com.skincareMall.skincareMall.entity.Cart;
import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.repository.CartRepository;
import com.skincareMall.skincareMall.repository.ProductRepository;
import com.skincareMall.skincareMall.repository.UserRepository;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public void addToCartFromDetailProduct(User user, CreateCartRequest request) {

        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Tidak ditemukan"));
        Cart cart = cartRepository.findByUserUsernameUserAndProductId(user.getUsernameUser(), product.getId()).orElse(null);

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setProduct(product);
            newCart.setTotal(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            newCart.setQuantity(request.getQuantity());
            cartRepository.save(newCart);
        } else {
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
            cart.setTotal(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
            cartRepository.save(cart);
        }
    }

    @Transactional(readOnly = true)
    public List<CartResponse> getAllCarts(User user) {


        List<Cart> carts = cartRepository.findByUserUsernameUser(user.getUsernameUser());


        AtomicReference<BigDecimal> overallTotal = new AtomicReference<>(BigDecimal.ZERO);


        // Hitung subtotal untuk item ini
        // Tambahkan subtotal ke total keseluruhan
        return carts.stream().map(
                cart -> {
                    // Hitung subtotal untuk item ini

                    BigDecimal itemSubtotal = cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
                    // Tambahkan subtotal ke total keseluruhan
                    overallTotal.updateAndGet(currentTotal -> currentTotal.add(itemSubtotal));


                    return CartResponse.builder()

                            .id(cart.getId())
                            .product(ProductResponse.builder()
                                    .size(cart.getProduct().getSize())
                                    .stok(cart.getProduct().getStok())
                                    .isPromo(cart.getProduct().getIsPromo())
                                    .productName(cart.getProduct().getName())
                                    .thumbnailImage(cart.getProduct().getThumbnailImage())
                                    .originalPrice(cart.getProduct().getOriginalPrice())
                                    .price(cart.getProduct().getPrice())
                                    .isPopularProduct(cart.getProduct().getIsPopularProduct())
                                    .brandName(cart.getProduct().getBrand().getName())
                                    .productId(cart.getProduct().getId())
                                    .bpomCode(cart.getProduct().getBpomCode())
                                    .discount(cart.getProduct().getDiscount())
                                    .categoryName(cart.getProduct().getCategoryItem().getName())
                                    .ingredient(cart.getProduct().getIngredient())
                                    .productDescription(cart.getProduct().getDescription())
                                    .build())


                            .total(itemSubtotal)
                            .quantity(cart.getQuantity())
                            .build();
                }
        ).toList();
    }

    @Transactional
    public CartResponse plusQuantity(User user, Long cartId) {
        Cart cart = cartRepository.findByUserUsernameUserAndId(user.getUsernameUser(), cartId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item pada keranjang tidak ditemukan"));
        cart.setQuantity(cart.getQuantity() + 1);
        cart.setTotal(cart.getTotal().multiply(BigDecimal.valueOf(cart.getQuantity() + 1)));
        cartRepository.save(cart);

        AtomicReference<BigDecimal> overallTotal = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
        BigDecimal itemSubtotal = cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
        // Tambahkan subtotal ke total keseluruhan
        overallTotal.updateAndGet(currentTotal -> currentTotal.add(itemSubtotal));

        return CartResponse
                .builder()
                .id(cart.getId())
                .product(ProductResponse.builder()
                        .size(cart.getProduct().getSize())
                        .stok(cart.getProduct().getStok())
                        .isPromo(cart.getProduct().getIsPromo())
                        .productName(cart.getProduct().getName())
                        .thumbnailImage(cart.getProduct().getThumbnailImage())
                        .originalPrice(cart.getProduct().getOriginalPrice())
                        .price(cart.getProduct().getPrice())
                        .isPopularProduct(cart.getProduct().getIsPopularProduct())
                        .brandName(cart.getProduct().getBrand().getName())
                        .productId(cart.getProduct().getId())
                        .bpomCode(cart.getProduct().getBpomCode())
                        .discount(cart.getProduct().getDiscount())
                        .categoryName(cart.getProduct().getCategoryItem().getName())
                        .ingredient(cart.getProduct().getIngredient())
                        .productDescription(cart.getProduct().getDescription())
                        .build())
                .total(itemSubtotal)
                .quantity(cart.getQuantity())
                .build();

    }

    @Transactional
    public void minusQuantity(User user, Long cartId) {
        Cart cart = cartRepository.findByUserUsernameUserAndId(user.getUsernameUser(), cartId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item pada keranjang tidak ditemukan"));
        cart.setQuantity(cart.getQuantity() - 1);
        cart.setTotal(cart.getTotal().multiply(BigDecimal.valueOf(cart.getQuantity() - 1)));
        cartRepository.save(cart);

    }

    @Transactional
    public void deleteProductFromCart(User user, Long cartId) {
        Cart cart = cartRepository.findByUserUsernameUserAndId(user.getUsernameUser(), cartId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item pada keranjang tidak ditemukan"));
        cartRepository.deleteById(cart.getId());


    }

    @Transactional
    public void deleteAllProductFromCart(User user) {
        List<Cart> carts = cartRepository.findByUserUsernameUser(user.getUsernameUser());
        if (!carts.isEmpty()) {
            cartRepository.deleteAll();
        }
    }


}