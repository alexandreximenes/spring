package com.example.pessoas.bootstrap;

import com.example.pessoas.document.Programador;
import com.example.pessoas.repository.ProgramadoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    @Autowired
    private ProgramadoresRepository programadoresRepository;

    @Override
    public void run(String... args) throws Exception {
        initData();

    }

    private void initData() {

        List<Programador> programadores = List.of(new Programador("Augusto", Math.random() * 100),
                new Programador("Julliana", Math.random() * 100),
                new Programador("Nascimento", Math.random() * 100),
                new Programador("Ailton", Math.random() * 100),
                new Programador("Dalpra", Math.random() * 100));

        programadoresRepository.deleteAll()
                .thenMany(Flux.fromIterable(programadores))
                .flatMap(programadoresRepository::save)
                .thenMany(programadoresRepository.findAll())
                .subscribe(System.out::println);
    }
}
