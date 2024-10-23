package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findByCustomerCustomerId(int customerId);
}
