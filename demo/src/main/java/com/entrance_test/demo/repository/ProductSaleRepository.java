package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.ProductSale;
import com.entrance_test.demo.entity.keys.KeyProductSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSaleRepository extends JpaRepository<ProductSale, KeyProductSale> {

    List<ProductSale> findAllBySaleSaleId(int saleId);
}
