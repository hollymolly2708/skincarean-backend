package com.skincareMall.skincareMall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seller_id")
    private Long sellerId;
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "shop_description")
    private String shopDescription;
    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    private User user;
}
