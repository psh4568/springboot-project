package com.jwt.spring_security_jwtcasestudy.dto;
public class ProductResponseDTO {
	    private String name;
	    private String category;
	    private Double price;
	    /*this is used to send controlled response data back to client
	     *  by converting java objects into JSON
	     */
	  public   ProductResponseDTO(String name , String category,Double price) {
		  this.name = name;
		  this.category = category;
		  this.price = price;
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
