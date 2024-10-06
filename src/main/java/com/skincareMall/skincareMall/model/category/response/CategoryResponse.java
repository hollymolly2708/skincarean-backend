package com.skincareMall.skincareMall.model.category.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private String categoryImage;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
}
