package com.learning.microservices.item.services;

import com.learning.microservices.item.Client.ClientFeign;
import com.learning.microservices.item.models.Item;
import com.learning.microservices.item.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements IItemService {

	@Autowired
	private ClientFeign clientFeign;

	@Override
	public Collection<Item> findAll() {
		Collection<Product> products = clientFeign.findAll();
		return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toSet());
	}

	@Override
	public Item findById(Long id, Integer size) {
		Product product = clientFeign.findById(id);
		return new Item(product, size);
	}
}
