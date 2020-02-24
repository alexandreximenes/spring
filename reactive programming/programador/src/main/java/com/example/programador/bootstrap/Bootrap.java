package com.example.programador.bootstrap;

import com.example.programador.document.Programador;
import com.example.programador.repository.ProgramadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

//@Component
public class Bootrap implements CommandLineRunner {

    @Autowired
    private ProgramadorRepository repository;

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    private void init() {

        List<Programador> programadores = List.of(
                new Programador("Augusto", Math.random() * 100),
                new Programador("Julliana", Math.random() * 100),
                new Programador("Ailton", Math.random() * 100)
        );

        repository.deleteAll()
                .thenMany(Flux.fromIterable(programadores))
                .flatMap(repository::save)
                .thenMany(repository.findAll())
                .subscribe(System.out::println);

    }
}
