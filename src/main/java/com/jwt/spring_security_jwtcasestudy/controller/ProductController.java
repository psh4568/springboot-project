package com.jwt.spring_security_jwtcasestudy.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.spring_security_jwtcasestudy.dto.ProductRequestDTO;
import com.jwt.spring_security_jwtcasestudy.dto.ProductResponseDTO;
import com.jwt.spring_security_jwtcasestudy.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/products")
//using openapi annotations

@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;
 // -------------------- CREATE PRODUCT --------------------
    @Operation(
            summary = "Create a new product",
            description = "This API creates a new product and stores it in the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "Product created successfully")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data"
            )
    })
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
    
    @Operation(
            summary = "Get all products",
            description = "This API retrieves all products from the database"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products =
                productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
   
    
}
