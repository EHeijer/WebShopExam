package com.edheijer.WebShopExam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.edheijer.WebShopExam.dto.ProductDTO;
import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.models.ProductCategory;
import com.edheijer.WebShopExam.services.ProductMapper;
import com.edheijer.WebShopExam.services.ShoppingCartService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartServiceTests {

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Test
	public void addProductToCartTest() {
		
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		Product product2 = new Product(2L, "Nike", "Nike Pants", 599.0, "nike-pants-png", "p54A", ProductCategory.CLOTHES);
		Product product3 = new Product(3L, "Nocco", "24 x NOCCO BCAA, 330 ml", 499.0, "nocco-png", "p34A", ProductCategory.SUPPLEMENT);
		
		ProductDTO productDTO1 = productMapper.toDto(product1);
		ProductDTO productDTO2 = productMapper.toDto(product2);
		ProductDTO productDTO3 = productMapper.toDto(product3);
		
		shoppingCartService.addProductToCart(productDTO1);
		shoppingCartService.addProductToCart(productDTO2);
		shoppingCartService.addProductToCart(productDTO3);
		
		assertEquals(3, shoppingCartService.getProductsInCart().size());
	}
	
	@Test
	public void removeProductfromCartTest() {
		
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		Product product2 = new Product(2L, "Nike", "Nike Pants", 599.0, "nike-pants-png", "p54A", ProductCategory.CLOTHES);
		Product product3 = new Product(3L, "Nocco", "24 x NOCCO BCAA, 330 ml", 499.0, "nocco-png", "p34A", ProductCategory.SUPPLEMENT);
		
		ProductDTO productDTO1 = productMapper.toDto(product1);
		ProductDTO productDTO2 = productMapper.toDto(product2);
		ProductDTO productDTO3 = productMapper.toDto(product3);
		
		shoppingCartService.addProductToCart(productDTO1);
		shoppingCartService.addProductToCart(productDTO2);
		shoppingCartService.addProductToCart(productDTO3);
		shoppingCartService.removeProductfromCart(productDTO1);
		
		assertEquals(2, shoppingCartService.getProductsInCart().size());
	}
	
	@Test
	public void getCartSumTest() {
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		Product product2 = new Product(2L, "Nike", "Nike Pants", 599.0, "nike-pants-png", "p54A", ProductCategory.CLOTHES);
		Product product3 = new Product(3L, "Nocco", "24 x NOCCO BCAA, 330 ml", 499.0, "nocco-png", "p34A", ProductCategory.SUPPLEMENT);
		
		ProductDTO productDTO1 = productMapper.toDto(product1);
		ProductDTO productDTO2 = productMapper.toDto(product2);
		ProductDTO productDTO3 = productMapper.toDto(product3);
		
		shoppingCartService.addProductToCart(productDTO1);
		shoppingCartService.addProductToCart(productDTO2);
		shoppingCartService.addProductToCart(productDTO3);
		
		assertEquals(2097.0, shoppingCartService.getCartSum());
	}
	
	@Test
	public void incrementQuantityTest() {
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		ProductDTO productDTO1 = productMapper.toDto(product1);
		shoppingCartService.addProductToCart(productDTO1);
		shoppingCartService.incrementQuantity(productDTO1);
		
		assertEquals(2, shoppingCartService.getProductsInCart().get(productDTO1));
	}
	
	@Test
	public void decrementQuantityTest() {
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		ProductDTO productDTO1 = productMapper.toDto(product1);
		shoppingCartService.addProductToCart(productDTO1);
		shoppingCartService.incrementQuantity(productDTO1);
		shoppingCartService.incrementQuantity(productDTO1);
		shoppingCartService.incrementQuantity(productDTO1);
		shoppingCartService.decrementQuantity(productDTO1);
		
		assertEquals(3, shoppingCartService.getProductsInCart().get(productDTO1));
	}

}
