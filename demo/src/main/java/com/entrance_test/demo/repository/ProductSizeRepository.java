package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer>, JpaSpecificationExecutor<ProductSize> {

    List<ProductSize> findAllByProductProductId(int productId);
}
