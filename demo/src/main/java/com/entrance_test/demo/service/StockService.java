package com.entrance_test.demo.service;

import com.entrance_test.demo.dto.response.ProductDTO;
import com.entrance_test.demo.dto.response.ProductSaleApplied;
import com.entrance_test.demo.entity.Product;
import com.entrance_test.demo.entity.ProductSale;
import com.entrance_test.demo.entity.Sale;
import com.entrance_test.demo.exception.AppException;
import com.entrance_test.demo.exception.ErrorCode;
import com.entrance_test.demo.repository.ProductRepository;
import com.entrance_test.demo.repository.ProductSaleRepository;
import com.entrance_test.demo.repository.SaleRepository;
import com.entrance_test.demo.service.imp.ProductServiceImp;
import com.entrance_test.demo.service.imp.StockServiceImp;
import com.entrance_test.demo.utils.DateTimeService;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StockService implements StockServiceImp {

    SaleRepository saleRepository;
    ProductSaleRepository productSaleRepository;
    ProductRepository productRepository;
    DateTimeService dateTimeService;

    @PostConstruct
    public void saleExpirationDateManagement() {
        List<Sale> sales = saleRepository.findAll();
        for(Sale sale : sales) {
            if(sale.getEndDate().before(new Date())) {
                sale.setStatus("EXPIRED");
            }
        }
    }

    @Override
    public List<ProductSaleApplied> getListProductSale() {

        List<ProductSaleApplied> productSaleApplieds = new ArrayList<>();
        List<Sale> sales = saleRepository.getAllByStatus("ACTIVE");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(sales == null || sales.isEmpty()) {
            return null;
        }
        for(Sale sale : sales) {
            calendar.add(Calendar.DATE, sale.getEndDate().getDate());
            List<ProductSale> productSales = productSaleRepository.findAllBySaleSaleId(sale.getSaleId());
            List<Integer> ids = new ArrayList<>();
            for(ProductSale productSale : productSales) {
                ids.add(productSale.getProduct().getProductId());
            }
            ProductSaleApplied productSaleApplied = ProductSaleApplied.builder()
                    .amountSale(sale.getAmount_sale())
                    .productIds(ids)
                    .expiredDate(dateTimeService.remainingTimeBetweenDays(sale.getEndDate()))
                    .build();
            productSaleApplieds.add(productSaleApplied);
        }
        return productSaleApplieds;
    }

    @Override
    public int getProductStockPrice(int productId) {

        List<ProductSaleApplied> productSaleApplieds = getListProductSale();
        int amountSale = 0;
            for(ProductSaleApplied productSaleApplied : productSaleApplieds) {
            if(productSaleApplied.getProductIds().contains(productId)) {
                amountSale = productSaleApplied.getAmountSale();
            }
        };
        return amountSale;
    }

    @Override
    public String getProductSaleExpirationDate(int productId) {
        List<ProductSaleApplied> productSaleApplieds = getListProductSale();
        String str = "";
        for(ProductSaleApplied productSaleApplied : productSaleApplieds) {
            if(productSaleApplied.getProductIds().contains(productId)) {
                str = productSaleApplied.getExpiredDate();
            }
        };
        return str;
    }
}
