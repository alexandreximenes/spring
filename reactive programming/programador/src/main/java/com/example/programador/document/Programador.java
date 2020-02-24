package com.example.programador.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Programador {

    @Id
    @ToString.Exclude
    private String id;
    private String name;
    @ToString.Exclude
    private Double rating;

    public Programador(String name, Double rating) {
        this.name = name;
        this.rating = rating;
    }
}
