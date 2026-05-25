package com.jwt.spring_security_jwtcasestudy.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.spring_security_jwtcasestudy.entity.Product;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByDeletedFalse();
}
