package com.learning.microservices.products.services;

import java.util.Collection;
import java.util.Optional;

import com.learning.microservices.products.model.Product;

public interface IProductService {

	public Collection<Product> findAll();
	public Product findById(Long id);
}
