package com.example.programador.controller;

import com.example.programador.document.Programador;
import com.example.programador.repository.ProgramadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/v1")
public class ProgramadorController {

    @Autowired
    private ProgramadorRepository programadorRepository;

    //    @GetMapping
    public Flux<Programador> lista() {
        return programadorRepository.findAll();
    }


    @GetMapping(value = "/", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux listaStream() {
        return Flux.interval(Duration.ofSeconds(1)).take(100);
    }

    //    @GetMapping("/{id}")
    public Mono<Programador> getId(@PathVariable String id) {
        return programadorRepository.findById(id);
    }

    //    @PostMapping
    public Mono<Programador> save(@RequestBody Programador programador) {
        return programadorRepository.save(programador);
    }

    //    @PutMapping("/{id}")
    public Mono<Programador> update(@PathVariable String id, @RequestBody Programador programador) {
        return programadorRepository
                .findById(id)
                .flatMap(p -> {
                    p.setName(programador.getName());
                    p.setRating(programador.getRating());
                    return programadorRepository.save(p);
                }).switchIfEmpty(Mono.empty());
    }

    //    @DeleteMapping("/{id}")
    public Mono<Programador> delete(@PathVariable String id) {
        return programadorRepository.findById(id).flatMap(p -> programadorRepository.save(p));
    }
}
