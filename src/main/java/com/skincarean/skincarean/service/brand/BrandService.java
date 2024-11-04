package com.skincarean.skincarean.service.brand;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.model.brand.request.CreateBrandRequest;
import com.skincarean.skincarean.model.brand.request.UpdateBrandRequest;
import com.skincarean.skincarean.model.brand.response.BrandResponse;
import com.skincarean.skincarean.model.product.response.ProductResponse;
import com.skincarean.skincarean.model.user.response.WebResponse;

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
