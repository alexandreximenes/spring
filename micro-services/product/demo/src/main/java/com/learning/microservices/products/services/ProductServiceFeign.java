package com.learning.microservices.products.services;

import com.learning.microservices.products.clientFeign.ClientFeign;
import com.learning.microservices.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service("serviceFeign")
public class ProductServiceFeign implements IProductService {

	@Autowired
	private ClientFeign clientFeign;

	@Transactional(readOnly = true)
	@Override
	public Collection<Product> findAll() {
		return clientFeign.findAll();
	}

	@Override
	public Product findById(Long id) {
		return clientFeign.findById(id).orElse(null);
	}

	
}
