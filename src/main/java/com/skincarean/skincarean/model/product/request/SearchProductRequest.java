package com.skincarean.skincarean.model.product.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductRequest {
    private String name;
    @NotNull
    private Integer page;
    @NotNull
    private Integer size;
}
