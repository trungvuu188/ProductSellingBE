package com.entrance_test.demo.entity.keys;

import com.entrance_test.demo.entity.Color;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeyProductColor implements Serializable {

    @Column(name = "color_id")
    int colorId;

    @Column(name = "product_id")
    int productId;
}
