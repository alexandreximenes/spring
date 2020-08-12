package com.learning.microservices.item.services;

import com.learning.microservices.item.models.Item;
import com.learning.microservices.item.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
public class ItemService implements IItemService {

	@Autowired
	private RestTemplate template;
	@Override
	public Collection<Item> findAll() {
		List<Product> products = asList(template.getForObject("http://localhost:8001/products", Product[].class));
		return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toSet());
	}

	@Override
	public Item findById(Long id, Integer size) {
		Map<String, String> map = new HashMap<>();
		map.put("id", id.toString());
		Product product = template.getForObject("http://localhost:8001/products/{id}", Product.class, map);
		return new Item(product, size);
	}

	
}
