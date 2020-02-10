package com.course.springbootreactiveprogramming;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class FluxAndMonoFactory {

    @Test
    public void FluxMonoFactoryTest(){

        List<String> abc = List.of("A", "B", "C", "D", "E", "F");
        Flux<String> names = Flux.fromIterable(abc).log();

        StepVerifier.create(names)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void FluxMonoFactoryTestReverse(){

        Flux<String> names = Flux.fromIterable(List.of("A", "B", "C", "D", "E", "F")).log();


        List<String> abc = names.collectList()
                .block()
                .stream()
                .collect(Collectors.toList());

        System.out.println(abc);

    }

    @Test
    public void fluxUsingArray(){

        String[] names = new String[]{"a", "b", "c", "d"};
        Flux<String> stringFlux = Flux.fromArray(names).log();

        StepVerifier.create(stringFlux)
                .expectNext("a", "b", "c", "d")
                .verifyComplete();
    }


    @Test
    public void fluxUsingStream(){

        List<String> abc = List.of("A", "B", "C", "D", "E", "F");
        Flux<String> stringFlux = Flux.fromStream(abc.stream()).log();

        StepVerifier.create(stringFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void fluxUsingJustOrEmpty(){

        Mono<Object> objectMono = Mono.justOrEmpty(null);

        StepVerifier.create(objectMono)
                .verifyComplete();
    }

    @Test
    public void fluxUsingJustOrSupplier(){

        Supplier<String> stringSupplier = () -> "string-supplier";

        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);

        StepVerifier.create(stringMono)
                .expectNext("string-supplier")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange(){

        Flux<Integer> range = Flux.range(1, 100);

        StepVerifier.create(range)
                .expectNextCount(100)
                .verifyComplete();
    }


    @Test
    public void fluxUsingFilter(){

        List<String> abc = List.of("A", "B", "C", "D", "EE", "FF");
        Flux<String> stringFlux = Flux.fromStream(abc.stream())
                .filter(s -> nonNull(s) && s.length() == 1)
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void fluxUsingMap(){

        List<String> abc = List.of("A", "B", "C", "D", "EE", "FF");
        Flux<String> stringFlux = Flux.fromStream(abc.stream())
                .map(String::toLowerCase)
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void fluxUsingMapFilter(){

        List<String> abc = List.of("A", "B", "C", "D", "EE", "FF");
        Flux<String> stringFlux = Flux.fromStream(abc.stream())
                .filter(s -> s.length() == 1)
                .map(s -> s.concat(" Flux"))
                .map(String::toLowerCase)
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void fluxUsingBuffer(){

        List<String> abc = List.of("A", "B", "C", "D", "EE", "FF");
        Flux<List<String>> listFlux = Flux.fromStream(abc.stream())
                .filter(s -> s.length() == 1)
                .map(s -> s.concat(" Flux"))
                .map(String::toLowerCase)
                .buffer(3)
                .log();

        StepVerifier.create(listFlux)
                .expectNextCount(2)
                .verifyComplete();
    }




}
