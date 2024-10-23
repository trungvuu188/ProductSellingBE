package com.entrance_test.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity(name = "sale")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int saleId;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "end_date")
    Date endDate;

    @Column(name = "amount_sale")
    int amount_sale;

    @Column(name = "sale_status")
    String status;

    @OneToMany(mappedBy = "sale")
    Set<ProductSale> productSales;
}
