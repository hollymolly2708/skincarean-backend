package com.skincareMall.skincareMall.mapper;

import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.ProductImage;
import com.skincareMall.skincareMall.entity.ProductVariant;
import com.skincareMall.skincareMall.model.product.response.DetailProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductImageResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductVariantResponse;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    private ProductMapper() {

    }

    public static ProductImageResponse toProductImageResponse(ProductImage productImage) {
        return ProductImageResponse.builder()
                .imageUrl(productImage.getImageUrl())
                .id(productImage.getId())
                .build();
    }

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .isPromo(product.getIsPromo())
                .isPopularProduct(product.getIsPopularProduct())
                .maxPrice(product.getProductVariants().stream()
                        .map(ProductVariant::getPrice) // Mengambil harga sebagai BigDecimal// Mengonversi BigDecimal ke double
                        .max(BigDecimal::compareTo) // Mendapatkan nilai maksimum
                        .orElse(BigDecimal.ZERO))
                .minPrice(product.getProductVariants().stream()
                        .map(ProductVariant::getPrice)
                        .min(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO))
                .brandName(product.getBrand().getName())
                .categoryName(product.getCategoryItem().getName())
                .thumbnailImage(product.getThumbnailImage())
                .build();
    }

    public static DetailProductResponse toDetailProductResponse(Product product, List<ProductVariantResponse> productVariantResponses) {

        return DetailProductResponse.builder()
                .productName(product.getName())
                .productId(product.getId())
                .ingredient(product.getIngredient())
                .isPopularProduct(product.getIsPopularProduct())
                .productDescription(product.getDescription())
                .brandName(product.getBrand().getName())
                .bpomCode(product.getBpomCode())
                .isPromo(product.getIsPromo())
                .categoryName(product.getCategoryItem().getName())
                .thumbnailImage(product.getThumbnailImage())
                .minPrice(product.getProductVariants().stream().map(ProductVariant::getPrice).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO))
                .maxPrice(product.getProductVariants().stream().map(ProductVariant::getPrice).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO))
                .productVariants(productVariantResponses)
                .totalStok(product.getTotalStok())
                .build();
    }

    public static List<ProductVariantResponse> productVariantsToProductVariantResponses(List<ProductVariant> productVariants) {
        return productVariants.stream().map(productVariant -> {
            List<ProductImageResponse> productImageResponses = productVariant.getProductImages().stream().map(productImage ->
                    ProductImageResponse.builder()
                            .id(productImage.getId())
                            .imageUrl(productImage.getImageUrl())
                            .build()
            ).toList();
            return ProductVariantResponse.builder()
                    .id(productVariant.getId())
                    .stok(productVariant.getStok())
                    .size(productVariant.getSize())
                    .productImages(productImageResponses)
                    .price(productVariant.getPrice())
                    .originalPrice(productVariant.getOriginalPrice())
                    .discount(productVariant.getDiscount())
                    .build();
        }).toList();

    }
}
