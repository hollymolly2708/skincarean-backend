package com.skincareMall.skincareMall.mapper;

import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.ProductVariantImage;
import com.skincareMall.skincareMall.entity.ProductVariant;
import com.skincareMall.skincareMall.model.product.response.DetailProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductVariantImageResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductVariantResponse;

import java.math.BigDecimal;
import java.util.List;

public class ProductMapper {
    private ProductMapper() {

    }

    public static ProductVariantImageResponse toProductImageResponse(ProductVariantImage productVariantImage) {
        return ProductVariantImageResponse.builder()
                .imageUrl(productVariantImage.getImageUrl())
                .id(productVariantImage.getId())
                .build();
    }

    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .isPromo(product.getIsPromo())
                .isPopularProduct(product.getIsPopularProduct())
                .firstOriginalPrice(product.getProductVariants().stream().map(ProductVariant::getOriginalPrice).findFirst().orElse(BigDecimal.ZERO))
                .firstDiscount(product.getProductVariants().stream().map(ProductVariant::getDiscount).findFirst().orElse(BigDecimal.ZERO))
                .firstPrice(product.getProductVariants().stream().map(ProductVariant::getPrice).findFirst().orElse(BigDecimal.ZERO))
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
            List<ProductVariantImageResponse> productVariantImageResponse = productVariant.getProductVariantImages().stream().map(productImage ->
                    ProductVariantImageResponse.builder()
                            .id(productImage.getId())
                            .imageUrl(productImage.getImageUrl())
                            .build()
            ).toList();
            return ProductVariantResponse.builder()
                    .id(productVariant.getId())
                    .stok(productVariant.getStok())
                    .size(productVariant.getSize())
                    .productVariantImages(productVariantImageResponse)
                    .price(productVariant.getPrice())
                    .thumbnailVariantImage(productVariant.getThumbnailVariantImage())
                    .originalPrice(productVariant.getOriginalPrice())
                    .discount(productVariant.getDiscount())
                    .build();
        }).toList();

    }
}
