package com.entrance_test.demo.service.imp;

import com.entrance_test.demo.dto.response.ProductSaleApplied;
import com.entrance_test.demo.entity.Product;

import java.util.List;

public interface StockServiceImp {

    List<ProductSaleApplied> getListProductSale();
    int getProductStockPrice(int productId);

    String getProductSaleExpirationDate(int productId);
}
