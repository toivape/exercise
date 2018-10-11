package com.ekahau.exercise.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {

    @Query("select a from Book a order by title asc")
    List<Book> listBooksByTitle();

    Book findByTitleAndAuthor(String title, String author);
}
