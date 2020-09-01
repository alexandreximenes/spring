package com.learning.microservices.item.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class Item implements Serializable{

	private Product product;
	private Integer size;

	public Item(Product product, Integer size) {
		this.product = product;
		this.size 	= size;
	}

	public Double getTotal(){
		return product.getPrice() * this.size;
	}



}
