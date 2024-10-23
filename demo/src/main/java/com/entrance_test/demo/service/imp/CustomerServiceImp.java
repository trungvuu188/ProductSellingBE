package com.entrance_test.demo.service.imp;

import com.entrance_test.demo.dto.request.*;
import com.entrance_test.demo.dto.response.OrderDTO;
import com.entrance_test.demo.dto.response.OrderItemDTO;
import com.entrance_test.demo.dto.response.PaymentDTO;
import com.entrance_test.demo.dto.response.ProductDTO;

import java.util.List;

public interface CustomerServiceImp {

    void addCustomer(CustomerCreationRequest customerCreationRequest);
    void addToWishList(WishListRequest wishListRequest);
    void removeFromWishList(WishListRequest wishListRequest);
    void ratingProduct(RatingProductRequest ratingProductRequest);
    List<ProductDTO> getProductFromWishList(int customerId);
    List<OrderDTO> getAllOrders(int customerId);
    void createOrderItem(OrderItemCreationRequest orderItemCreationRequest);
    void addToCart(OrderItemCreationRequest orderItemCreationRequest);
    void removeFromCart(ItemCartRequest itemCartRequest);
    void updateCartItem(OrderItemRequestDTO orderItemRequestDTO);
    List<OrderItemDTO> getCartItems(int customerId);
    PaymentDTO createPayment(int customerId);

}
