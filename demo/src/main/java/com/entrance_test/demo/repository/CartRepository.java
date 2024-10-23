package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.Cart;
import com.entrance_test.demo.entity.keys.KeyProductCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, KeyProductCustomer> {
    boolean existsByCustomerCustomerIdAndProductProductId(int customerId, int productId);
    Cart getCartByCustomerCustomerIdAndProductProductId(int customerId, int productId);

    void deleteByCustomerCustomerIdAndProductProductId(int customerId, int productId);
    List<Cart> findAllByCustomerCustomerId(int customerId);
}
