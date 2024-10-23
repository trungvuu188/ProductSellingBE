package com.entrance_test.demo.entity.keys;


import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeyWishlist implements Serializable {

    @Column(name = "customer_id")
    int customerId;

    @Column(name = "product_id")
    int productId;
}
