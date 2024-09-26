package com.skincareMall.skincareMall.service.product;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.Product;
import com.skincareMall.skincareMall.entity.ProductImage;
import com.skincareMall.skincareMall.mapper.ProductMapper;
import com.skincareMall.skincareMall.model.product.request.*;
import com.skincareMall.skincareMall.model.product.response.*;
import com.skincareMall.skincareMall.repository.ProductImageRepository;
import com.skincareMall.skincareMall.repository.ProductRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

import static com.skincareMall.skincareMall.mapper.ProductMapper.*;

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
        product.setId(UUID.randomUUID().toString());
        product.setThumbnailImage(productRequest.getThumbnailImage());
        product.setName(productRequest.getProductName());
        product.setDescription(productRequest.getProductDescription());
        product.setIsPromo(productRequest.getIsPromo());
        product.setBpomCode(productRequest.getBpomCode());
        product.setSize(productRequest.getSize());
        product.setQuantity(productRequest.getQuantity());
        product.setBrands(productRequest.getBrands());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setOriginalPrice(productRequest.getOriginalPrice());
        product.setDiscount(productRequest.getDiscount());
        product.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));

        // Simpan product dan ambil kembali untuk memastikan ID sudah di-set
        productRepository.save(product);

        if (productRequest.getProductImages() != null) {
            // Membuat dan menyimpan ProductCategory yang terkait
            for (ProductImageRequest productImageRequest : productRequest.getProductImages()) {
                ProductImage productImage = new ProductImage();
                productImage.setImageUrl(productImageRequest.getImageUrl());
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }
        }

    }


    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> toProductResponse(product)).toList();
    }


    @Transactional(readOnly = true)
    public DetailProductResponse getDetailProduct(String productId) {
        List<ProductImageResponse> productImageResponses = productImageRepository.findAllByProductId(productId)
                .stream()
                .map(productImage -> toProductImageResponse(productImage))
                .toList();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product is not found"));

        return toDetailProductResponse(product, productImageResponses);
    }

    @Transactional
    public void deleteProductById(String productId) {
        productRepository.deleteById(productId);
    }


    @Transactional
    public DetailProductResponse updateProduct(Admin admin, String productId, UpdateProductRequest productRequest) {
        validationService.validate(productRequest);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));
        System.out.println(product);
        System.out.println(product.getId());
        System.out.println(product.getDescription());
        System.out.println(product.getPrice());

        if (Objects.nonNull(productRequest.getProductName())) {
            product.setName(productRequest.getProductName());
            System.out.println(productRequest.getProductName());

        }
        if (Objects.nonNull(productRequest.getProductDescription())) {
            product.setDescription(productRequest.getProductDescription());
            System.out.println(productRequest.getProductDescription());
        }
        if (Objects.nonNull(productRequest.getDiscount())) {
            product.setDiscount(productRequest.getDiscount());
            System.out.println(productRequest.getDiscount());
        }
        if (Objects.nonNull(productRequest.getBrands())) {
            product.setBrands(productRequest.getBrands());
            System.out.println(productRequest.getBrands());
        }
        if (Objects.nonNull(productRequest.getBpomCode())) {
            product.setBpomCode(productRequest.getBpomCode());
            System.out.println(productRequest.getBpomCode());
        }
        if (Objects.nonNull(productRequest.getProductImages())) {
//            List<ProductImage> productImages = productRequest.getProductImages().stream().map(productImageRequest -> toProductImage(productImageRequest)).toList();
            for (UpdateProductImageRequest updateProductImageRequest : productRequest.getProductImages()) {
                ProductImage productImageByIdAndProductId = productImageRepository.findByIdAndProductId(updateProductImageRequest.getId(), productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found"));
                if (productImageByIdAndProductId != null) {
                    productImageByIdAndProductId.setImageUrl(updateProductImageRequest.getImageUrl());
                    productImageRepository.save(productImageByIdAndProductId);
                }

            }
            System.out.println(productRequest.getProductImages());
        }
        if (Objects.nonNull(productRequest.getPrice())) {
            product.setPrice(productRequest.getPrice());
            System.out.println(productRequest.getPrice());
        }
        if (Objects.nonNull(productRequest.getCategory())) {
            product.setCategory(productRequest.getCategory());
            System.out.println(productRequest.getCategory());
        }
        if (Objects.nonNull(productRequest.getIsPromo())) {
            product.setIsPromo(productRequest.getIsPromo());
            System.out.println(productRequest.getIsPromo());
        }
        if (Objects.nonNull(productRequest.getOriginalPrice())) {
            product.setOriginalPrice(productRequest.getOriginalPrice());
            System.out.println(productRequest.getOriginalPrice());
        }
        if (Objects.nonNull(productRequest.getQuantity())) {
            product.setQuantity(productRequest.getQuantity());
            System.out.println(productRequest.getQuantity());
        }
        productRepository.save(product);
        List<ProductImageResponse> productImageResponse = product.getProductImages().stream().map(productImage -> toProductImageResponse(productImage)).toList();
        return toDetailProductResponse(product, productImageResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> search(SearchProductRequest searchProductRequest) {
        System.out.println(searchProductRequest.getName());
        Specification<Product> specification = (root, query, criteriaBuilder) -> {

            System.out.println(searchProductRequest.getName());
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(searchProductRequest.getName()) && !searchProductRequest.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchProductRequest.getName() + "%"));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(searchProductRequest.getPage(), searchProductRequest.getSize());
        Page<Product> products = productRepository.findAll(specification, pageable);
        List<ProductResponse> productResponses = products.getContent().stream().map(product -> toProductResponse(product)).toList();
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }


}
