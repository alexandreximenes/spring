package com.spring.reactiveprogramming.controller;

import com.spring.reactiveprogramming.constants.EndPoints;
import com.spring.reactiveprogramming.document.Pessoa;
import com.spring.reactiveprogramming.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(EndPoints.PESSOAS)
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping(EndPoints.ALL)
    public Flux<Pessoa> findAll(){
        return pessoaService.findAll();
    }

    @GetMapping(EndPoints.ID)
    public Mono<ResponseEntity<Pessoa>> findById(@PathVariable String id){
        return pessoaService.findById(id).map(pessoa -> new ResponseEntity<>(pessoa, HttpStatus.OK));
    }

    @PostMapping
    public Mono<ResponseEntity<Pessoa>> save(@RequestBody Pessoa pessoa){
        return pessoaService.save(pessoa).map(p-> new ResponseEntity<>(p, HttpStatus.OK));
    }

    @PutMapping(EndPoints.ID)
    public Mono<ResponseEntity<Pessoa>> update(@PathVariable String id, @RequestBody Pessoa pessoa){
        return pessoaService.update(id, pessoa).map(p -> new ResponseEntity<>(p, HttpStatus.OK));
    }

    @DeleteMapping(EndPoints.ID)
    public Mono<ResponseEntity<Pessoa>> delete(@PathVariable String id){
        return pessoaService.delete(id).map(p -> new ResponseEntity<>(HttpStatus.OK));
    }

}
