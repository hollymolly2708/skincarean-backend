package com.skincareMall.skincareMall.service.product;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.ProductCategory;
import com.skincareMall.skincareMall.model.product.request.ProductCategoryRequest;
import com.skincareMall.skincareMall.model.product.request.ProductRequest;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.repository.ProductCategoryRepository;
import com.skincareMall.skincareMall.repository.ProductRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Transactional
    public ProductResponse createProduct(Admin admin, ProductRequest productRequest, List<ProductCategoryRequest> listProductCategoryRequest) {
        validationService.validate(productRequest);

        Product product = new Product();
        product.setAdmin(admin);
        product.setProductId(UUID.randomUUID().toString());
        product.setThumbnailImage(productRequest.getThumbnailImage());
        product.setName(productRequest.getProductName());
        product.setDescription(productRequest.getProductDescription());
        product.setIsPromo(productRequest.getIsPromo());
        product.setBpomCode(productRequest.getBpomCode());
        product.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));

        List<ProductCategory> categories = new ArrayList<>();

        for (ProductCategoryRequest request : listProductCategoryRequest) {
            validationService.validate(request);
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProduct(product);
            productCategory.setPrice(request.getPrice());
            productCategory.setDiscount(request.getDiscount());
            productCategory.setQuantity(request.getQuantity());
            productCategory.setOriginalPrice(request.getOriginalPrice());
            productCategory.setSize(request.getSize());
            categories.add(productCategory);
            productCategoryRepository.save(productCategory);

        }
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
