package org.meusite.service;

import org.meusite.repository.UserRepository;
import org.meusite.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getName(String name) {
        return userRepository
                .findByName(name)
                .filter(Objects::nonNull)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
