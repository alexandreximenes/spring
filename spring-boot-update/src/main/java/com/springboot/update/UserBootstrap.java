package com.springboot.update;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserBootstrap implements CommandLineRunner {

//    @Autowired
//    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
//        userRepository.deleteAll();
//        userRepository.saveAll(generateUser());
    }

    private List<User> generateUser() {
        return null;//List.of(new User("Alexandre ", LocalDate.of(2019,2,27)), new User("Amanda", LocalDate.of(2017,2,27)), new User("Arthur", LocalDate.of(2016,2,27)));
    }
}
