package com.skincareMall.skincareMall.model.cart.response;


import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private ProductResponse product;
    private Long quantity;

}
