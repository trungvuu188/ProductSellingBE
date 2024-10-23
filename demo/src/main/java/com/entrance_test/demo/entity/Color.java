package com.entrance_test.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity(name = "color")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int colorId;

    @Column(name = "color_name")
    String colorName;

    @OneToOne(mappedBy = "color")
    OrderItem orderItem;

    @OneToMany(mappedBy = "color")
    Set<Cart> carts;

    @OneToMany(mappedBy = "color")
    Set<ProductColor> productColors;
}
