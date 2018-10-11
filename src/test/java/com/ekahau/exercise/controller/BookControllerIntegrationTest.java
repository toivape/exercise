package com.ekahau.exercise.controller;

import com.ekahau.exercise.Main;
import com.ekahau.exercise.db.Book;
import com.ekahau.exercise.db.BookRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.ekahau.exercise.TestUtil.getTestBooks;
import static com.ekahau.exercise.TestUtil.jsonBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Main.class)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepo bookRepo;

    @Before
    public void clearDatabase() {
        bookRepo.deleteAllInBatch();
    }

    @Test
    public void addBook() throws Exception {
        List<Book> savedBooks = addBooksToDatabase();

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook("The Catcher in the Rye", "J. D. Salinger", 1951)))
                .andExpect(status().isOk());

        List<Book> books = bookRepo.findAll();
        assertThat(books).size().isEqualTo(savedBooks.size()+1);
        assertThat(books).extracting(Book::getTitle).contains("The Catcher in the Rye");
    }

    @Test
    public void givenAddBook_whenBookExists_exceptionIsReturned() throws Exception {
        Book existingBook = addBooksToDatabase().get(0);
        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook(existingBook.getTitle(), existingBook.getAuthor(), existingBook.getYear())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Book already exists: Pride and Prejudice - Jane Austen")));
    }

    @Test
    public void listBooks() throws Exception {
        addBooksToDatabase();
        mockMvc.perform(get("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", is("Pride and Prejudice")));
    }

    @Test
    public void deleteBook() throws Exception {
        List<Book> savedBooks = addBooksToDatabase();

        mockMvc.perform(delete("/api/v1/books/{bookId}", savedBooks.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<Book> books = bookRepo.findAll();
        assertThat(books).size().isEqualTo(savedBooks.size()-1);
        assertThat(books).extracting(Book::getTitle).containsOnly("War and Peace","The Old Man and the Sea");
    }

    private List<Book> addBooksToDatabase() {
        return bookRepo.save(getTestBooks());
    }

}
