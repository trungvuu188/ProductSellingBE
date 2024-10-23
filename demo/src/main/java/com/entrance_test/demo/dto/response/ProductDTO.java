package com.entrance_test.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    int productId;
    String productName;
    String productDesc;
    int price;
    int salePrice;
    String expiredDate;
    double ratingStars;
    List<SizeDTO> productSizes;
    List<ColorDTO> productColors;
    List<ProductImageDTO> productImageDTOS;
}
