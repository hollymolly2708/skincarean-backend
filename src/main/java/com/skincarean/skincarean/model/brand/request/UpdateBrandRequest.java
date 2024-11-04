package com.skincarean.skincarean.model.brand.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {
    @Size(max = 100)
    private String name;
    private String description;
    @Size(max = 255)
    private String brandLogo;
    @Size(max = 255)
    private String brandPoster;
    @Size(max = 255)
    private String websiteMediaUrl;
    @Size(max = 255)
    private String instagramUrl;
    @Size(max = 255)
    private String facebookUrl;
    @Size(max = 255)
    private String contactEmailUrl;
    private String address;
    private Boolean isPopularBrand;

}
