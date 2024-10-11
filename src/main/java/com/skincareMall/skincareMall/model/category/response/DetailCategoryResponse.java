package com.skincareMall.skincareMall.model.category.response;

import com.skincareMall.skincareMall.entity.CategoryItem;
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
public class DetailCategoryResponse {
    private Long id;
    private String name;
    private String categoryImage;
    private Timestamp createdAt;
    private Timestamp lastUpdatedAt;
    private List<CategoryItemResponse> categoryItems;
}
