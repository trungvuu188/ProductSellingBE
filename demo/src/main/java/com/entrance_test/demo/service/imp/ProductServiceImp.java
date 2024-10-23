package com.entrance_test.demo.service.imp;

import com.entrance_test.demo.dto.request.FilterProductRequest;
import com.entrance_test.demo.dto.request.ProductCreationRequest;
import com.entrance_test.demo.dto.request.ProductImageUpload;
import com.entrance_test.demo.dto.request.SaleProductRequest;
import com.entrance_test.demo.dto.response.*;
import com.entrance_test.demo.entity.Color;
import com.entrance_test.demo.entity.Product;


import java.util.List;

public interface ProductServiceImp {

    ProductDTO productMapper(Product product);

    List<ProductDTO> getAllProduct(FilterProductRequest filterProductRequest);

    ProductDTO getProductById(int productId);

    List<ProductDTO> getProductsByCategory(int categoryId);

    boolean addProduct(ProductCreationRequest productCreationRequest);

    List<SizeDTO> getAllSize();

    List<CategoryDTO> getAllCategory();

    List<StyleDTO> getAllStyle();

    List<ColorDTO> getAllColor();

    int insertProduct(Product product);

    void applySale(SaleProductRequest saleProductRequest);

    double getAverageRating(int productId);

    boolean updateProductImage(int productId, ProductImageUpload productImageUpload);

}
