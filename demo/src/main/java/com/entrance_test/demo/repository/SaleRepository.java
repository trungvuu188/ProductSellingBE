package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    List<Sale> getAllByStatus(String status);
}
