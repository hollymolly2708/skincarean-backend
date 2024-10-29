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
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{

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

    @Override
    @Transactional
    public void addToCartFromDetailProduct(User user, CreateCartRequest request) {
        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser()).orElse(null);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Tidak ditemukan"));
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        BigDecimal productTotal = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

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
            newCartItem.setCart(newCart);
            cartItemRepository.save(newCartItem);

        } else {
            if (cartItem == null) {
                CartItem newCartItem = new CartItem();
                newCartItem.setCart(cart);
                newCartItem.setProduct(product);

                newCartItem.setQuantity(request.getQuantity());
                newCartItem.setTotal(productTotal);
                cartItemRepository.save(newCartItem);
            } else {
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
    public CartResponse getAllCarts(User user) {
        Cart cart = cartRepository.findByUserUsernameUser(user.getUsernameUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Keranjang tidak ditemukan"));

        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream().map(cartItem -> {
            BigDecimal itemSubtotal = cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            return CartItemResponse.builder()
                    .id(cartItem.getId())
                    .product(ProductResponse.builder()
                            .size(cartItem.getProduct().getSize())
                            .stok(cartItem.getProduct().getStok())
                            .isPromo(cartItem.getProduct().getIsPromo())
                            .productName(cartItem.getProduct().getName())
                            .thumbnailImage(cartItem.getProduct().getThumbnailImage())
                            .originalPrice(cartItem.getProduct().getOriginalPrice())
                            .price(cartItem.getProduct().getPrice())
                            .isPopularProduct(cartItem.getProduct().getIsPopularProduct())
                            .brandName(cartItem.getProduct().getBrand().getName())
                            .productId(cartItem.getProduct().getId())
                            .bpomCode(cartItem.getProduct().getBpomCode())
                            .discount(cartItem.getProduct().getDiscount())
                            .categoryName(cartItem.getProduct().getCategoryItem().getName())
                            .ingredient(cartItem.getProduct().getIngredient())
                            .productDescription(cartItem.getProduct().getDescription())
                            .build())
                    .total(itemSubtotal)
                    .quantity(cartItem.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        return CartResponse.builder()
                .id(cart.getId())
                .cartItems(cartItemResponses)
                .totalPrice(cart.getTotalPrice())
                .quantity(cart.getQuantity())
                .build();
    }


    @Override
    @Transactional
    public void plusQuantity(User user, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item tidak ditemukan"));

        // Increase quantity and update total
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        BigDecimal newSubTotal = cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        cartItem.setTotal(newSubTotal);
        cartItemRepository.save(cartItem);

        // Update cart total price and quantity
        Cart cart = cartItem.getCart();
        cart.setQuantity(cart.getQuantity() + 1);
        cart.setTotalPrice(cart.getTotalPrice().add(cartItem.getProduct().getPrice()));
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
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getProduct().getPrice()));
            cartRepository.save(cart);
            cartItemRepository.delete(cartItem);
        } else {
            // Decrease quantity and update total
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            BigDecimal newSubTotal = cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            cartItem.setTotal(newSubTotal);
            cartItemRepository.save(cartItem);

            // Update cart total price and quantity
            Cart cart = cartItem.getCart();
            cart.setQuantity(cart.getQuantity() - 1);
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getProduct().getPrice()));
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
