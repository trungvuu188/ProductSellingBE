package com.entrance_test.demo.service;

import com.entrance_test.demo.dto.request.*;
import com.entrance_test.demo.dto.response.OrderDTO;
import com.entrance_test.demo.dto.response.OrderItemDTO;
import com.entrance_test.demo.dto.response.PaymentDTO;
import com.entrance_test.demo.dto.response.ProductDTO;
import com.entrance_test.demo.entity.*;
import com.entrance_test.demo.entity.keys.KeyProductCustomer;
import com.entrance_test.demo.entity.keys.KeyWishlist;
import com.entrance_test.demo.exception.AppException;
import com.entrance_test.demo.exception.ErrorCode;
import com.entrance_test.demo.repository.*;
import com.entrance_test.demo.service.imp.CustomerServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerService implements CustomerServiceImp {

    WishListRepository wishListRepository;
    ProductRepository productRepository;
    CustomerRepository customerRepository;
    RatingRepository ratingRepository;
    OrderRepository orderRepository;
    PaymentRepository paymentRepository;
    SizeRepository sizeRepository;
    ColorRepository colorRepository;
    OrderItemRepository orderItemRepository;
    CartRepository cartRepository;

    @Override
    public void addCustomer(CustomerCreationRequest customerCreationRequest) {
        Customer customer = Customer.builder()
                .customerName(customerCreationRequest.getCustomerName())
                .address(customerCreationRequest.getAddress())
                .email(customerCreationRequest.getEmail())
                .phone(customerCreationRequest.getPhone())
                .password(customerCreationRequest.getPassword())
                .build();
        customerRepository.save(customer);
    }

    @Override
    public void addToWishList(WishListRequest wishListRequest) {

        Product product = productRepository.findById(wishListRequest.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

        Customer customer = customerRepository.findById(wishListRequest.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOTFOUND));

        KeyWishlist keyWishlist = KeyWishlist.builder()
                .customerId(wishListRequest.getCustomerId())
                .productId(wishListRequest.getProductId())
                .build();

        Wishlist wishlist = Wishlist.builder()
                .keyWishlist(keyWishlist)
                .customer(customer)
                .product(product)
                .build();
        wishListRepository.save(wishlist);
    }

    @Override
    @Transactional
    public void removeFromWishList(WishListRequest wishListRequest) {
        wishListRepository.removeByKeyWishlistCustomerIdAndKeyWishlistProductId(wishListRequest.getCustomerId(), wishListRequest.getProductId());
    }

    @Override
    public void ratingProduct(RatingProductRequest ratingProductRequest) {

        Customer customer = customerRepository.findById(ratingProductRequest.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOTFOUND));

        Product product = productRepository.findById(ratingProductRequest.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

        if(ratingRepository.existsByCustomerCustomerIdAndProductProductId(ratingProductRequest.getCustomerId(), ratingProductRequest.getProductId())) {
            Rating rating = ratingRepository.findByCustomerCustomerIdAndProductProductId(ratingProductRequest.getCustomerId(), ratingProductRequest.getProductId());
            rating.setStar(ratingProductRequest.getStars());
            ratingRepository.save(rating);
        } else {
            KeyProductCustomer keyProductCustomer = KeyProductCustomer.builder()
                    .customerId(ratingProductRequest.getCustomerId())
                    .productId(ratingProductRequest.getProductId())
                    .build();

            Rating rating = Rating.builder()
                    .keyProductCustomer(keyProductCustomer)
                    .product(product)
                    .customer(customer)
                    .star(ratingProductRequest.getStars())
                    .build();

            ratingRepository.save(rating);
        }
    }

    @Override
    public List<ProductDTO> getProductFromWishList(int customerId) {

        List<Wishlist> wishlists = wishListRepository.findAllByKeyWishlistCustomerId(customerId);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Wishlist wishlist : wishlists) {
            Product product = wishlist.getProduct();
            ProductDTO productDTO = ProductDTO.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .price(product.getProductPrice())
                    .build();
            productDTOS.add(productDTO);
        }

        return productDTOS;
    }

    @Override
    public List<OrderDTO> getAllOrders(int customerId) {

        Order order = orderRepository.findByCustomerCustomerId(customerId);
        if(order == null) return null;
        List<OrderDTO> orderDTOS = new ArrayList<>();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        for(OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .productName(product.getProductName())
                    .sizeName(orderItem.getSize().getSizeName())
                    .colorName(orderItem.getColor().getColorName())
                    .quantity(orderItem.getQuantity())
                    .price(product.getProductPrice() * orderItem.getQuantity())
                    .build();
            orderItemDTOS.add(orderItemDTO);
        }
        OrderDTO orderDTO = OrderDTO.builder()
                .orderId(order.getOrderId())
                .orderItemDTOList(orderItemDTOS)
                .total(order.getTotalPrice())
                .build();
        orderDTOS.add(orderDTO);

        return orderDTOS;
    }

    @Override
    public void createOrderItem(OrderItemCreationRequest orderItemCreationRequest) {

        if(orderItemCreationRequest.getOrderItemRequestDTOS().size() == 0) return;
        Customer customer = customerRepository.findById(orderItemCreationRequest.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOTFOUND));
        int totalPrice = 0;

        Order order = orderRepository.findByCustomerCustomerId(customer.getCustomerId());
        if(order != null) {
            totalPrice = order.getTotalPrice();
            for(OrderItem orderItem : generateOrderItem(orderItemCreationRequest.getOrderItemRequestDTOS())) {
                OrderItem orderItem1 = orderItemRepository.findByOrderOrderIdAndProductProductIdAndSizeSizeIdAndColorColorId(
                        order.getOrderId(),
                        orderItem.getProduct().getProductId(),
                        orderItem.getSize().getSizeId(),
                        orderItem.getColor().getColorId());
                totalPrice += orderItem.getQuantity() * orderItem.getProduct().getProductPrice();
                order.setTotalPrice(totalPrice);
                orderRepository.save(order);
                if(orderItem1 != null) {
                    orderItem1.setQuantity(orderItem1.getQuantity() + orderItem.getQuantity());
                    orderItemRepository.save(orderItem1);
                } else {
                    orderItem.setOrder(order);
                    orderItemRepository.save(orderItem);
                }
            };
        } else {
            Payment payment = Payment.builder()
                    .customer(customer)
                    .build();
            paymentRepository.save(payment);

            Order order1 = Order.builder()
                    .payment(payment)
                    .customer(customer)
                    .build();
            orderRepository.save(order1);

            for(OrderItem orderItem : generateOrderItem(orderItemCreationRequest.getOrderItemRequestDTOS())) {
                orderItem.setOrder(order1);
                orderItemRepository.save(orderItem);
                totalPrice += orderItem.getQuantity() * orderItem.getProduct().getProductPrice();
            }
            order1.setTotalPrice(totalPrice);
            orderRepository.save(order1);
        }
    }

    @Override
    public void addToCart(OrderItemCreationRequest orderItemCreationRequest) {

        Customer customer = customerRepository.findById(orderItemCreationRequest.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOTFOUND));

        for(OrderItemRequestDTO orderItemRequestDTO : orderItemCreationRequest.getOrderItemRequestDTOS()) {

            Product product = productRepository.findById(orderItemRequestDTO.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

            Size size = sizeRepository.findById(orderItemRequestDTO.getSizeId())
                    .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOTFOUND));

            Color color = colorRepository.findById(orderItemRequestDTO.getColorId())
                    .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOTFOUND));

            KeyProductCustomer keyProductCustomer = KeyProductCustomer.builder()
                    .customerId(customer.getCustomerId())
                    .productId(product.getProductId())
                    .build();

            if(!cartRepository.existsByCustomerCustomerIdAndProductProductId(customer.getCustomerId(), product.getProductId())) {
                Cart cart = Cart.builder()
                        .keyProductCustomer(keyProductCustomer)
                        .customer(customer)
                        .product(product)
                        .size(size)
                        .color(color)
                        .quantity(orderItemRequestDTO.getQuantity())
                        .build();
                cartRepository.save(cart);
            } else {
                Cart cart = cartRepository.getCartByCustomerCustomerIdAndProductProductId(customer.getCustomerId(), product.getProductId());
                cart.setQuantity(cart.getQuantity() + 1);
                cartRepository.save(cart);
            }
        }
    }

    @Override
    @Transactional
    public void removeFromCart(ItemCartRequest itemCartRequest) {
        if(!cartRepository.existsByCustomerCustomerIdAndProductProductId(itemCartRequest.getCustomerId(), itemCartRequest.getProductId())) {
            return;
        }
        cartRepository.deleteByCustomerCustomerIdAndProductProductId(itemCartRequest.getCustomerId(), itemCartRequest.getProductId());
    }

    @Override
    public void updateCartItem(OrderItemRequestDTO orderItemRequestDTO) {

        Cart cart = cartRepository.getCartByCustomerCustomerIdAndProductProductId(orderItemRequestDTO.getCustomerId(), orderItemRequestDTO.getProductId());
        if(Objects.isNull(cart)) {
            return;
        }
        Product product = cart.getProduct();

        if(orderItemRequestDTO.getSizeId() != 0) {
            Size size = sizeRepository.findById(orderItemRequestDTO.getSizeId())
                    .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOTFOUND));
            cart.setSize(size);
        }
        if(orderItemRequestDTO.getColorId() != 0) {
            Color color = colorRepository.findById(orderItemRequestDTO.getColorId())
                    .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOTFOUND));
            cart.setColor(color);
        }
        if(orderItemRequestDTO.getQuantity() != 0) {
            cart.setQuantity(orderItemRequestDTO.getQuantity());
        }
        cartRepository.save(cart);
    }


    @Override
    public List<OrderItemDTO> getCartItems(int customerId) {

        List<Cart> carts = cartRepository.findAllByCustomerCustomerId(customerId);
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(Cart cart : carts) {
            Product product = cart.getProduct();
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .productName(product.getProductName())
                    .colorName(cart.getColor().getColorName())
                    .sizeName(cart.getSize().getSizeName())
                    .quantity(cart.getQuantity())
                    .price(cart.getQuantity() * product.getProductPrice())
                    .build();
            orderItemDTOS.add(orderItemDTO);
        }
        return orderItemDTOS;
    }

    @Override
    public PaymentDTO createPayment(int customerId) {

        Payment payment = paymentRepository.findByCustomerCustomerId(customerId);
        if(payment == null) throw new AppException(ErrorCode.CUSTOMER_NOTFOUND);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOTFOUND));

        Order order = orderRepository.findByCustomerCustomerId(customerId);
        payment.setAmount(order.getTotalPrice());
        paymentRepository.save(payment);

        return PaymentDTO.builder()
                .customerName(customer.getCustomerName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .amount(order.getTotalPrice())
                .build();
    }

    private List<OrderItem> generateOrderItem(List<OrderItemRequestDTO> orderItemRequestDTOS) {
        List<OrderItem> orderItems = new ArrayList<>();
        for(OrderItemRequestDTO orderItemRequestDTO :  orderItemRequestDTOS) {
            Size size = sizeRepository.findById(orderItemRequestDTO.getSizeId())
                    .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOTFOUND));

            Color color = colorRepository.findById(orderItemRequestDTO.getColorId())
                    .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOTFOUND));

            Product product = productRepository.findById(orderItemRequestDTO.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .size(size)
                    .color(color)
                    .quantity(orderItemRequestDTO.getQuantity())
                    .price(product.getProductPrice())
                    .build();

            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
