package com.skincarean.skincarean.service.product;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.model.product.request.CreateProductRequest;
import com.skincarean.skincarean.model.product.request.SearchProductRequest;
import com.skincarean.skincarean.model.product.request.UpdateProductRequest;
import com.skincarean.skincarean.model.product.response.DetailProductResponse;
import com.skincarean.skincarean.model.product.response.ProductResponse;
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
