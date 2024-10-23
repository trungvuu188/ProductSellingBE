package com.entrance_test.demo.entity;

import com.entrance_test.demo.entity.keys.KeyProductCustomer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    @EmbeddedId
    KeyProductCustomer keyProductCustomer;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable=false, updatable=false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable=false, updatable=false)
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "size_id")
    Size size;

    @ManyToOne
    @JoinColumn(name = "color_id")
    Color color;

    @Column(name = "quantity")
    int quantity;
}
