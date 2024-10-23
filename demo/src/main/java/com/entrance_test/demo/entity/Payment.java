package com.entrance_test.demo.entity;

import com.entrance_test.demo.entity.keys.KeyCustomerOrder;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int paymentId;

    @OneToMany(mappedBy = "payment")
    Set<Order> orders;

    @ManyToOne
    @JoinColumn(name = "customer_Id")
    Customer customer;

    @Column(name = "payment_date")
    Date paymentDate;

    @Column(name = "amount")
    int amount;
}
