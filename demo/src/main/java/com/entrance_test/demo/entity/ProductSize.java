package com.entrance_test.demo.entity;

import com.entrance_test.demo.entity.keys.KeyProductSize;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "product_size")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSize {

    @EmbeddedId
    KeyProductSize keyProductSize;

    @ManyToOne
    @JoinColumn(name = "size_id", insertable = false, updatable = false)
    Size size;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;
}
