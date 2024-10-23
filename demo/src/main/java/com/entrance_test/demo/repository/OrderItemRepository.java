package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    OrderItem findByOrderOrderIdAndProductProductIdAndSizeSizeIdAndColorColorId(int orderId, int productId, int sizeId, int colorId);

}
