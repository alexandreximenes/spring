package com.learning.microservices.item.models;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Product implements Serializable{

	private Long id;
	private String name;
	private Double price;
	private Date createdAt;
	
}
