package com.livetouch.workshop.springreactiveprogramming;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringReactiveProgrammingApplicationTests {

    @Test
    void contextLoads() {

        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
