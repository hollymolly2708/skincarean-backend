package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    private String id;

    private String name;

    private String description;

    @Column(name = "thumbnail_image")
    private String thumbnailImage;

    @Column(name = "bpom_code")
    private String bpomCode;

    @Column(name = "is_promo")
    private Boolean isPromo;

    @Column(name = "total_stok")
    private Long totalStok;

    @Column(name = "is_popular_product")
    private Boolean isPopularProduct;



    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "ingredient")
    private String ingredient;
    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;

    @ManyToOne
    @JoinColumn(name = "added_by_admin", referencedColumnName = "username")
    private Admin admin;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> productReviews;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_item_id", referencedColumnName = "id")
    private CategoryItem categoryItem;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> productVariants;

}
