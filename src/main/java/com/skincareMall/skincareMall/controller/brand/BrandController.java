package com.skincareMall.skincareMall.controller.brand;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.brand.request.CreateBrandRequest;
import com.skincareMall.skincareMall.model.brand.request.UpdateBrandRequest;
import com.skincareMall.skincareMall.model.brand.response.BrandResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.brand.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandController {
    @Autowired
    private BrandService brandService;

    @PostMapping(path = "/api/brands", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addBrand(Admin admin, @RequestBody CreateBrandRequest createBrandRequest) {
        brandService.addBrand(admin, createBrandRequest);
        return WebResponse.<String>builder().data("Berhasil menambahkan brands").build();
    }

    @GetMapping(path = "/api/brands", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<BrandResponse>> getAllBrands() {
        List<BrandResponse> brands = brandService.getAllBrands();
        return WebResponse.<List<BrandResponse>>builder().data(brands).build();
    }

    @DeleteMapping(path = "/api/brands/{brandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteBrand(Admin admin, @PathVariable("brandId") Long brandId) {
        return brandService.deleteBrand(admin, brandId);
    }

    @GetMapping(path = "/api/brands/{brandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<BrandResponse> getDetailBrand(@PathVariable("brandId") Long brandId) {
        BrandResponse response = brandService.detailBrand(brandId);
        return WebResponse.<BrandResponse>builder().data(response).build();
    }

    @PatchMapping(path = "/api/brands/{brandId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<BrandResponse> updateBrand(Admin admin, @PathVariable("brandId") Long brandId,@RequestBody UpdateBrandRequest updateBrandRequest) {
        BrandResponse response = brandService.updateBrand(admin, brandId, updateBrandRequest);
        return WebResponse.<BrandResponse>builder().data(response).build();
    }
}
