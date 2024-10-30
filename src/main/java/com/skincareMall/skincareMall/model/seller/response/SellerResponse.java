package com.skincareMall.skincareMall.model.seller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerResponse {
    private Long sellerId;
    private String shopName;
    private String shopDescription;
    private Timestamp createdAt;
}
