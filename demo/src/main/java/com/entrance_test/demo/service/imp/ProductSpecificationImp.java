package com.entrance_test.demo.service.imp;

import com.entrance_test.demo.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public interface ProductSpecificationImp {
    Specification<Product> categoryLike(int cateId);
    Specification<Product> styleLike(int styleId);
    Specification<Product> hasColorInProduct(int colorId);
    Specification<Product> hasSizeInProduct(int sizeId);

    Specification<Product> lessThanPrice(int price);
}
