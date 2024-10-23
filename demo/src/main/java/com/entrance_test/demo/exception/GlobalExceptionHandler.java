package com.entrance_test.demo.exception;

import com.entrance_test.demo.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<ApiResponse> handlingOtherExceptions(RuntimeException runtimeException) {
//        ApiResponse apiResponse = ApiResponse.builder()
//                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
//                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
//                .build();
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException) {

        ErrorCode errorCode = appException.getErrorCode();

        ApiResponse apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

}
