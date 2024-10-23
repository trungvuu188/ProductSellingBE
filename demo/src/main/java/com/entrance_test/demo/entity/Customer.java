package com.entrance_test.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int customerId;

    @Column(name = "customer_name")
    String customerName;

    @Column(name = "phone")
    String phone;

    @Column(name = "email")
    String email;

    @Column(name = "address")
    String address;

    @Column(name = "password")
    String password;

    @OneToMany(mappedBy = "customer")
    Set<Wishlist> wishlists;

    @OneToMany(mappedBy = "customer")
    Set<Cart> carts;

    @OneToMany(mappedBy = "customer")
    Set<Payment> payments;

    @OneToMany(mappedBy = "customer")
    Set<Rating> ratings;
}
