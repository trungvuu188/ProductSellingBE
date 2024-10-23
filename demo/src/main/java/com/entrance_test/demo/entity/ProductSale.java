package com.entrance_test.demo.entity;

import com.entrance_test.demo.entity.keys.KeyProductSale;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity(name = "product_sale")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSale {

    @EmbeddedId
    KeyProductSale keyProductSale;

    @ManyToOne
    @JoinColumn(name = "sale_id", insertable = false, updatable = false)
    Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;
}
