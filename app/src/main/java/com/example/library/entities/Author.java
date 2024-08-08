package com.example.library.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Author {
    private long id;
    private String name;
    private String surname;
    private List<Book> books;
}
