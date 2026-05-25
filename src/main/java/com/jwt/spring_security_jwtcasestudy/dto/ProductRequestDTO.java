package com.jwt.spring_security_jwtcasestudy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

// OpenAPI imports
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for creating a product")
public class ProductRequestDTO {

    /*
     * This class is used to receive product data from client (JSON → Java object)
     */

    @Schema(
            description = "Name of the product",
            example = "Laptop"
    )
    @NotBlank
    private String name;

    @Schema(
            description = "Category of the product",
            example = "Electronics"
    )
    @NotBlank
    private String category;

    @Schema(
            description = "Price of the product (must be positive value)",
            example = "50000"
    )
    @Positive
    private Double price;

    public ProductRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
