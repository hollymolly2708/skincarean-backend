package com.skincareMall.skincareMall.service.cart;

import com.skincareMall.skincareMall.entity.*;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.cart.response.CartItemResponse;
import com.skincareMall.skincareMall.model.cart.response.CartProductResponse;
import com.skincareMall.skincareMall.model.cart.response.CartProductVariantResponse;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.repository.*;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;


    @Override
    @Transactional
    public void addProductToCart(User user, CreateCartRequest request) {
        validationService.validate(request);
        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser()).orElse(null);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Tidak ditemukan"));

        ProductVariant productVariant = productVariantRepository.findByIdAndProductId(request.getProductVariantId(), request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Varian produk tidak ditemukan"));

        CartItem cartItem = cartItemRepository.findByCartAndProductAndProductVariant(cart, product, productVariant).orElse(null);


        BigDecimal productTotal = productVariant.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartRepository.save(newCart);


            CartItem newCartItem = new CartItem();
            newCartItem.setProductVariant(productVariant);
            newCartItem.setQuantity(request.getQuantity());
            newCartItem.setTotal(productTotal);
            newCartItem.setProductVariant(productVariant);
            newCartItem.setIsActive(false);
            newCartItem.setCart(cart);
            cartItemRepository.save(newCartItem);


        } else {
            if (cartItem == null) {
                CartItem newCartItem = new CartItem();
                newCartItem.setCart(cart);
                newCartItem.setProduct(product);
                newCartItem.setIsActive(false);
                newCartItem.setProductVariant(productVariant);
                newCartItem.setQuantity(request.getQuantity());
                newCartItem.setTotal(productTotal);
                cartItemRepository.save(newCartItem);
            } else {
                cartItem.setIsActive(cartItem.getIsActive());
                cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
                cartItem.setTotal(cartItem.getTotal().add(productTotal));
                cartItemRepository.save(cartItem);
            }

        }


    }


    @Override
    @Transactional(readOnly = true)
    public CartResponse getActiveCartItem(User user) {
        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));
        List<CartItem> cartItems = cartItemRepository.findByCartAndIsActive(cart, true);
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);


        List<CartItemResponse> cartItemResponses = cartItems.stream().map(cartItem -> {
            totalPrice.updateAndGet(currentTotal -> currentTotal.add(cartItem.getTotal()));
            BigDecimal itemSubTotal = cartItem.getProductVariant().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            return CartItemResponse.builder()
                    .id(cartItem.getId())
                    .quantity(cartItem.getQuantity())
                    .total(itemSubTotal)
                    .isActive(cartItem.getIsActive())
                    .product(CartProductResponse.builder()
                            .productName(cartItem.getProduct().getName())
                            .thumbnailImage(cartItem.getProduct().getThumbnailImage())
                            .brandName(cartItem.getProduct().getBrand().getName())
                            .productId(cartItem.getProduct().getId())
                            .categoryName(cartItem.getProduct().getCategoryItem().getName())
                            .build())
                    .productVariant(CartProductVariantResponse.builder()
                            .price(cartItem.getProductVariant().getPrice())
                            .size(cartItem.getProductVariant().getSize())
                            .thumbnailVariantImage(cartItem.getProductVariant().getThumbnailVariantImage())
                            .discount(cartItem.getProductVariant().getDiscount())
                            .originalPrice(cartItem.getProductVariant().getOriginalPrice())
                            .id(cartItem.getProductVariant().getId())
                            .build())

                    .build();
        }).collect(Collectors.toList());

        long totalQuantity = cartItemResponses.stream().mapToLong(CartItemResponse::getQuantity).sum();

        return CartResponse.builder()
                .id(cart.getId())
                .cartItems(cartItemResponses)
                .totalPrice(totalPrice.get())
                .quantity(totalQuantity)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getAllCarts(User user) {
        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));

        List<CartItem> activeCartItem = cartItemRepository.findByCartAndIsActive(cart, true);

        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream().map(cartItem -> {


            BigDecimal itemSubTotal = cartItem.getProductVariant().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));


            return CartItemResponse.builder()
                    .id(cartItem.getId())
                    .total(itemSubTotal)
                    .isActive(cartItem.getIsActive())
                    .quantity(cartItem.getQuantity())
                    .product(CartProductResponse.builder()
                            .productName(cartItem.getProduct().getName())
                            .thumbnailImage(cartItem.getProduct().getThumbnailImage())
                            .brandName(cartItem.getProduct().getBrand().getName())
                            .productId(cartItem.getProduct().getId())
                            .categoryName(cartItem.getProduct().getCategoryItem().getName())
                            .build())
                    .productVariant(CartProductVariantResponse.builder()
                            .size(cartItem.getProductVariant().getSize())
                            .price(cartItem.getProductVariant().getPrice())
                            .thumbnailVariantImage(cartItem.getProductVariant().getThumbnailVariantImage())
                            .originalPrice(cartItem.getProductVariant().getOriginalPrice())
                            .discount(cartItem.getProductVariant().getDiscount())
                            .id(cartItem.getProductVariant().getId())
                            .build())
                    .build();
        }).collect(Collectors.toList());

        BigDecimal totalPrice = activeCartItem.stream()
                .map(CartItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalQuantity = activeCartItem.stream().mapToLong(CartItem::getQuantity).sum();


        return CartResponse.builder()
                .id(cart.getId())
                .cartItems(cartItemResponses)
                .totalPrice(totalPrice)
                .quantity(totalQuantity)
                .build();
    }


    @Override
    @Transactional
    public void activeCartItem(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item tidak ditemukan"));
        cartItem.setIsActive(!cartItem.getIsActive());
        cartItemRepository.save(cartItem);

    }

    @Override
    @Transactional
    public void plusQuantity(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item tidak ditemukan"));

        // Increase quantity and update total
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        BigDecimal newSubTotal = cartItem.getProductVariant().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        cartItem.setTotal(newSubTotal);
        cartItemRepository.save(cartItem);

    }


    @Override
    @Transactional
    public void minusQuantity(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item tidak ditemukan"));

        if (cartItem.getQuantity() <= 1) {
            cartItemRepository.delete(cartItem);
        } else {
            // Decrease quantity and update total
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            BigDecimal newSubTotal = cartItem.getProductVariant().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            cartItem.setTotal(newSubTotal);
            cartItemRepository.save(cartItem);
        }
    }


    @Override
    @Transactional
    public void deleteCartItemFromCart(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item tidak ditemukan"));
        cartItemRepository.delete(cartItem);
    }


    @Override
    @Transactional
    public void deleteAllCartItems(User user) {
        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));
        cartItemRepository.deleteAll(cart.getCartItems());
    }
}
