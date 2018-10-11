package com.ekahau.exercise.service;

import com.ekahau.exercise.db.Book;
import com.ekahau.exercise.db.BookRepo;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.ekahau.exercise.TestUtil.getTestBooks;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepo bookRepo;

    @Before
    public void clearDatabase() {
        bookRepo.deleteAllInBatch();
    }

    @Test
    public void booksAreOrderedByTitle(){
        addBooksToDatabase();

        List<Book> books = bookService.listBooksByTitle();

        softly.assertThat(books).size().isEqualTo(3);
        softly.assertThat(books).extracting(Book::getTitle).containsExactly("Pride and Prejudice", "The Old Man and the Sea", "War and Peace");
    }

    @Test
    public void bookIsDeleted(){
        List<Book> savedBooks = addBooksToDatabase();

        long bookId = savedBooks.get(0).getId();
        bookService.deleteBook(bookId);

        assertThat(bookRepo.findOne(bookId)).isNull();
    }

    @Test
    public void giveDeleteBook_whenBookIsNotFound_thenExceptionIsThrown(){
        thrown.expect(BookNotFoundException.class);
        thrown.expectMessage("Book not found by id 0");
        bookService.deleteBook(0L);
    }

    @Test
    public void bookIsAdded(){
        bookService.addBook("War and Peace", "Leo Tolstoy", 1869);
        List<Book> books = bookRepo.findAll();
        softly.assertThat(books).size().isEqualTo(1);
        softly.assertThat(books).first().hasFieldOrPropertyWithValue("title", "War and Peace");
    }

    @Test
    public void givenAddBook_whenBookExists_thenExceptionIsThrown(){
        addBooksToDatabase();
        thrown.expect(BookExistsException.class);
        thrown.expectMessage("Book already exists: War and Peace - Leo Tolstoy");
        bookService.addBook("War and Peace", "Leo Tolstoy", 1869);
    }

    @Test
    public void givenIsExistingBook_whenBookExists_returnTrue(){
        addBooksToDatabase();
        boolean isExisting = bookService.isExistingBook("Pride and Prejudice", "Jane Austen");
        assertThat(isExisting).isTrue();
    }

    @Test
    public void givenIsExistingBook_whenBookDoesNotExists_thenReturnFalse(){
        addBooksToDatabase();
        boolean isExisting = bookService.isExistingBook("xxx", "yyy");
        assertThat(isExisting).isFalse();
    }

    private List<Book> addBooksToDatabase() {
        return bookRepo.save(getTestBooks());
    }

}