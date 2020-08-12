package com.learning.microservices.item.services;

import com.learning.microservices.item.models.Item;

import java.util.Collection;
import java.util.Optional;

public interface IItemService {

    public Collection<Item> findAll();
    public Item findById(Long id, Integer size);
}
