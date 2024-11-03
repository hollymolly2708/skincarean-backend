package com.skincareMall.skincareMall.model.category.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private Long id;
    private String categoryImage;
    private String name;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;

}
