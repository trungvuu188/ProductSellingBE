package com.entrance_test.demo.controller;

import com.entrance_test.demo.dto.request.*;
import com.entrance_test.demo.dto.response.*;
import com.entrance_test.demo.service.imp.CustomerServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/customer")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerController {

    CustomerServiceImp customerServiceImp;

    @PostMapping("/register")
    public ApiResponse<Void> addCustomer(@RequestBody CustomerCreationRequest customerCreationRequest) {
        customerServiceImp.addCustomer(customerCreationRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

    @GetMapping("/wishlist")
    public ApiResponse<List<ProductDTO>> getProductFromWishList(@RequestParam int customerId) {
        return ApiResponse.<List<ProductDTO>>builder()
                .result(customerServiceImp.getProductFromWishList(customerId))
                .build();
    }

    @PostMapping("/wishlist-add")
    public ApiResponse<Void> addToWishList(@RequestBody WishListRequest wishListRequest) {
        customerServiceImp.addToWishList(wishListRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/wishlist-remove")
    public ApiResponse<Void> removeFromWishList(@RequestBody WishListRequest wishListRequest) {
        customerServiceImp.removeFromWishList(wishListRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/rating")
    public ApiResponse<Void> ratingProduct(@RequestBody RatingProductRequest ratingProductRequest) {
        customerServiceImp.ratingProduct(ratingProductRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/order")
    public ApiResponse<Void> createOrder(@RequestBody OrderItemCreationRequest orderItemCreationRequest) {
        customerServiceImp.createOrderItem(orderItemCreationRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/payment")
    public ApiResponse<PaymentDTO> createPayment(@RequestParam int customerId) {
        return ApiResponse.<PaymentDTO>builder()
                .result(customerServiceImp.createPayment(customerId))
                .build();
    }

    @GetMapping("/order")
    public ApiResponse<List<OrderDTO>> getOrders(@RequestParam int customerId) {
        return ApiResponse.<List<OrderDTO>>builder()
                .result(customerServiceImp.getAllOrders(customerId))
                .build();
    }

    @PostMapping("/add-to-cart")
    public ApiResponse<Void> addToCart(@RequestBody OrderItemCreationRequest orderItemCreationRequest) {
        customerServiceImp.addToCart(orderItemCreationRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

    @GetMapping("/my-cart")
    public ApiResponse<List<OrderItemDTO>> getMyCart(@RequestParam int customerId) {
        return ApiResponse.<List<OrderItemDTO>>builder()
                .result(customerServiceImp.getCartItems(customerId))
                .build();
    }

    @DeleteMapping("/remove-from-cart")
    public ApiResponse<Void> removeFromCart(@RequestBody ItemCartRequest itemCartRequest) {
        customerServiceImp.removeFromCart(itemCartRequest);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PutMapping("/update-cart-item")
    public ApiResponse<Void> updateCartItem(@RequestBody OrderItemRequestDTO orderItemRequestDTO) {
        customerServiceImp.updateCartItem(orderItemRequestDTO);
        return ApiResponse.<Void>builder()
                .build();
    }


}
