package com.entrance_test.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity(name = "size")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int sizeId;

    @Column(name = "size_name")
    String sizeName;

    @OneToOne(mappedBy = "size")
    OrderItem orderItem;

    @OneToMany(mappedBy = "size")
    Set<Cart> carts;

    @OneToMany(mappedBy = "size")
    Set<ProductSize> productSizes;
}
