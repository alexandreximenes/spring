package com.livetouch.workshop.springreactiveprogramming.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@ToString
public class Programmer {

    @Id
    private String id;
    private String name;
    private Double rating;

    public Programmer(String name) {
        this.name = name;
    }

    public Programmer(String name, Double rating) {
        this.name = name;
        this.rating = rating;
    }
}
