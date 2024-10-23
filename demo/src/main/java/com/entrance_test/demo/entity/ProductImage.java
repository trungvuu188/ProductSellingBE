package com.entrance_test.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "product_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    int productImageId;

    @Lob
    @Column(name = "image_data")
    String imageData;

    @Column(name = "is_main_display")
    boolean isMainDisplay;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
