package com.skincareMall.skincareMall.service.product;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.model.product.request.CreateProductRequest;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.repository.ProductRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(Admin admin, CreateProductRequest createProductRequest) {
        validationService.validate(createProductRequest);

        Product product = new Product();
        product.setAdmin(admin);
        product.setProductId(UUID.randomUUID().toString());
        product.setThumbnailImage(createProductRequest.getThumbnailImage());
        product.setName(createProductRequest.getProductName());
        product.setDescription(createProductRequest.getProductDescription());
        product.setIsPromo(createProductRequest.getIsPromo());
        product.setBpomCode(createProductRequest.getBpomCode());
        product.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));

        productRepository.save(product);

        return ProductResponse.builder()
                .productDescription(product.getDescription())
                .productName(product.getName())
                .isPromo(product.getIsPromo())
                .thumbnailImage(product.getThumbnailImage())
                .addedByAdmin(product.getAdmin().getUsernameAdmin())
                .build();
    }
}
