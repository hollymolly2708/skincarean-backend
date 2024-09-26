package com.skincareMall.skincareMall.mapper;

import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.ProductImage;
import com.skincareMall.skincareMall.model.product.response.DetailProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductImageResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;

import java.util.List;

public class ProductMapper {
    private ProductMapper(){

    }
    public static ProductImageResponse toProductImageResponse(ProductImage productImage){
        return ProductImageResponse.builder()
                .imageUrl(productImage.getImageUrl())
                .id(productImage.getId())
                .build();
    }

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .isPromo(product.getIsPromo())
                .bpomCode(product.getBpomCode())
                .originalPrice(product.getOriginalPrice())
                .price(product.getPrice())
                .stok(product.getStok())
                .brands(product.getBrands())
                .category(product.getCategory())
                .size(product.getSize())
                .discount(product.getDiscount())
                .thumbnailImage(product.getThumbnailImage())
                .build();
    }

    public static DetailProductResponse toDetailProductResponse(Product product, List<ProductImageResponse> productImageResponse){
        return DetailProductResponse.builder()
                .productName(product.getName())
                .productImage(productImageResponse)
                .productId(product.getId())
                .productDescription(product.getDescription())
                .brands(product.getBrands())
                .bpomCode(product.getBpomCode())
                .price(product.getPrice())
                .isPromo(product.getIsPromo())
                .size(product.getSize())
                .category(product.getCategory())
                .thumbnailImage(product.getThumbnailImage())
                .originalPrice(product.getOriginalPrice())
                .discount(product.getDiscount())
                .stok(product.getStok())
                .build();
    }
}
