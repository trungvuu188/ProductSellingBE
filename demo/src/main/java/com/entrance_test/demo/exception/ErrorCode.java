package com.entrance_test.demo.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception"),
    PRODUCT_NOTFOUND(5000, "Product ID is not found"),
    CUSTOMER_NOTFOUND(5000, "Customer ID is not found"),
    CATEGORY_NOTFOUND(5000, "Category ID is not found"),
    STYLE_NOTFOUND(5000, "Style ID is not found"),
    COLOR_NOTFOUND(5000, "Color ID is not found"),
    SIZE_NOTFOUND(5000, "Size ID is not found"),
    FILE_UPLOAD_ERROR(5000, "Upload file error"),
    PARSE_DATE_ERROR(5000, "Parse date error"),
    ;

    int code;
    String message;

}
