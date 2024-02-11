package com.example.ProductManagerApplication.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProductManagerApplication.entity.Product;
@RestController
@RequestMapping("/api/products")
public class ProductManagerApplicationController {
	    private Map<String, Product> products = new HashMap<>();

	    @PostMapping
	    public Product createProduct(@RequestBody Product product) {
	        products.put(product.getProductId(), product);
	        return product;
	    }

	    @GetMapping("/{productId}")
	    public Object getProduct(@PathVariable String productId) {
	        Product product = products.get(productId);
	        if (product == null) {
	            return "Product not found";
	        }
	        return product;
	    }

	    @PutMapping("/{productId}")
	    public String updateProduct(@PathVariable String productId, @RequestBody Product product) {
	        if (!products.containsKey(productId)) {
	            return "Product not found";
	        }
	        product.setProductId(productId);
	        products.put(productId, product);
	        return "Product updated successfully";
	    }

	    @DeleteMapping("/{productId}")
	    public String deleteProduct(@PathVariable String productId) {
	        if (!products.containsKey(productId)) {
	            return "Product not found";
	        }
	        products.remove(productId);
	        return "Product deleted successfully";
	    }

	    @PostMapping("/{productId}/apply-discount")
	    public String applyDiscount(@PathVariable String productId, @RequestParam double discountPercentage) {
	        Product product = products.get(productId);
	        if (product == null) {
	            return "Product not found";
	        }
	        double discountedPrice = product.getPrice() * (1 - discountPercentage / 100);
	        product.setPrice(discountedPrice);
	        return "Discount applied successfully";
	    }

	    @PostMapping("/{productId}/apply-tax")
	    public String applyTax(@PathVariable String productId, @RequestParam double taxRate) {
	        Product product = products.get(productId);
	        if (product == null) {
	            return "Product not found";
	        }
	        double taxedPrice = product.getPrice() * (1 + taxRate / 100);
	        product.setPrice(taxedPrice);
	        return "Tax applied successfully";
	    }
	}
