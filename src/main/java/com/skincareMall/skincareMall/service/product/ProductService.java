package com.skincareMall.skincareMall.service.product;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.product.request.CreateProductRequest;
import com.skincareMall.skincareMall.model.product.request.SearchProductRequest;
import com.skincareMall.skincareMall.model.product.request.UpdateProductRequest;
import com.skincareMall.skincareMall.model.product.response.DetailProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    void createProduct(Admin admin, CreateProductRequest createProductRequest);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getAllPopularProducts();

    DetailProductResponse getDetailProduct(String productId);

    void deleteProductById(String productId);

    DetailProductResponse updateProduct(Admin admin, String productId, UpdateProductRequest productRequest);

    Page<ProductResponse> search(SearchProductRequest searchProductRequest);
}
