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
public class KeyProductSize implements Serializable {

    @Column(name = "size_id")
    int sizeId;

    @Column(name = "product_id")
    int productId;
}
