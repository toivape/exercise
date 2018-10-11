package com.ekahau.exercise.controller;

import com.ekahau.exercise.db.Book;
import com.ekahau.exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> listBooks(){
        return bookService.listBooksByTitle();
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId){
        bookService.deleteBook(bookId);
    }

    @PostMapping
    public void addBook(@Valid @RequestBody BookForm bookForm){
        bookService.addBook(bookForm.getTitle(), bookForm.getAuthor(), bookForm.getYear());
    }

}
