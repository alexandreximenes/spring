package com.learning.microservices.products.services;

import com.learning.microservices.products.model.Product;
import com.learning.microservices.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service("serviceRestTemplate")
public class ProductService implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = true)
	@Override
	public Collection<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	
}
