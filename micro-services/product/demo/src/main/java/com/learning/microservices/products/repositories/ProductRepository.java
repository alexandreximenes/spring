package com.learning.microservices.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.microservices.products.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
