package com.skincareMall.skincareMall.controller.product;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.product.request.ProductRequest;
import com.skincareMall.skincareMall.model.product.request.UpdateProductRequest;
import com.skincareMall.skincareMall.model.product.response.DetailProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(
            path = "/api/products",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
     public WebResponse<String> createProduct(Admin admin, @RequestBody ProductRequest productRequest) {
        productService.createProduct(admin, productRequest);
        return WebResponse.<String>builder().data("Product berhasil ditambahkan").build();

    }

    @GetMapping(path = "/api/products")
    public  WebResponse<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> allProducts = productService.getAllProducts();
        return WebResponse.<List<ProductResponse>>builder().data(allProducts).build();
    }

    @GetMapping(path = "/api/products/{productId}")
    public WebResponse<DetailProductResponse> getDetailProduct(@PathVariable("productId") String productId){
        DetailProductResponse detailProduct = productService.getDetailProduct(productId);
        return WebResponse.<DetailProductResponse>builder().data(detailProduct).build();
    }

    @DeleteMapping(path = "/api/products/{productId}")
    public WebResponse<String> deleteProductById(Admin admin, @PathVariable("productId") String productId){
        productService.deleteProductById(productId);
        return WebResponse.<String>builder().data("Product berhasil dihapus").build();
    }

    @PatchMapping (path = "/api/products/{productId}" )
    public WebResponse<DetailProductResponse> updateProductById(@PathVariable("productId") String productId, @RequestBody UpdateProductRequest updateProductRequest, Admin admin){
        DetailProductResponse updateDetailProductResponse = productService.updateProduct(admin, productId, updateProductRequest);
        return WebResponse.<DetailProductResponse>builder().data(updateDetailProductResponse).build();
    }
}
