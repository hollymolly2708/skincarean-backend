package com.skincareMall.skincareMall.service.cart;

import com.skincareMall.skincareMall.entity.*;
import com.skincareMall.skincareMall.model.cart.request.CreateCartRequest;
import com.skincareMall.skincareMall.model.cart.response.CartItemResponse;
import com.skincareMall.skincareMall.model.cart.response.CartProductResponse;
import com.skincareMall.skincareMall.model.cart.response.CartProductVariantResponse;
import com.skincareMall.skincareMall.model.cart.response.CartResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductVariantResponse;
import com.skincareMall.skincareMall.repository.*;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
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
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        ProductVariant productVariant = productVariantRepository.findByIdAndProductId(request.getProductVariantId(), request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Varian produk tidak ditemukan"));


        BigDecimal productTotal = productVariant.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setQuantity(request.getQuantity());
            newCart.setTotalPrice(productTotal);
            cartRepository.save(newCart);

            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(request.getQuantity());
            newCartItem.setTotal(productTotal);
            newCartItem.setProductVariant(productVariant);
            newCartItem.setCart(newCart);
            newCartItem.setIsActive(false);
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

            // Update total cart
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice().add(productTotal));
            cartRepository.save(cart);
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
                    .product(CartProductResponse.builder()
                            .productName(cartItem.getProduct().getName())
                            .thumbnailImage(cartItem.getProduct().getThumbnailImage())
                            .brandName(cartItem.getProduct().getBrand().getName())
                            .productId(cartItem.getProduct().getId())
                            .categoryName(cartItem.getProduct().getCategoryItem().getName())
                            .build())
                    .total(itemSubTotal)
                    .isActive(cartItem.getIsActive())
                    .productVariant(CartProductVariantResponse.builder()
                            .price(cartItem.getProductVariant().getPrice())
                            .size(cartItem.getProductVariant().getSize())
                            .discount(cartItem.getProductVariant().getDiscount())
                            .originalPrice(cartItem.getProductVariant().getOriginalPrice())
                            .id(cartItem.getProductVariant().getId())
                            .build())
                    .quantity(cartItem.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        return CartResponse.builder()
                .id(cart.getId())
                .cartItems(cartItemResponses)
                .totalPrice(totalPrice.get())
                .quantity(cart.getQuantity())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getAllCarts(User user) {
        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));
        List<CartItem> cartItemByIsActive = cartItemRepository.findByCartAndIsActive(cart, true);

        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);

        cartItemByIsActive.forEach(cartItem -> {
            totalPrice.updateAndGet(currentTotal -> currentTotal.add(cartItem.getTotal()));
        });


        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream().map(cartItem -> {


            BigDecimal itemSubTotal = cartItem.getProductVariant().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));


            return CartItemResponse.builder()
                    .id(cartItem.getId())
                    .product(CartProductResponse.builder()
                            .productName(cartItem.getProduct().getName())
                            .thumbnailImage(cartItem.getProduct().getThumbnailImage())
                            .brandName(cartItem.getProduct().getBrand().getName())
                            .productId(cartItem.getProduct().getId())
                            .categoryName(cartItem.getProduct().getCategoryItem().getName())
                            .build())
                    .total(itemSubTotal)
                    .isActive(cartItem.getIsActive())
                    .quantity(cartItem.getQuantity())
                    .productVariant(CartProductVariantResponse.builder()
                            .size(cartItem.getProductVariant().getSize())
                            .price(cartItem.getProductVariant().getPrice())
                            .originalPrice(cartItem.getProductVariant().getOriginalPrice())
                            .discount(cartItem.getProductVariant().getDiscount())
                            .id(cartItem.getProductVariant().getId())
                            .build())
                    .build();
        }).collect(Collectors.toList());

        return CartResponse.builder()
                .id(cart.getId())
                .cartItems(cartItemResponses)
                .totalPrice(totalPrice.get())
                .quantity(cart.getQuantity())
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

        // Update cart total price and quantity
        Cart cart = cartItem.getCart();
        cart.setQuantity(cart.getQuantity() + 1);
        cart.setTotalPrice(cart.getTotalPrice().add(cartItem.getProductVariant().getPrice()));
        cartRepository.save(cart);
    }


    @Override
    @Transactional
    public void minusQuantity(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item tidak ditemukan"));

        if (cartItem.getQuantity() <= 1) {
            // Remove item if quantity is 1 or less
            Cart cart = cartItem.getCart();
            cart.setQuantity(cart.getQuantity() - 1);
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getProductVariant().getPrice()));
            cartRepository.save(cart);
            cartItemRepository.delete(cartItem);
        } else {
            // Decrease quantity and update total
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            BigDecimal newSubTotal = cartItem.getProductVariant().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            cartItem.setTotal(newSubTotal);
            cartItemRepository.save(cartItem);

            // Update cart total price and quantity
            Cart cart = cartItem.getCart();
            cart.setQuantity(cart.getQuantity() - 1);
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getProductVariant().getPrice()));
            cartRepository.save(cart);
        }
    }


    @Override
    @Transactional
    public void deleteCartItemFromCart(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item tidak ditemukan"));

        Cart cart = cartItem.getCart();
        cart.setQuantity(cart.getQuantity() - cartItem.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getTotal()));
        cartRepository.save(cart);
        cartItemRepository.delete(cartItem);
    }


    @Override
    @Transactional
    public void deleteAllCartItems(User user) {
        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));

        cart.setTotalPrice(BigDecimal.ZERO);
        cart.setQuantity(0L);
        cartItemRepository.deleteAll(cart.getCartItems());
        cartRepository.save(cart);
    }
}
