package com.learning.microservices.products.controllers;

import java.util.Collection;

import com.learning.microservices.products.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.microservices.products.model.Product;
import com.learning.microservices.products.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	@Qualifier("serviceRestTemplate")
	private IProductService productService;
	
	@GetMapping
	public Collection<Product> findAll(){
		Collection<Product> findAll = productService.findAll();
		return findAll;
	}
	
	@GetMapping("/{id}")
	public Product findById(@PathVariable Long id){
		return productService.findById(id);
	}
}
