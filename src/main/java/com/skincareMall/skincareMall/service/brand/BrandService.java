package com.skincareMall.skincareMall.service.brand;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.brand.request.CreateBrandRequest;
import com.skincareMall.skincareMall.model.brand.request.UpdateBrandRequest;
import com.skincareMall.skincareMall.model.brand.response.BrandResponse;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;

import java.util.List;

public interface BrandService {

    void addBrand(Admin admin, CreateBrandRequest createBrandRequest);

    List<BrandResponse> getAllBrands();

    WebResponse<String> deleteBrand(Admin admin, Long brandId);

    BrandResponse detailBrand(Long brandId);

    BrandResponse updateBrand(Admin admin, Long brandId, UpdateBrandRequest updateBrandRequest);

    List<BrandResponse> getBrandsByIsTopBrand();

    List<ProductResponse> getAllProductsByBrand(Long brandId);
}
