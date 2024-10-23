package com.entrance_test.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int productId;

    @Column(name = "product_name")
    String productName;

    @Column(name = "product_desc")
    String productDesc;

    @Column(name = "product_price")
    int productPrice;

    @ManyToOne
    @JoinColumn(name = "cate_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "style_id")
    Style style;

    @OneToMany(mappedBy = "product")
    Set<ProductColor> productColors;

    @OneToMany(mappedBy = "product")
    Set<ProductSize> productSizes;

    @OneToMany(mappedBy = "product")
    Set<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    Set<Wishlist> wishlists;

    @OneToMany(mappedBy = "product")
    Set<Cart> carts;

    @OneToMany(mappedBy = "product")
    Set<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    Set<Rating> ratings;

    @OneToMany(mappedBy = "product")
    Set<ProductSale> productSales;
}
