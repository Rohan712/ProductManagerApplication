package com.example.ProductManagerApplication.repo;

import java.util.HashMap;
import java.util.Map;

import com.example.ProductManagerApplication.entity.Product;

public class ProductRepository {

	private Map<String, Product> products = new HashMap<>();
 
	public Product save(Product product) {
		products.put(product.getProductId(), product);
		return product;
	}

	public Product findById(String productId) {
		return products.get(productId);
	}

	public void deleteById(String productId) {
		products.remove(productId);
	}
}
