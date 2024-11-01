package com.skincareMall.skincareMall.service.product;

import com.skincareMall.skincareMall.entity.*;
import com.skincareMall.skincareMall.mapper.ProductMapper;
import com.skincareMall.skincareMall.model.product.request.*;
import com.skincareMall.skincareMall.model.product.response.DetailProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductImageResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductVariantResponse;
import com.skincareMall.skincareMall.repository.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static com.skincareMall.skincareMall.mapper.ProductMapper.toDetailProductResponse;
import static com.skincareMall.skincareMall.mapper.ProductMapper.toProductImageResponse;

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

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Transactional
    public void createProduct(Admin admin, CreateProductRequest createProductRequest) {
        validationService.validate(createProductRequest);

        Brand brand = brandRepository.findById(createProductRequest.getBrandId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand tidak ditemukan"));
        CategoryItem categoryItem = categoryItemRepository.findById(createProductRequest.getCategoryItemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category tidak ditemukan"));


        // Membuat dan menyimpan product terlebih dahulu
        Product product = new Product();
        product.setAdmin(admin);
        product.setId(UUID.randomUUID().toString());
        product.setThumbnailImage(createProductRequest.getThumbnailImage());
        product.setName(createProductRequest.getProductName());
        product.setDescription(createProductRequest.getProductDescription());
        product.setIsPromo(createProductRequest.getIsPromo());
        product.setBpomCode(createProductRequest.getBpomCode());
        product.setIngredient(createProductRequest.getIngredient());
        product.setIsPopularProduct(createProductRequest.getIsPopularProduct());
        product.setBrand(brand);
        product.setCategoryItem(categoryItem);
        product.setCreatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp(System.currentTimeMillis()));

        // Simpan product dan ambil kembali untuk memastikan ID sudah di-set
        productRepository.save(product);

        if (createProductRequest.getCreateProductVariantRequests() != null) {
            for (CreateProductVariantRequest createProductVariantRequest : createProductRequest.getCreateProductVariantRequests()) {
                ProductVariant productVariant = new ProductVariant();
                productVariant.setProduct(product);
                productVariant.setCreatedAt(Utilities.changeFormatToTimeStamp());
                productVariant.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
                productVariant.setSize(createProductVariantRequest.getSize());
                productVariant.setStok(createProductVariantRequest.getStok());
                productVariant.setDiscount(createProductVariantRequest.getDiscount());
                productVariant.setOriginalPrice(createProductVariantRequest.getOriginalPrice());
                productVariant.setDiscount(createProductVariantRequest.getDiscount());
                productVariant.setPrice(createProductVariantRequest.getOriginalPrice().subtract(createProductVariantRequest.getOriginalPrice().multiply(createProductVariantRequest.getDiscount().divide(BigDecimal.valueOf(100)))));
                productVariantRepository.save(productVariant);


                for (CreateProductImageRequest createProductImageRequest : createProductVariantRequest.getProductImages()) {
                    ProductImage productImage = new ProductImage();
                    productImage.setProductVariant(productVariant);
                    productImage.setImageUrl(createProductImageRequest.getImageUrl());
                    productImageRepository.save(productImage);
                }

            }
        }

        List<ProductVariant> productVariants = productVariantRepository.findAllByProductId(product.getId());

        AtomicReference<Long> overalTotalStok = new AtomicReference<>(0L);
        for (ProductVariant productVariant : productVariants) {
            overalTotalStok.updateAndGet(total -> productVariant.getStok());
        }
        product.setTotalStok(overalTotalStok.get());
        productRepository.save(product);

    }


    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::toProductResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllPopularProducts() {

        return productRepository.findAllByIsPopularProduct(true).stream().map(ProductMapper::toProductResponse).toList();
    }


    @Transactional(readOnly = true)
    public DetailProductResponse getDetailProduct(String productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk tidak ditemukan"));

        List<ProductVariant> productVariants = productVariantRepository.findAllByProductId(productId);


        List<ProductVariantResponse> productVariantResponses = ProductMapper.productVariantsToProductVariantResponses(productVariants);

        return toDetailProductResponse(product, productVariantResponses);
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

        if (Objects.nonNull(productRequest.getBrandId())) {
            Brand brand = brandRepository.findById(productRequest.getBrandId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand tidak ditemukan"));
            product.setBrand(brand);
        }
        if (Objects.nonNull(productRequest.getBpomCode())) {
            product.setBpomCode(productRequest.getBpomCode());
            System.out.println(productRequest.getBpomCode());
        }
//        if (Objects.nonNull(productRequest.getProductImages())) {
////            List<ProductImage> productImages = productRequest.getProductImages().stream().map(productImageRequest -> toProductImage(productImageRequest)).toList();
//            for (UpdateProductImageRequest updateProductImageRequest : productRequest.getProductImages()) {
//                ProductImage productImageByIdAndProductId = productImageRepository.findByIdAndProductId(updateProductImageRequest.getId(), productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produk Image tidak ditemukan"));
//                if (productImageByIdAndProductId != null) {
//                    productImageByIdAndProductId.setImageUrl(updateProductImageRequest.getImageUrl());
//                    productImageRepository.save(productImageByIdAndProductId);
//                }
//
//            }
//            System.out.println(productRequest.getProductImages());
//        }

        if (Objects.nonNull(productRequest.getProductVariants())) {
            for (UpdateProductVariantRequest updateProductVariantRequest : productRequest.getProductVariants()) {
                ProductVariant productVariant = productVariantRepository.findByIdAndProductId(updateProductVariantRequest.getId(), productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Varian dari produk tersebut tidak ditemukan"));

                productVariant.setOriginalPrice(updateProductVariantRequest.getOriginalPrice());
                productVariant.setSize(updateProductVariantRequest.getSize());
                productVariant.setStok(updateProductVariantRequest.getStok());
                productVariant.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
                productVariant.setDiscount(updateProductVariantRequest.getDiscount());
                productVariant.setPrice(updateProductVariantRequest.getOriginalPrice().subtract(updateProductVariantRequest.getOriginalPrice().multiply(updateProductVariantRequest.getDiscount().divide(BigDecimal.valueOf(100)))));

                productVariantRepository.save(productVariant);

                if (updateProductVariantRequest.getProductImages() != null) {
                    for (UpdateProductImageRequest updateProductImageRequest : updateProductVariantRequest.getProductImages()) {
                        ProductImage productImage = productImageRepository.findByIdAndProductVariantId(updateProductImageRequest.getId(), updateProductVariantRequest.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gambar tidak ditemukan"));
                        productImage.setImageUrl(updateProductImageRequest.getImageUrl());
                        productImageRepository.save(productImage);
                    }
                }


            }
        }

        if (Objects.nonNull(productRequest.getCategoryItemId())) {
            CategoryItem categoryItem = categoryItemRepository.findById(productRequest.getCategoryItemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category tidak ditemukan"));
            product.setCategoryItem(categoryItem);
        }
        if (Objects.nonNull(productRequest.getIsPromo())) {
            product.setIsPromo(productRequest.getIsPromo());
            System.out.println(productRequest.getIsPromo());
        }


        if (Objects.nonNull(productRequest.getIngredient())) {
            product.setIngredient(productRequest.getIngredient());
        }

        if (Objects.nonNull(productRequest.getIsPopularProduct())) {
            product.setIsPopularProduct(productRequest.getIsPopularProduct());
        }
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());

        AtomicReference<Long> overalTotalStok = new AtomicReference<>(0L);

        List<ProductVariant> productVariants = productVariantRepository.findAllByProductId(productId);

        productVariants.forEach(productVariant -> {
            overalTotalStok.updateAndGet(total -> productVariant.getStok());
        });
        product.setTotalStok(overalTotalStok.get());
        product.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
        productRepository.save(product);

        List<ProductVariantResponse> productVariantResponses = ProductMapper.productVariantsToProductVariantResponses(productVariants);
        return toDetailProductResponse(product, productVariantResponses);
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
