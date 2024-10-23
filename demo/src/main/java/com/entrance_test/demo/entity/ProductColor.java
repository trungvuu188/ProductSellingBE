package com.entrance_test.demo.entity;

import com.entrance_test.demo.entity.keys.KeyProductColor;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "product_color")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductColor {

    @EmbeddedId
    KeyProductColor keyProductColor;

    @ManyToOne
    @JoinColumn(name = "color_id", insertable = false, updatable = false)
    Color color;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;
}
