package com.ekahau.exercise;

import com.ekahau.exercise.db.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;

import java.util.List;

public class TestUtil {
    public static String jsonBook(String title, String author, Integer year) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new Book(title, author, year));
    }

    public static List<Book> getTestBooks() {
        return Lists.newArrayList(
                new Book("Pride and Prejudice", "Jane Austen", 1813),
                new Book("War and Peace", "Leo Tolstoy", 1869),
                new Book("The Old Man and the Sea", "Ernest Hemingway", 1952)
        );
    }
}
