package com.spring.completablefuture.controller;

import com.spring.completablefuture.entity.User;
import com.spring.completablefuture.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @SneakyThrows
    @PostMapping(value = "/save1", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save1(@RequestParam("files") List<MultipartFile> files) {
        List<User> users = userService.save1(files);
        return ResponseEntity.status(HttpStatus.CREATED).body(users);
    }

    @SneakyThrows
    @PostMapping(value = "/save2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save2(@RequestParam("files") List<MultipartFile> files) {
        CompletableFuture<List<User>> users = userService.save2(files);
        return ResponseEntity.status(HttpStatus.CREATED).body(users.get());
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<List<User>>> findAll() {
        return userService.findAll().thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/thread", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAllUsers() {
        CompletableFuture<List<User>> all1 = userService.findAll();
        CompletableFuture<List<User>> all2 = userService.findAll();
        CompletableFuture<List<User>> all3 = userService.findAll();
        CompletableFuture<List<User>> all4 = userService.findAll();
        CompletableFuture<List<User>> all5 = userService.findAll();
        CompletableFuture<List<User>> all6 = userService.findAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAllNormal() {
        return ResponseEntity.ok().body(userService.findAllNormal());
    }
}
