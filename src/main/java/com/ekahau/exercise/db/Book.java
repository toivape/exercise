package com.ekahau.exercise.db;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {
/*
    id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1000000, INCREMENT BY 1) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    year INTEGER NOT NULL
  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    @Size(max = 100)
    private String title;

    @Column(name = "author", length = 100, nullable = false)
    @Size(max = 100)
    private String author;

    @Column(name = "year")
    private Integer year;

    public Book() {
    }

    public Book(String title, String author, Integer year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
