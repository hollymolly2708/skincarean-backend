package com.skincareMall.skincareMall.model.seller.request;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSellerRequest {
    @Size(max = 100)
    private String shopName;
    @Size(max = 255)
    private String shopDescription;
}
