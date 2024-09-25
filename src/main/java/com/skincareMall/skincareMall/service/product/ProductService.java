package com.skincareMall.skincareMall.service.product;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.ProductImage;
import com.skincareMall.skincareMall.model.product.request.ProductImageRequest;
import com.skincareMall.skincareMall.model.product.request.ProductRequest;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.repository.ProductImageRepository;
import com.skincareMall.skincareMall.repository.ProductRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Transactional
    public void createProduct(Admin admin, ProductRequest productRequest) {
        validationService.validate(productRequest);

        // Membuat dan menyimpan product terlebih dahulu
        Product product = new Product();
        product.setAdmin(admin);
        product.setProductId(UUID.randomUUID().toString());
        product.setThumbnailImage(productRequest.getThumbnailImage());
        product.setName(productRequest.getProductName());
        product.setDescription(productRequest.getProductDescription());
        product.setIsPromo(productRequest.getIsPromo());
        product.setBpomCode(productRequest.getBpomCode());
        product.setSize(productRequest.getSize());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setOriginalPrice(productRequest.getOriginalPrice());
        product.setDiscount(productRequest.getDiscount());
        product.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));

        // Simpan product dan ambil kembali untuk memastikan ID sudah di-set
        productRepository.save(product);

        if (productRequest.getProductImage() != null) {
            // Membuat dan menyimpan ProductCategory yang terkait

            for (ProductImageRequest productImageRequest : productRequest.getProductImage()) {
                ProductImage productImage = new ProductImage();
                productImage.setImageUrl(productImageRequest.getImageUrl());
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }
        }

    }

    private ProductResponse toProductResponse(Product product){
        return ProductResponse.builder()
                .productName(product.getName())
                .productDescription(product.getDescription())
                .isPromo(product.getIsPromo())
                .bpomCode(product.getBpomCode())
                .originalPrice(product.getOriginalPrice())
                .price(product.getPrice())
                .size(product.getSize())
                .discount(product.getDiscount())
                .thumbnailImage(product.getThumbnailImage())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
     return  products.stream().map(this::toProductResponse).toList();
    }

}
