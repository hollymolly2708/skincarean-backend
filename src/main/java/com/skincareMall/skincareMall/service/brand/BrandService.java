package com.skincareMall.skincareMall.service.brand;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.Brand;
import com.skincareMall.skincareMall.model.brand.request.CreateBrandRequest;
import com.skincareMall.skincareMall.model.brand.request.UpdateBrandRequest;
import com.skincareMall.skincareMall.model.brand.response.BrandResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.repository.BrandRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class BrandService {
    @Autowired
    private ValidationService validationService;

    @Autowired
    private BrandRepository brandRepository;

    @Transactional
    public void addBrand(Admin admin, CreateBrandRequest createBrandRequest) {
        validationService.validate(createBrandRequest);
        Brand brand = Brand.builder()
                .name(createBrandRequest.getName())
                .description(createBrandRequest.getDescription())
                .brandLogo(createBrandRequest.getBrandLogo())
                .brandPoster(createBrandRequest.getBrandPoster())
                .address(createBrandRequest.getAddress())
                .facebookUrl(createBrandRequest.getFacebookUrl())
                .contactEmailUrl(createBrandRequest.getContactEmailUrl())
                .websiteMediaUrl(createBrandRequest.getWebsiteMediaUrl())
                .instagramUrl(createBrandRequest.getInstagramUrl())
                .createdAt(Utilities.changeFormatToTimeStamp())
                .lastUpdatedAt(Utilities.changeFormatToTimeStamp())
                .build();

        brandRepository.save(brand);
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream().map(brand -> {
            return BrandResponse.builder()
                    .id(brand.getId())
                    .brandLogo(brand.getBrandLogo())
                    .brandPoster(brand.getBrandPoster())
                    .name(brand.getName())
                    .facebookUrl(brand.getFacebookUrl())
                    .instagramUrl(brand.getInstagramUrl())
                    .contactEmailUrl(brand.getContactEmailUrl())
                    .websiteMediaUrl(brand.getWebsiteMediaUrl())
                    .description(brand.getDescription())
                    .address(brand.getAddress())
                    .createdAt(brand.getCreatedAt())
                    .lastUpdatedAt(brand.getLastUpdatedAt())
                    .build();
        }).toList();
    }

    public WebResponse<String> deleteBrand(Admin admin, Long brandId) {

        try {
            brandRepository.deleteById(brandId);
            return WebResponse.<String>builder().data("Berhasil menghapus brand").isSuccess(true).build();
        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.<String>builder().errors(e.getMessage()).isSuccess(false).build();
        }

    }

    @Transactional(readOnly = true)
    public BrandResponse detailBrand(Long brandId) {

        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand tidak ditemukan"));

        return BrandResponse.builder()
                .lastUpdatedAt(brand.getLastUpdatedAt())
                .name(brand.getName())
                .brandLogo(brand.getBrandLogo())
                .brandPoster(brand.getBrandPoster())
                .id(brand.getId())
                .contactEmailUrl(brand.getContactEmailUrl())
                .websiteMediaUrl(brand.getWebsiteMediaUrl())
                .description(brand.getDescription())
                .address(brand.getAddress())
                .facebookUrl(brand.getFacebookUrl())
                .instagramUrl(brand.getInstagramUrl())
                .createdAt(brand.getCreatedAt())
                .build();
    }

    @Transactional
    public BrandResponse updateBrand(Admin admin, Long brandId, UpdateBrandRequest updateBrandRequest) {
        validationService.validate(updateBrandRequest);
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand tidak ditemukan"));
        if (Objects.nonNull(updateBrandRequest.getBrandLogo())) {
            brand.setBrandLogo(updateBrandRequest.getBrandLogo());
        }

        if (Objects.nonNull(updateBrandRequest.getBrandPoster())) {
            brand.setBrandPoster(updateBrandRequest.getBrandPoster());
        }
        if (Objects.nonNull(updateBrandRequest.getAddress())) {
            brand.setAddress(updateBrandRequest.getAddress());
        }
        if (Objects.nonNull(updateBrandRequest.getDescription())) {
            brand.setDescription(updateBrandRequest.getDescription());
        }
        if (Objects.nonNull(updateBrandRequest.getName())) {
            brand.setName(updateBrandRequest.getName());
        }
        if (Objects.nonNull(updateBrandRequest.getContactEmailUrl())) {
            brand.setContactEmailUrl(updateBrandRequest.getContactEmailUrl());
        }
        if (Objects.nonNull(updateBrandRequest.getInstagramUrl())) {
            brand.setInstagramUrl(updateBrandRequest.getInstagramUrl());
        }

        if (Objects.nonNull(updateBrandRequest.getWebsiteMediaUrl())) {
            brand.setWebsiteMediaUrl(updateBrandRequest.getWebsiteMediaUrl());
        }
        if (Objects.nonNull(updateBrandRequest.getFacebookUrl())) {
            brand.setFacebookUrl(updateBrandRequest.getFacebookUrl());
        }

        brand.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
        brandRepository.save(brand);

        return BrandResponse.builder()
                .brandLogo(brand.getBrandLogo())
                .brandPoster(brand.getBrandPoster())
                .description(brand.getDescription())
                .name(brand.getName())
                .id(brand.getId())
                .lastUpdatedAt(brand.getLastUpdatedAt())
                .websiteMediaUrl(brand.getWebsiteMediaUrl())
                .address(brand.getAddress())
                .facebookUrl(brand.getFacebookUrl())
                .instagramUrl(brand.getInstagramUrl())
                .createdAt(brand.getCreatedAt())
                .contactEmailUrl(brand.getContactEmailUrl())
                .build();

    }
}
