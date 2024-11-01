package com.skincareMall.skincareMall.model.order.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductVariantResponse {
    private Long id;
    private String size;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private BigDecimal discount;

}
