package com.course.springbootreactiveprogramming;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {

        Flux<String> stringFlux = Flux
                .just("Spring", "boot", "Application", "learn", "reative", "programming")
                .concatWith(Flux.error(new RuntimeException("Exception ocurred!")))
                .log()
                .onErrorResume((e) -> {
                    System.out.println(e.getMessage());
                    return Flux.empty();
                });

        stringFlux.subscribe(System.out::println,
                (e) -> System.err.println(e),
                () -> System.out.println("Completed")
        );
    }

    @Test
    public void fluxElementWithoutError() {

        Flux<String> flux = Flux
                .just("Spring", "boot", "Application", "learn", "reative", "programming");

        StepVerifier.create(flux.log())
                .expectNext("Spring")
                .expectNext("boot")
                .expectNext("Application")
                .expectNext("learn")
                .expectNext("reative")
                .expectNext("programming")
                .verifyComplete();
    }

    @Test
    public void fluxElementWithError() {

        Flux<String> flux = Flux
                .just("Spring", "boot", "Application", "learn", "reative", "programming")
                .concatWith(Flux.error(new RuntimeException("Exception ocurred!")));

        StepVerifier.create(flux.log())
                .expectNextCount(6)
//                .expectError(RuntimeException.class)
                .expectErrorMessage("Exception ocurred!")
                .verify();
    }

    @Test
    public void monoTest() {

        Mono<String> mono = Mono.just("Spring");

        StepVerifier.create(mono.log())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void monoTestWithError() {

        Mono<String> mono = Mono.error(new RuntimeException("Exception Ocurred!"));

        StepVerifier.create(mono.log())
                .expectError(RuntimeException.class)
                .verify();
    }

}
