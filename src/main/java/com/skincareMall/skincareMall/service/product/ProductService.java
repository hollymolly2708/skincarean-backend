package com.skincareMall.skincareMall.service.product;

import com.skincareMall.skincareMall.entity.*;
import com.skincareMall.skincareMall.model.product.request.*;
import com.skincareMall.skincareMall.model.product.response.*;
import com.skincareMall.skincareMall.repository.BrandRepository;
import com.skincareMall.skincareMall.repository.CategoryRepository;
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

import java.math.BigDecimal;
import java.util.*;

import static com.skincareMall.skincareMall.mapper.ProductMapper.*;

@Service
public class ProductService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Transactional
    public void createProduct(Admin admin, CreateProductRequest createProductRequest) {
        validationService.validate(createProductRequest);

        Brand brand = brandRepository.findById(createProductRequest.getBrandId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand tidak ditemukan"));
        Category category = categoryRepository.findById(createProductRequest.getCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category tidak ditemukan"));


        // Membuat dan menyimpan product terlebih dahulu
        Product product = new Product();
        BigDecimal requestOriginalPrice = createProductRequest.getOriginalPrice();
        BigDecimal requestDiscount = createProductRequest.getDiscount();
        product.setAdmin(admin);
        product.setId(UUID.randomUUID().toString());
        product.setThumbnailImage(createProductRequest.getThumbnailImage());
        product.setName(createProductRequest.getProductName());
        product.setDescription(createProductRequest.getProductDescription());
        product.setIsPromo(createProductRequest.getIsPromo());
        product.setBpomCode(createProductRequest.getBpomCode());
        product.setSize(createProductRequest.getSize());
        product.setStok(createProductRequest.getStok());
        product.setBrand(brand);
        product.setPrice(requestOriginalPrice.subtract(requestOriginalPrice.multiply(requestDiscount.divide(BigDecimal.valueOf(100)))));
        product.setCategory(category);
        product.setOriginalPrice(createProductRequest.getOriginalPrice());
        product.setDiscount(createProductRequest.getDiscount());
        product.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));

        // Simpan product dan ambil kembali untuk memastikan ID sudah di-set
        productRepository.save(product);

        if (createProductRequest.getProductImages() != null) {
            // Membuat dan menyimpan ProductCategory yang terkait
            for (CreateProductImageRequest createProductImageRequest : createProductRequest.getProductImages()) {
                ProductImage productImage = new ProductImage();
                productImage.setImageUrl(createProductImageRequest.getImageUrl());
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
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));

        return toDetailProductResponse(product, productImageResponses);
    }

    @Transactional
    public void deleteProductById(String productId) {
        productRepository.deleteById(productId);
    }


    @Transactional
    public DetailProductResponse updateProduct(Admin admin, String productId, UpdateProductRequest productRequest) {
        validationService.validate(productRequest);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));


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
            product.setPrice(productRequest.getOriginalPrice().subtract(productRequest.getOriginalPrice().multiply(productRequest.getDiscount().divide(BigDecimal.valueOf(100)))));
            System.out.println(productRequest.getDiscount());
        }
        if (Objects.nonNull(productRequest.getBrandId())) {
            Brand brand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand tidak ditemukan"));
            product.setBrand(brand);
        }
        if (Objects.nonNull(productRequest.getBpomCode())) {
            product.setBpomCode(productRequest.getBpomCode());
            System.out.println(productRequest.getBpomCode());
        }
        if (Objects.nonNull(productRequest.getProductImages())) {
//            List<ProductImage> productImages = productRequest.getProductImages().stream().map(productImageRequest -> toProductImage(productImageRequest)).toList();
            for (UpdateProductImageRequest updateProductImageRequest : productRequest.getProductImages()) {
                ProductImage productImageByIdAndProductId = productImageRepository.findByIdAndProductId(updateProductImageRequest.getId(), productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk Image tidak ditemukan"));
                if (productImageByIdAndProductId != null) {
                    productImageByIdAndProductId.setImageUrl(updateProductImageRequest.getImageUrl());
                    productImageRepository.save(productImageByIdAndProductId);
                }

            }
            System.out.println(productRequest.getProductImages());
        }

        if (Objects.nonNull(productRequest.getCategoryId())) {
            Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category tidak ditemukan"));
            product.setCategory(category);
        }
        if (Objects.nonNull(productRequest.getIsPromo())) {
            product.setIsPromo(productRequest.getIsPromo());
            System.out.println(productRequest.getIsPromo());
        }
        if (Objects.nonNull(productRequest.getOriginalPrice())) {
            product.setOriginalPrice(productRequest.getOriginalPrice());
            System.out.println(productRequest.getOriginalPrice());
        }
        if (Objects.nonNull(productRequest.getStok())) {
            product.setStok(productRequest.getStok());
            System.out.println(productRequest.getStok());
        }
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());

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
