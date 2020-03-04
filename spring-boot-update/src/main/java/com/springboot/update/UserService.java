package com.springboot.update;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class UserService {

    //    private final UserRepository userRepository;
    private List<User> users = new ArrayList<>();

    public UserService() {
        this.users.clear();
        this.users.addAll(
                asList(
                        new User(1L, "Alexandre ", LocalDate.of(2019, 2, 27)),
                        new User(2L, "Amanda", LocalDate.of(2017, 2, 27)),
                        new User(3L, "Arthur", LocalDate.of(2016, 2, 27)))
        );
    }

    public List<User> findAll() {
        return findList();
    }

    private List<User> findList() {
        return this.users;
    }

    public User save(User user) {
        user.setId(getSizeListPlusOne());
        this.users.add(user);
        return user;
    }

    private long getSizeListPlusOne() {
        return this.users.size()+1L;
    }

    public User findById(Long id) {
        return this.users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }
}
