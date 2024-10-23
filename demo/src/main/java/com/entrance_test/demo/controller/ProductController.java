package com.entrance_test.demo.controller;

import com.entrance_test.demo.dto.request.FilterProductRequest;
import com.entrance_test.demo.dto.request.ProductCreationRequest;
import com.entrance_test.demo.dto.request.SaleProductRequest;
import com.entrance_test.demo.dto.response.*;
import com.entrance_test.demo.service.imp.ProductServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductServiceImp productServiceImp;

    @GetMapping("")
    public ApiResponse<List<ProductDTO>> getAllProduct(@RequestBody FilterProductRequest filterProductRequest) {
        return ApiResponse.<List<ProductDTO>>builder()
                .result(productServiceImp.getAllProduct(filterProductRequest))
                .build();
    }

    @GetMapping("/productId={id}")
    public ApiResponse<ProductDTO> getProductById(@PathVariable(name = "id") int productId) {
        return ApiResponse.<ProductDTO>builder()
                .result(productServiceImp.getProductById(productId))
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<Boolean> addProduct(@ModelAttribute ProductCreationRequest productCreationRequest) {
        return ApiResponse.<Boolean>builder()
                .result(productServiceImp.addProduct(productCreationRequest))
                .build();
    }

    @GetMapping("/category")
    public ApiResponse<List<CategoryDTO>> getAllCategory() {
        return ApiResponse.<List<CategoryDTO>>builder()
                .result(productServiceImp.getAllCategory())
                .build();
    }

    @GetMapping("/size")
    public ApiResponse<List<SizeDTO>> getAllSize() {
        return ApiResponse.<List<SizeDTO>>builder()
                .result(productServiceImp.getAllSize())
                .build();
    }

    @GetMapping("/style")
    public ApiResponse<List<StyleDTO>> getAllStyle() {
        return ApiResponse.<List<StyleDTO>>builder()
                .result(productServiceImp.getAllStyle())
                .build();
    }

    @GetMapping("/color")
    public ApiResponse<List<ColorDTO>> getAllColor() {
        return ApiResponse.<List<ColorDTO>>builder()
                .result(productServiceImp.getAllColor())
                .build();
    }

    @PostMapping("/apply-sale")
    public ApiResponse<Void> applySale(@RequestBody SaleProductRequest saleProductRequest) {
        productServiceImp.applySale(saleProductRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

}
