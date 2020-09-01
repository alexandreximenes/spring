package com.learning.microservices.item.controller;


import com.learning.microservices.item.models.Item;
import com.learning.microservices.item.services.IItemService;
import com.learning.microservices.item.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private IItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> findAll(){
        Collection<Item> findAll = itemService.findAll();
        return new ResponseEntity(findAll, HttpStatus.OK);
    }

    @GetMapping("/{id}/{size}")
    public ResponseEntity<Item> findById(@PathVariable Long id, @PathVariable Integer size){
        return new ResponseEntity(itemService.findById(id, size), HttpStatus.OK);
    }
}
