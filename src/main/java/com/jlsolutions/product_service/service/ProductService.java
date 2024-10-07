package com.jlsolutions.product_service.service;

import com.jlsolutions.product_service.model.Product;
import com.jlsolutions.product_service.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProductById(String id) {
		return productRepository.findById(id);
	}

	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(String id, Product productDetails) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		product.setName(productDetails.getName());
		product.setPrice(productDetails.getPrice());
		product.setDescription(productDetails.getDescription());
		return productRepository.save(product);
	}

	public void deleteProduct(String id) {
		productRepository.deleteById(id);
	}
}