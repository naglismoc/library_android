package com.example.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")//saugiklis nuo rekursijos
public class Book {
    private long id;

    private String title;
    private String genre;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonBackReference
//    @JoinColumn(name = "author_id", nullable = false)
    private Long author;
}