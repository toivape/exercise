package com.example.exercise.service;

import com.example.exercise.db.Book;
import com.example.exercise.db.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;

    public List<Book> listBooksByTitle() {
        return bookRepo.listBooksByTitle();
    }

    public void deleteBook(Long bookId) {
        Book book = bookRepo.findOne(bookId);

        if (book==null){
            throw new BookNotFoundException("Book not found by id " + bookId);
        }

        bookRepo.delete(book);
    }

    public void addBook(String title, String author, Integer year) {
        if (isExistingBook(title, author)){
            throw new BookExistsException("Book already exists: " + title + " - " + author);
        }
        Book book = new Book(title, author, year);
        bookRepo.save(book);
    }

    boolean isExistingBook(String title, String author) {
        Book book = bookRepo.findByTitleAndAuthor(title, author);
        return book!=null;
    }
}
