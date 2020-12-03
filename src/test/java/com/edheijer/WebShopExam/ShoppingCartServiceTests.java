package com.edheijer.WebShopExam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.models.ProductCategory;
import com.edheijer.WebShopExam.services.ShoppingCartService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartServiceTests {

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Test
	public void addProductToCartTest() {
		
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		Product product2 = new Product(2L, "Nike", "Nike Pants", 599.0, "nike-pants-png", "p54A", ProductCategory.CLOTHES);
		Product product3 = new Product(3L, "Nocco", "24 x NOCCO BCAA, 330 ml", 499.0, "nocco-png", "p34A", ProductCategory.SUPPLEMENT);
		
		shoppingCartService.addProductToCart(product1);
		shoppingCartService.addProductToCart(product2);
		shoppingCartService.addProductToCart(product3);
		
		assertEquals(3, shoppingCartService.getProductsInCart().size());
	}
	
	@Test
	public void removeProductfromCartTest() {
		
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		Product product2 = new Product(2L, "Nike", "Nike Pants", 599.0, "nike-pants-png", "p54A", ProductCategory.CLOTHES);
		Product product3 = new Product(3L, "Nocco", "24 x NOCCO BCAA, 330 ml", 499.0, "nocco-png", "p34A", ProductCategory.SUPPLEMENT);
		
		shoppingCartService.addProductToCart(product1);
		shoppingCartService.addProductToCart(product2);
		shoppingCartService.addProductToCart(product3);
		shoppingCartService.removeProductfromCart(product1);
		
		assertEquals(2, shoppingCartService.getProductsInCart().size());
	}
	
	@Test
	public void getCartSumTest() {
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		Product product2 = new Product(2L, "Nike", "Nike Pants", 599.0, "nike-pants-png", "p54A", ProductCategory.CLOTHES);
		Product product3 = new Product(3L, "Nocco", "24 x NOCCO BCAA, 330 ml", 499.0, "nocco-png", "p34A", ProductCategory.SUPPLEMENT);
		
		shoppingCartService.addProductToCart(product1);
		shoppingCartService.addProductToCart(product2);
		shoppingCartService.addProductToCart(product3);
		
		assertEquals(2097.0, shoppingCartService.getCartSum());
	}
	
	@Test
	public void incrementQuantityTest() {
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		shoppingCartService.addProductToCart(product1);
		shoppingCartService.incrementQuantity(product1);
		
		assertEquals(2, shoppingCartService.getProductsInCart().get(product1));
	}
	
	@Test
	public void decrementQuantityTest() {
		Product product1 = new Product(1L, "Adidas", "adidas shoes", 999.0, "adidas-shoes-png", "p34A", ProductCategory.TRAINING_SHOES);
		shoppingCartService.addProductToCart(product1);
		shoppingCartService.decrementQuantity(product1);
		
		assertEquals(0, shoppingCartService.getProductsInCart().get(product1));
	}
}
