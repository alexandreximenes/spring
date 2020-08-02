package org.meusite.repository;

import org.meusite.user.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    Map<String, User> map = new HashMap<>();

    @PostConstruct
    private final void initialize(){

        User ale = new User();
        ale.setId(1);
        ale.setName("Ale");
        ale.setEmail("xyy@gmailc.om");
        ale.setSalary(10_000);

        User day = new User();
        day.setId(1);
        day.setName("Day");
        day.setEmail("day@gmailc.om");
        day.setSalary(5_000);

        User arthur = new User();
        arthur.setId(1);
        arthur.setName("Arthur");
        arthur.setEmail("arthur@gmailc.om");
        arthur.setSalary(1_000);

        map.put(ale.getName(), ale);
        map.put(day.getName(), day);
        map.put(arthur.getName(), arthur);
    }

    public Optional<User> findByName(String name){
        return Optional.ofNullable(map.get(name));
    }
}
