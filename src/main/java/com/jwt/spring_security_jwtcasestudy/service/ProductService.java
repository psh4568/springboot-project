package com.jwt.spring_security_jwtcasestudy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.spring_security_jwtcasestudy.JpaRepository.ProductRepository;
import com.jwt.spring_security_jwtcasestudy.dto.ProductRequestDTO;
import com.jwt.spring_security_jwtcasestudy.dto.ProductResponseDTO;
import com.jwt.spring_security_jwtcasestudy.entity.Product;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String createProduct(ProductRequestDTO request) {
    	
        Product product = new Product();

        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());

        productRepository.save(product);

        return "Product Created";
    }

   
    public List<ProductResponseDTO> getAllProducts() {

        List<Product> products =
                productRepository.findByDeletedFalse();

        List<ProductResponseDTO> responseList =
                new ArrayList<>();

        for (Product product : products) {

        	ProductResponseDTO response =
                    new ProductResponseDTO(
                            product.getName(),
                            product.getCategory(),
                            product.getPrice()
                    );

            responseList.add(response);
        }

        return responseList;
    }
    
    
}