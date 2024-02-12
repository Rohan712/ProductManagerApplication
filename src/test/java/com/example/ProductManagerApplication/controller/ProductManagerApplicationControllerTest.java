package com.example.ProductManagerApplication.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.ProductManagerApplication.entity.Product;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductManagerApplicationController.class)
class ProductManagerApplicationControllerTest {
	 private ProductManagerApplicationController productController;
	    @Autowired
	    private MockMvc mockMvc;
	    @BeforeEach
	    void setUp() throws Exception {
	        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products")); // Clearing existing products before each test
	    }

	    @Test
	    void testCreateProduct() throws Exception {
	        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content("{\"name\":\"Book\",\"description\":\"Fiction book\",\"price\":20,\"quantityAvailable\":50}"))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").exists())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Book"))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Fiction book"))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(20.0))
	                .andExpect(MockMvcResultMatchers.jsonPath("$.quantityAvailable").value(50));
	    }

	    @Test
	    void testApplyDiscount() throws Exception {
	        String productId = createProductAndGetId("Book", "Fiction book", 20, 50);
	        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/" + productId + "/apply-discount")
	                .param("discountPercentage", "10"))
	                .andExpect(MockMvcResultMatchers.status().isOk());
	        // Verify if the price is updated after applying discount
	        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/" + productId))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(18.0));
	    }

	    @Test
	    void testApplyTax() throws Exception {
	        String productId = createProductAndGetId("Book", "Fiction book", 20, 50);
	        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/" + productId + "/apply-tax")
	                .param("taxRate", "5"))
	                .andExpect(MockMvcResultMatchers.status().isOk());
	        // Verify if the price is updated after applying tax
	        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/" + productId))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(21.0));
	    }

	    private String createProductAndGetId(String name, String description, double price, int quantityAvailable) throws Exception {
	        String content = String.format("{\"name\":\"%s\",\"description\":\"%s\",\"price\":%f,\"quantityAvailable\":%d}",
	                name, description, price, quantityAvailable);
	        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(content))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andReturn().getResponse().getContentAsString();
	        return response.split(":")[1].split(",")[0].replace("\"", "");
	    }
	    
}
