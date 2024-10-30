package com.skincareMall.skincareMall.service.seller;

import com.skincareMall.skincareMall.entity.*;
import com.skincareMall.skincareMall.mapper.ProductMapper;
import com.skincareMall.skincareMall.model.product.request.CreateProductImageRequest;
import com.skincareMall.skincareMall.model.product.request.CreateProductRequest;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.seller.request.CreateSellerRequest;
import com.skincareMall.skincareMall.model.seller.response.SellerResponse;
import com.skincareMall.skincareMall.repository.*;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.skincareMall.skincareMall.mapper.ProductMapper.toProductResponse;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryItemRepository categoryItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ValidationService validationService;

    public SellerResponse becomeSeller(User user, CreateSellerRequest createSellerRequest) {
        validationService.validate(createSellerRequest);
        if (user.getIsSeller()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User sudah menjadi seller");
        }
        Seller seller = new Seller();
        seller.setUser(user);
        seller.setCreatedAt(Utilities.changeFormatToTimeStamp());
        seller.setShopName(createSellerRequest.getShopName());
        seller.setShopDescription(createSellerRequest.getShopDescription());
        sellerRepository.save(seller);

        user.setIsSeller(true);
        userRepository.save(user);


        return SellerResponse.builder()
                .sellerId(user.getSeller().getSellerId())
                .shopDescription(user.getSeller().getShopDescription())
                .createdAt(user.getSeller().getCreatedAt())
                .shopName(user.getSeller().getShopName())
                .build();

    }
    @Transactional
    public void createProduct(User user, CreateProductRequest createProductRequest) {
        validationService.validate(createProductRequest);

        Brand brand = brandRepository.findById(createProductRequest.getBrandId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand tidak ditemukan"));
        CategoryItem categoryItem = categoryItemRepository.findById(createProductRequest.getCategoryItemId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category tidak ditemukan"));
        if (user.getIsSeller().equals(false)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User belum menjadi seller");
        }


        // Membuat dan menyimpan product terlebih dahulu
        Product product = new Product();
        BigDecimal requestOriginalPrice = createProductRequest.getOriginalPrice();
        BigDecimal requestDiscount = createProductRequest.getDiscount();
        product.setSeller(user.getSeller());
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


    public SellerResponse getSeller(User user) {

        return SellerResponse.builder()
                .sellerId(user.getSeller().getSellerId())
                .createdAt(user.getSeller().getCreatedAt())
                .shopName(user.getSeller().getShopName())
                .shopDescription(user.getSeller().getShopDescription())
                .build();
    }

    public List<ProductResponse> getAllProductsBySeller(User user) {
        Seller seller = sellerRepository.findSellerByUser(user);
        return seller.getProducts().stream().map(product -> toProductResponse(product)).toList();
    }
}
