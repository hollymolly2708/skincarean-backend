package com.skincareMall.skincareMall.controller.seller;

import com.skincareMall.skincareMall.entity.Seller;
import com.skincareMall.skincareMall.entity.User;
import com.skincareMall.skincareMall.model.product.request.CreateProductRequest;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.seller.request.CreateSellerRequest;
import com.skincareMall.skincareMall.model.seller.response.SellerResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.seller.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
public class SellerController {
    @Autowired
    private SellerService sellerService;


    @PostMapping(
            path = "/api/seller/products",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> createProduct(User user, @RequestBody CreateProductRequest createProductRequest) {
        sellerService.createProduct(user, createProductRequest);
        return WebResponse.<String>builder().data("Produk berhasil ditambahkan").build();

    }

    @GetMapping(path = "/api/seller/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProductResponse>> getAllProductsBySeller(User user) {
        List<ProductResponse> allProductsBySeller = sellerService.getAllProductsBySeller(user);
        return WebResponse.<List<ProductResponse>>builder().data(allProductsBySeller).build();
    }

    @GetMapping(path = "api/seller/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<SellerResponse> getSellerProfile(User user) {
        SellerResponse seller = sellerService.getSeller(user);
        return WebResponse.<SellerResponse>builder().data(seller).build();
    }

    @PostMapping(path = "/api/seller/become-seller", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<SellerResponse> becomeSeller(User user, CreateSellerRequest createSellerRequest) {
        SellerResponse sellerResponse = sellerService.becomeSeller(user, createSellerRequest);
        return WebResponse.<SellerResponse>builder().data(sellerResponse).build();
    }


}
