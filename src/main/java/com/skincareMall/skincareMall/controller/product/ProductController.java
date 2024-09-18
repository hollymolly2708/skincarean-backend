package com.skincareMall.skincareMall.controller.product;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.product.request.CreateProductRequest;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(path = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public WebResponse<ProductResponse> createProduct(Admin admin, CreateProductRequest createProductRequest) {
        ProductResponse productResponse = productService.createProduct(admin, createProductRequest);
        return WebResponse.<ProductResponse>builder().data(productResponse).build();

    }
}
