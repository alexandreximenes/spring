package com.learning.microservices.products.clientFeign;

import com.learning.microservices.products.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Optional;

@FeignClient(name = "service-products", url = "localhost:8001")
public interface ClientFeign {
	
	@GetMapping("/products")
	public Collection<Product> findAll();

	@GetMapping("/{id}")
	public Optional<Product> findById(@PathVariable Long id);
}
