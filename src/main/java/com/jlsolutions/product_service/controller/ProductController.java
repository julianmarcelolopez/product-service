package com.jlsolutions.product_service.controller;

import com.jlsolutions.commons.ProductEvent;
import com.jlsolutions.product_service.kafka.KafkaProducer;
import com.jlsolutions.product_service.model.Product;
import com.jlsolutions.commons.ProductDTO;
import com.jlsolutions.product_service.service.ProductService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;
	private final KafkaProducer kafkaProducer;



	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable String id) {
		return productService.getProductById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
		ProductDTO createdProductDTO =
				ProductDTO.builder().id(createdProduct.getId()).name(createdProduct.getName())
						.price(createdProduct.getPrice()).description(createdProduct.getDescription()).build();
//		kafkaProducer.sendProductEvent("ProductCreated", createdProductDTO);
		kafkaProducer.sendProductEvent(new ProductEvent("ProductCreated", createdProductDTO));

		return createdProduct;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable String id,
												 @RequestBody Product productDetails) {
		Product updatedProduct = productService.updateProduct(id, productDetails);
		ProductDTO updatedProductDTO =
				ProductDTO.builder().id(updatedProduct.getId()).name(updatedProduct.getName())
						.price(updatedProduct.getPrice()).description(updatedProduct.getDescription()).build();
//		kafkaProducer.sendProductEvent("ProductUpdated", updatedProductDTO);
		kafkaProducer.sendProductEvent(new ProductEvent("ProductUpdated", updatedProductDTO));

		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
		productService.deleteProduct(id);
		ProductDTO deletedProductDTO = ProductDTO.builder().id(id).build();
//		kafkaProducer.sendProductEvent("ProductDeleted", deletedProductDTO);
		kafkaProducer.sendProductEvent(new ProductEvent("ProductDeleted", deletedProductDTO));

		return ResponseEntity.noContent().build();
	}
}