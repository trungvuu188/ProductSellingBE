package com.entrance_test.demo.entity;

import com.entrance_test.demo.entity.keys.KeyProductCustomer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "rating")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating {

    @EmbeddedId
    KeyProductCustomer keyProductCustomer;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable=false, updatable=false)
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable=false, updatable=false)
    Product product;

    @Column(name = "star")
    int star;
}
