package com.skincareMall.skincareMall.service.product;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.productVariant;
import com.skincareMall.skincareMall.entity.ProductVariantImage;
import com.skincareMall.skincareMall.model.product.request.ProductVariantRequest;
import com.skincareMall.skincareMall.model.product.request.ProductVariantImageRequest;
import com.skincareMall.skincareMall.model.product.request.ProductRequest;
import com.skincareMall.skincareMall.repository.ProductVariantRepository;
import com.skincareMall.skincareMall.repository.ProductVariantImageRepository;
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
    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ProductVariantImageRepository productVariantImageRepository;

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
        product.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));

        // Simpan product dan ambil kembali untuk memastikan ID sudah di-set
        productRepository.save(product);

        if (productRequest.getProductVariants() != null) {
            // Membuat dan menyimpan ProductCategory yang terkait
            for (ProductVariantRequest variantRequest : productRequest.getProductVariants()) {
                productVariant productVariant = new productVariant();
                productVariant.setQuantity(variantRequest.getQuantity());
                productVariant.setProduct(product); // Referensi ke product yang sudah disimpan
                productVariant.setSize(variantRequest.getSize());
                productVariant.setDiscount(variantRequest.getDiscount());
                productVariant.setPrice(variantRequest.getPrice());
                productVariant.setOriginalPrice(variantRequest.getOriginalPrice());
                productVariantRepository.save(productVariant);

                if (variantRequest.getProductVariantImages() != null) {
                    for (ProductVariantImageRequest productVariantImageRequest : variantRequest.getProductVariantImages()) {
                        ProductVariantImage productVariantImage = new ProductVariantImage();
                        productVariantImage.setProductVariant(productVariant);
                        productVariantImage.setImageUrl(productVariantImageRequest.getImageUrl());
                        productVariantImageRepository.save(productVariantImage);
                    }
                }
            }
        }

    }


//    public List<ProductResponse> getAllProducts(){
//
//    }

}
