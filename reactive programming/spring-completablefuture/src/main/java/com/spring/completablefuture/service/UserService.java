package com.spring.completablefuture.service;

import com.spring.completablefuture.entity.User;
import com.spring.completablefuture.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Async
    public List<User> save1(List<MultipartFile> files) throws Exception {

        long start = System.currentTimeMillis();

        List<User> users = parseCSVFile(files);

        log.info("List of users '{}' - '{}'", users.size(), "" + Thread.currentThread().getName());

        users = userRepository.saveAll(users);

        long end = System.currentTimeMillis();

        long ms = end - start;

        log.info("Total time: {}", getFormattedTime(ms));

        return users;
    }

    @Async
    public CompletableFuture<List<User>> save2(List<MultipartFile> files) throws Exception {

        long start = System.currentTimeMillis();

        List<User> users = parseCSVFile(files);

        log.info("List of users '{}' - '{}'", users.size(), "" + Thread.currentThread().getName());

        users = userRepository.saveAll(users);

        long end = System.currentTimeMillis();

        long ms = end - start;

        log.info("Total time: {}", getFormattedTime(ms));

        return CompletableFuture.completedFuture(users);
    }

    private String getFormattedTime(long ms) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(ms), TimeUnit.MILLISECONDS.toMinutes(ms) % 60, TimeUnit.MILLISECONDS.toSeconds(ms));
    }

    @Async
    public CompletableFuture<Integer> countAll() {
        log.info("count all users {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(userRepository.findAll().size());
    }

    @Async
    public CompletableFuture<List<User>> findAll() {
        log.info("find all users {}", Thread.currentThread().getName());
        List<User> users = userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }

    public List<User> findAllNormal() {
        log.info("find all users {}", Thread.currentThread().getName());
        return userRepository.findAll();
    }

    private List<User> parseCSVFile(final List<MultipartFile> files) throws Exception {

        final List<User> users = new LinkedList<>();

        files.forEach(f -> {

            try (final BufferedReader br = new BufferedReader(new InputStreamReader(f.getInputStream()))) {
                String line = "";
                while (nonNull(line = br.readLine())) {
                    final String[] data = line.split(",");
                    final User user = new User();
                    user.setName(data[0]);
                    user.setEmail(data[1]);
                    user.setGender(data[2]);
                    users.add(user);
                }
            } catch (final IOException e) {
                log.info("Erro no parseCSVFile");
            }

        });

        return users;

    }

}
