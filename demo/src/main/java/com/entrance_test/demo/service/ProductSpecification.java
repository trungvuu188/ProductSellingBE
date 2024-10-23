package com.entrance_test.demo.service;

import com.entrance_test.demo.entity.Product;
import com.entrance_test.demo.entity.ProductColor;
import com.entrance_test.demo.entity.ProductSize;
import com.entrance_test.demo.service.imp.ProductSpecificationImp;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecification implements ProductSpecificationImp {

    @Override
    public Specification<Product> categoryLike(int cateId) {
        return (root, query, builder) -> builder.equal(root.get("category").as(Integer.class), cateId);
    }

    @Override
    public Specification<Product> styleLike(int styleId) {
        return (root, query, builder) -> builder.equal(root.get("style").as(Integer.class), styleId);
    }

    @Override
    public Specification<Product> hasColorInProduct(int colorId) {
            return (root, query, builder) -> {
                Join<Product, ProductColor> productColors = root.join("productColors");
                return builder.equal(productColors.get("color").as(Integer.class), colorId);
            };
    }

    @Override
    public Specification<Product> hasSizeInProduct(int sizeId) {
        return (root, query, builder) -> {
            Join<Product, ProductSize> productSizes = root.join("productSizes");
            return builder.equal(productSizes.get("size").as(Integer.class), sizeId);
        };
    }

    @Override
    public Specification<Product> lessThanPrice(int price) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("productPrice"), price);
    }

}
