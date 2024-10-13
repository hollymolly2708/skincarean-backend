package com.skincareMall.skincareMall.service.cart;

import com.skincareMall.skincareMall.entity.Cart;
import com.skincareMall.skincareMall.entity.CartItem;
import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.cart.response.CartItemResponse;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.repository.CartItemRepository;
import com.skincareMall.skincareMall.repository.CartRepository;
import com.skincareMall.skincareMall.repository.ProductRepository;
import com.skincareMall.skincareMall.repository.UserRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

    @Autowired
    private CartItemRepository cartItemRepository;


    public void addToCart(User user, CreateCartRequest request) {

        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Tidak ditemukan"));
        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser()).orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(Utilities.changeFormatToTimeStamp());
            cart.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
            cartRepository.save(cart);
        }


        CartItem cartItem = cartItemRepository.findByProductId(product.getId());
        if (cartItem == null) {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(request.getQuantity());
            newCartItem.setCart(cart);
            cartItemRepository.save(newCartItem);
        } else {
            Long newQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        }


    }

    public CartResponse getAllItemCart(User user) {
        Cart cartByUsernameUser = cartRepository.findByUserUsernameUser(user.getUsernameUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartByUsernameUser.getId());

        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
        List<CartItemResponse> cartItemResponses = cartItems.stream().map(cartItem -> {
            ProductResponse productResponse = ProductResponse.builder()
                    .isPopularProduct(cartItem.getProduct().getIsPopularProduct())
                    .brandName(cartItem.getProduct().getName())
                    .ingredient(cartItem.getProduct().getIngredient())
                    .productId(cartItem.getProduct().getId())
                    .bpomCode(cartItem.getProduct().getBpomCode())
                    .categoryName(cartItem.getProduct().getCategoryItem().getName())
                    .price(cartItem.getProduct().getPrice())
                    .isPromo(cartItem.getProduct().getIsPromo())
                    .discount(cartItem.getProduct().getDiscount())
                    .stok(cartItem.getProduct().getStok())
                    .originalPrice(cartItem.getProduct().getOriginalPrice())
                    .thumbnailImage(cartItem.getProduct().getThumbnailImage())
                    .productName(cartItem.getProduct().getName())
                    .productDescription(cartItem.getProduct().getDescription())
                    .build();

            // Hitung subtotal untuk setiap item dan tambahkan ke total
            BigDecimal itemSubtotal = cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total.updateAndGet(currentTotal -> currentTotal.add(itemSubtotal));

            return CartItemResponse.builder()
                    .cartItemId(cartItem.getId())
                    .product(productResponse)
                    .quantity(cartItem.getQuantity())
                    .build();
        }).toList();

        // Set total keseluruhan ke cart dan simpan ke database
        cartByUsernameUser.setTotal(total.get());
        cartRepository.save(cartByUsernameUser);

        return CartResponse.builder()
                .cartItems(cartItemResponses)
                .total(total.get())
                .build();
    }


    public CartResponse deleteCart(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item keranjang tidak ditemukan"));

        Cart cartByUsernameUser = cartRepository.findByUserUsernameUser(user.getUsernameUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));
        AtomicReference<BigDecimal> total = new AtomicReference(BigDecimal.ZERO);

        if (cartItem.getQuantity() <= 1) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
        }


        List<CartItemResponse> cartItemResponses = cartItemRepository.findAllByCartId(cartByUsernameUser.getId()).stream().map(cartItem1 -> {

            ProductResponse productResponse = ProductResponse.builder()
                    .isPopularProduct(cartItem1.getProduct().getIsPopularProduct())
                    .brandName(cartItem1.getProduct().getName())
                    .ingredient(cartItem1.getProduct().getIngredient())
                    .productId(cartItem1.getProduct().getId())
                    .bpomCode(cartItem1.getProduct().getBpomCode())
                    .categoryName(cartItem1.getProduct().getCategoryItem().getName())
                    .price(cartItem1.getProduct().getPrice())
                    .isPromo(cartItem1.getProduct().getIsPromo())
                    .discount(cartItem1.getProduct().getDiscount())
                    .stok(cartItem1.getProduct().getStok())
                    .originalPrice(cartItem1.getProduct().getOriginalPrice())
                    .thumbnailImage(cartItem1.getProduct().getThumbnailImage())
                    .productName(cartItem1.getProduct().getName())
                    .productDescription(cartItem1.getProduct().getDescription())
                    .build();

            // Memperbarui total
            BigDecimal itemSubtotal = cartItem1.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity()));

            total.updateAndGet(currentTotal -> currentTotal.add(itemSubtotal));


            return CartItemResponse.builder()
                    .product(productResponse)
                    .cartItemId(cartItem1.getId())
                    .quantity(cartItem1.getQuantity())
                    .build();
        }).toList();


        cartByUsernameUser.setTotal(total.get());

        cartRepository.save(cartByUsernameUser);

        return CartResponse.builder()
                .total(total.get())
                .cartItems(cartItemResponses)
                .build();


    }

    public CartResponse deleteCartByCartItem(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item keranjang tidak ditemukan"));

        cartItemRepository.deleteById(cartItem.getId());


        Cart cartByUsernameUser = cartRepository.findByUserUsernameUser(user.getUsernameUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));


        AtomicReference<BigDecimal> total = new AtomicReference(BigDecimal.ZERO);

        List<CartItemResponse> cartItemResponses = cartItemRepository.findAllByCartId(cartByUsernameUser.getId()).stream().map(cartItem1 -> {

            ProductResponse productResponse = ProductResponse.builder()
                    .isPopularProduct(cartItem1.getProduct().getIsPopularProduct())
                    .brandName(cartItem1.getProduct().getName())
                    .ingredient(cartItem1.getProduct().getIngredient())
                    .productId(cartItem1.getProduct().getId())
                    .bpomCode(cartItem1.getProduct().getBpomCode())
                    .categoryName(cartItem1.getProduct().getCategoryItem().getName())
                    .price(cartItem1.getProduct().getPrice())
                    .isPromo(cartItem1.getProduct().getIsPromo())
                    .discount(cartItem1.getProduct().getDiscount())
                    .stok(cartItem1.getProduct().getStok())
                    .originalPrice(cartItem1.getProduct().getOriginalPrice())
                    .thumbnailImage(cartItem1.getProduct().getThumbnailImage())
                    .productName(cartItem1.getProduct().getName())
                    .productDescription(cartItem1.getProduct().getDescription())
                    .build();

            // Memperbarui total
            BigDecimal itemSubTotal = cartItem1.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem1.getQuantity()));

            total.updateAndGet(currentTotal -> currentTotal.add(itemSubTotal));

            return CartItemResponse.builder()
                    .product(productResponse)
                    .cartItemId(cartItem1.getId())
                    .quantity(cartItem1.getQuantity())
                    .build();
        }).toList();

        cartByUsernameUser.setTotal(total.get());

        cartRepository.save(cartByUsernameUser);

        return CartResponse.builder()
                .total(total.get())
                .cartItems(cartItemResponses)
                .build();
    }


}
