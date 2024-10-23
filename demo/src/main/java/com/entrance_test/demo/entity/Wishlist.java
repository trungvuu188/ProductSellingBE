package com.entrance_test.demo.entity;

import com.entrance_test.demo.entity.keys.KeyWishlist;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "wishlist")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wishlist {

    @EmbeddedId
    KeyWishlist keyWishlist;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable=false, updatable=false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable=false, updatable=false)
    Customer customer;
}
