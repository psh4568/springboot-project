package com.jwt.spring_security_jwtcasestudy.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jwt.spring_security_jwtcasestudy.dto.ProductRequestDTO;
import com.jwt.spring_security_jwtcasestudy.dto.ProductResponseDTO;
import com.jwt.spring_security_jwtcasestudy.service.ProductService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PostMapping
    public ResponseEntity<String> createProduct(
            @Valid @RequestBody ProductRequestDTO request
    ) {
    	System.out.print("response coming here");
        String response =
                productService.createProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products =
                productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
   
    
}