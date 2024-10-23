package com.entrance_test.demo.repository;

import com.entrance_test.demo.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer>, JpaSpecificationExecutor<Style> {
}
