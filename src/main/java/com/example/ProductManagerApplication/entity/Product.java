package com.example.ProductManagerApplication.entity;

import java.util.UUID;

public class Product {

	    private String productId;
	    private String name;
	    private String description;
	    private double price;
	    private int quantityAvailable;

	    // Constructors, getters, and setters
	    public Product() {
	        this.productId = UUID.randomUUID().toString();
	    }

	    public String getProductId() {
	        return productId;
	    }

	    public void setProductId(String productId) {
	        this.productId = productId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public double getPrice() {
	        return price;
	    }

	    public void setPrice(double price) {
	        this.price = price;
	    }

	    public int getQuantityAvailable() {
	        return quantityAvailable;
	    }

	    public void setQuantityAvailable(int quantityAvailable) {
	        this.quantityAvailable = quantityAvailable;
	    }
	}
