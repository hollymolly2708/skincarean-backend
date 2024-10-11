package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
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
@Table(name = "category_items")
@Entity
public class CategoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "category_items_image")
    private String categoryImage;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;
    @OneToMany(mappedBy = "categoryItem")
    private List<Product> products;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id" )
    private Category category;
}
