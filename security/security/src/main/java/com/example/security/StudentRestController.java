package com.example.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class StudentRestController {

    private List<Student> STUDENTS = List.of(
            new Student(1, "Alexandre Ximenes"),
            new Student(2, "Amanda Ximenes"),
            new Student(3, "Arthur Ximenes")
    );

    @GetMapping("/{id}")
    public Student findById(@PathVariable Integer id) {
        return STUDENTS.stream()
                .filter(student -> id.equals(student.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(id + " não encontrado"));
    }
}
