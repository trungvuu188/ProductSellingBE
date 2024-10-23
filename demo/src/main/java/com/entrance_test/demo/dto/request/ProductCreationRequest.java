package com.entrance_test.demo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {

    String productName;
    String productDesc;
    int categoryId;
    int styleId;
    int[] size;
    int[] color;
    MultipartFile mainDisplayImage;
    MultipartFile[] descImage;
    int price;
}
