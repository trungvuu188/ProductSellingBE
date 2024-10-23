package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.Cart;
import com.entrance_test.demo.entity.Product;
import com.entrance_test.demo.entity.Rating;
import com.entrance_test.demo.entity.keys.KeyProductCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, KeyProductCustomer> {

    boolean existsByCustomerCustomerIdAndProductProductId(int customerId, int productId);
    Rating findByCustomerCustomerIdAndProductProductId(int customerId, int productId);
    List<Rating> findAllByProductProductId(int productId);
}
