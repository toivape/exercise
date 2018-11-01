package com.example.exercise.controller;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookForm {

    @NotNull(message = "{bookForm.title.required}")
    @Size(min=1, max = 100, message = "{bookForm.title.length}")
    private String title;

    @NotNull(message = "{bookForm.author.required}")
    @Size(min=1, max = 100, message = "{bookForm.author.length}")
    private String author;

    @NotNull(message = "{bookForm.year.required}")
    private Integer year;

    @Override
    public String toString() {
        return "BookForm{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
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
