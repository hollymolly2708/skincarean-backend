package com.skincareMall.skincareMall.service.product;

import com.skincareMall.skincareMall.entity.*;
import com.skincareMall.skincareMall.mapper.ProductMapper;
import com.skincareMall.skincareMall.model.product.request.*;
import com.skincareMall.skincareMall.model.product.response.*;
import com.skincareMall.skincareMall.repository.BrandRepository;
import com.skincareMall.skincareMall.repository.CategoryItemRepository;
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
public class ProductServiceImpl {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryItemRepository categoryItemRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Transactional
    public void createProduct(Admin admin, CreateProductRequest createProductRequest) {
        validationService.validate(createProductRequest);

        Brand brand = brandRepository.findById(createProductRequest.getBrandId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand tidak ditemukan"));
        CategoryItem categoryItem = categoryItemRepository.findById(createProductRequest.getCategoryItemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category tidak ditemukan"));


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
        product.setIngredient(createProductRequest.getIngredient());
        product.setSize(createProductRequest.getSize());
        product.setStok(createProductRequest.getStok());
        product.setIsPopularProduct(createProductRequest.getIsPopularProduct());
        product.setBrand(brand);
        product.setPrice(requestOriginalPrice.subtract(requestOriginalPrice.multiply(requestDiscount.divide(BigDecimal.valueOf(100)))));
        product.setCategoryItem(categoryItem);
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
    public List<ProductResponse> getAllPopularProducts() {
        List<ProductResponse> popularProducts = productRepository.findAllByIsPopularProduct(true).stream().map(
                products ->
                        ProductMapper.toProductResponse(products)
        ).toList();

        return popularProducts;
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


        if (Objects.nonNull(productRequest.getProductName())) {
            product.setName(productRequest.getProductName());
            System.out.println(productRequest.getProductName());

        }
        if (Objects.nonNull(productRequest.getProductDescription())) {
            product.setDescription(productRequest.getProductDescription());
            System.out.println(productRequest.getProductDescription());
        }
        if (Objects.nonNull(productRequest.getDiscount())) {
            if (Objects.nonNull(productRequest.getOriginalPrice())) {
                product.setDiscount(productRequest.getDiscount());
                product.setPrice(productRequest.getOriginalPrice().subtract(productRequest.getOriginalPrice().multiply(productRequest.getDiscount().divide(BigDecimal.valueOf(100)))));
                System.out.println(productRequest.getDiscount());
            } else {
                product.setDiscount(productRequest.getDiscount());
                product.setPrice(product.getOriginalPrice().subtract(product.getOriginalPrice().multiply(productRequest.getDiscount().divide(BigDecimal.valueOf(100)))));
            }

        }
        if(Objects.nonNull(productRequest.getSize())){
            product.setSize(productRequest.getSize());
        }

        if (Objects.nonNull(productRequest.getOriginalPrice())) {
            product.setOriginalPrice(productRequest.getOriginalPrice());
            product.setPrice(productRequest.getOriginalPrice());
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

        if (Objects.nonNull(productRequest.getCategoryItemId())) {
            CategoryItem categoryItem = categoryItemRepository.findById(productRequest.getCategoryItemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category tidak ditemukan"));
            product.setCategoryItem(categoryItem);
        }
        if (Objects.nonNull(productRequest.getIsPromo())) {
            product.setIsPromo(productRequest.getIsPromo());
            System.out.println(productRequest.getIsPromo());
        }

        if (Objects.nonNull(productRequest.getStok())) {
            product.setStok(productRequest.getStok());
            System.out.println(productRequest.getStok());
        }

        if (Objects.nonNull(productRequest.getIngredient())) {
            product.setIngredient(productRequest.getIngredient());
        }

        if (Objects.nonNull(productRequest.getIsPopularProduct())) {
            product.setIsPopularProduct(productRequest.getIsPopularProduct());
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
        List<ProductResponse> productResponses = products.getContent().stream().map(ProductMapper::toProductResponse).toList();
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }


}
