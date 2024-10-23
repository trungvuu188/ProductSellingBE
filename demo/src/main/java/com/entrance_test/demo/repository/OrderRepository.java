package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByCustomerCustomerId(int customerId);
}
