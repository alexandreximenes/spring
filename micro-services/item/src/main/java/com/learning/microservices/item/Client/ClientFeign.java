package com.learning.microservices.item.Client;

import com.learning.microservices.item.models.Item;
import com.learning.microservices.item.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@FeignClient(name = "service-products", url = "localhost:8001")
public interface ClientFeign{

    @GetMapping("/products")
    public Collection<Product> findAll();

    @GetMapping("/products/{id}")
    public Product findById(@PathVariable Long id);


}
