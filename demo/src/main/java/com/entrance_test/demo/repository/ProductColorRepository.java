package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor, Integer>, JpaSpecificationExecutor<ProductColor> {

    List<ProductColor> findAllByProductProductId(int productId);

}
