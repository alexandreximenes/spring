package com.spring.reactiveprogramming.bootstrap;

import com.spring.reactiveprogramming.document.Pessoa;
import com.spring.reactiveprogramming.repository.PessoaReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class InitializationData implements CommandLineRunner {

    @Autowired
    private PessoaReactiveRepository pessoaReactiveRepository;

    @Override
    public void run(String... args) throws Exception {
        initData();
    }

    private void initData() {

        System.out.println("InitializationData");
        List<Pessoa> pessoas = List.of(new Pessoa("Alexandre", "xyymenes@gmail.com"),
                new Pessoa("Augusto", "augusto@gmail.com"),
                new Pessoa("Lecheta", "lecheta@gmail.com"),
                new Pessoa("Julliana", "julliana@gmail.com"),
                new Pessoa("Lucas", "lucas@gmail.com"));

        pessoaReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(pessoas))
                .flatMap(pessoaReactiveRepository::save)
                .thenMany(pessoaReactiveRepository.findAll())
                .subscribe(p -> System.out.println("Insert pessoa: " + p.getNome()));
    }
}
