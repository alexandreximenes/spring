package com.course.springbootreactiveprogramming;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class FluxAndMonoFactory {

    @Test
    public void FluxMonoFactoryTest() {

        List<String> abc = List.of("A", "B", "C", "D", "E", "F");
        Flux<String> names = Flux.fromIterable(abc).log();

        StepVerifier.create(names)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void FluxMonoFactoryTestReverse() {

        Flux<String> names = Flux.fromIterable(List.of("A", "B", "C", "D", "E", "F")).log();


        List<String> abc = names.collectList()
                .block()
                .stream()
                .collect(Collectors.toList());

        System.out.println(abc);

    }

    @Test
    public void fluxUsingArray() {

        String[] names = new String[]{"a", "b", "c", "d"};
        Flux<String> stringFlux = Flux.fromArray(names).log();

        StepVerifier.create(stringFlux)
                .expectNext("a", "b", "c", "d")
                .verifyComplete();
    }


    @Test
    public void fluxUsingStream() {

        List<String> abc = List.of("A", "B", "C", "D", "E", "F");
        Flux<String> stringFlux = Flux.fromStream(abc.stream()).log();

        StepVerifier.create(stringFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void fluxUsingJustOrEmpty() {

        Mono<Object> objectMono = Mono.justOrEmpty(null);

        StepVerifier.create(objectMono)
                .verifyComplete();
    }

    @Test
    public void fluxUsingJustOrSupplier() {

        Supplier<String> stringSupplier = () -> "string-supplier";

        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);

        StepVerifier.create(stringMono)
                .expectNext("string-supplier")
                .verifyComplete();
    }

    @Test
    public void fluxUsingRange() {

        Flux<Integer> range = Flux.range(1, 100);

        StepVerifier.create(range)
                .expectNextCount(100)
                .verifyComplete();
    }


    @Test
    public void fluxUsingFilter() {

        List<String> abc = List.of("A", "B", "C", "D", "EE", "FF");
        Flux<String> stringFlux = Flux.fromStream(abc.stream())
                .filter(s -> nonNull(s) && s.length() == 1)
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void fluxUsingMap() {

        List<String> abc = List.of("A", "B", "C", "D", "EE", "FF");
        Flux<String> stringFlux = Flux.fromStream(abc.stream())
                .map(String::toLowerCase)
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void fluxUsingMapFilter() {

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
    public void fluxUsingBuffer() {

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

    @Test
    public void fluxUsingMerge() {

        Flux<String> justA = Flux.just("A", "B");
        Flux<String> justB = Flux.just("C", "D");

        //A, B, C, D
        Flux<String> merge = Flux.merge(justA, justB);

        StepVerifier.create(merge.log())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }


    @Test
    public void fluxUsingZip() {

        Flux<String> justA = Flux.just("A", "B");
        Flux<String> justB = Flux.just("C", "D");

        //AC, BD
        Flux<String> merge = Flux.zip(justA, justB, (t1, t2) -> t1.concat(t2));

        StepVerifier.create(merge.log())
                .expectSubscription()
                .expectNext("AC", "BD")
                .verifyComplete();
    }


    @Test
    public void fluxWithError() {

        Flux<String> just = Flux.just("A", "B")
                .concatWith(Flux.error(new RuntimeException("Erro aqui")))
                .concatWith(Flux.just("C"));

        StepVerifier.create(just.log())
                .expectSubscription()
                .expectNext("A", "B")
                .expectError(RuntimeException.class)
                .verify();
    }


    @Test
    public void fluxWithError_WithOnResumeError() {

        Flux<String> just = Flux.just("A", "B")
                .concatWith(Flux.error(new RuntimeException("Erro aqui")))
                .concatWith(Flux.just("C"))
                .onErrorResume((e) -> {
                    System.out.println("Capturando erro aqui e retornando outra flux default");
                    return Flux.just("default1", "default2");
                });

        StepVerifier.create(just.log())
                .expectSubscription()
                .expectNext("A", "B")
                .expectNext("default1", "default2")
                .verifyComplete();
    }

    @Test
    public void fluxWithError_WithOnErrorReturn() {

        Flux<String> just = Flux.just("A", "B")
                .concatWith(Flux.error(new RuntimeException("Erro aqui")))
                .concatWith(Flux.just("C"))
                .onErrorReturn("default");

        StepVerifier.create(just.log())
                .expectSubscription()
                .expectNext("A", "B")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    public void fluxWithError_WithOnErrorMap() {

        Flux<String> just = Flux.just("A", "B")
                .concatWith(Flux.error(new RuntimeException("Erro aqui")))
                .concatWith(Flux.just("C"))
                .onErrorMap((e) -> new CustomError(e.getMessage()));

        StepVerifier.create(just.log())
                .expectSubscription()
                .expectNext("A", "B")
//                .expectError(CustomError.class)
                .expectErrorMessage("Erro aqui")
                .verify();
    }

    @Test
    public void fluxWithError_WithOnErrorMap_Retry() {

        Flux<String> just = Flux.just("A", "B")
                .concatWith(Flux.error(new RuntimeException("Erro aqui")))
                .concatWith(Flux.just("C"))
                .onErrorMap((e) -> new CustomError(e.getMessage()))
                .retry(2);

        StepVerifier.create(just.log())
                .expectSubscription()
                .expectNext("A", "B")
                .expectNext("A", "B")
                .expectNext("A", "B")
                .expectError(CustomError.class)
                .verify();
    }

    @Test
    public void fluxWithError_WithOnErrorMap_RetryBackoff() {

        Flux<String> just = Flux.just("A", "B")
                .concatWith(Flux.error(new RuntimeException("Erro aqui")))
                .concatWith(Flux.just("C"))
                .onErrorMap((e) -> new CustomError(e.getMessage()))
                .retryBackoff(2, Duration.ofSeconds(5));

        StepVerifier.create(just.log())
                .expectSubscription()
                .expectNext("A", "B")
                .expectNext("A", "B")
                .expectNext("A", "B")
                .expectError(IllegalStateException.class)
                .verify();
    }


    @Test
    public void fluxWith_InfiniteSequence() throws InterruptedException {

        Flux<Long> infinite = Flux.interval(Duration.ofMillis(200)).log();

        infinite.subscribe((element) -> System.out.print(element + 1 + " | "));

        Thread.sleep(2000);

    }

    @Test
    public void fluxWith_finitFlux() throws InterruptedException {

        Flux<Integer> finitFlux = Flux
                .interval(Duration.ofMillis(100))
                .map(n -> n.intValue())
                .take(3)
                .log();

        StepVerifier.create(finitFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }


    @Test
    public void fluxWith_mapSum() throws InterruptedException {

        Flux<Integer> finitFlux = Flux
                .interval(Duration.ofMillis(100))
                .delayElements(Duration.ofSeconds(1))
                .map(n -> n.intValue() + 10)
                .take(3)
                .log();

        StepVerifier.create(finitFlux)
                .expectSubscription()
                .expectNext(10, 11, 12)
                .verifyComplete();
    }
}
