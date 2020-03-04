package com.livetouch.workshop.springreactiveprogramming.bootstrap;

import com.livetouch.workshop.springreactiveprogramming.document.Programmer;
import com.livetouch.workshop.springreactiveprogramming.repository.ProgrammerReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InitData implements CommandLineRunner {

    private final ProgrammerReactiveRepository programmerReactiveRepository;

    @Override
    public void run(String... args) throws Exception {
        runData();
    }

    private void runData() {

        List<Programmer> programmers = List.of(
                new Programmer("Alexandre", Math.random() * 100),
                new Programmer("Augusto", Math.random() * 100),
                new Programmer("Jullinana", Math.random() * 100),
                new Programmer("Henrique", Math.random() * 100),
                new Programmer("5", "Lecheta", Math.random() * 100)
        );

        programmerReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(programmers))
                .flatMap(programmerReactiveRepository::save)
                .thenMany(programmerReactiveRepository.findAll())
                .log()
                .subscribe(System.out::println);
    }
}
