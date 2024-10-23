package com.entrance_test.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity(name = "style")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Style {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int styleId;

    @Column(name = "style_title")
    String styleTitle;

    @OneToMany(mappedBy = "style")
    Set<Product> products;
}
