package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.Wishlist;
import com.entrance_test.demo.entity.keys.KeyProductCustomer;
import com.entrance_test.demo.entity.keys.KeyWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<Wishlist, KeyWishlist> {

    void removeByKeyWishlistCustomerIdAndKeyWishlistProductId(int customerId, int productId);

    List<Wishlist> findAllByKeyWishlistCustomerId(int customerId);
}
