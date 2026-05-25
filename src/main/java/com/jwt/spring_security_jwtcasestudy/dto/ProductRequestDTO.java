package com.jwt.spring_security_jwtcasestudy.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO {
/*
 * this class needed by Jackson for JSON conversion
 * Request DTO is used to accept client request data and incoming JSON into java objects.
 */
    @NotBlank
    private String name;

    @NotBlank
    private String category;

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