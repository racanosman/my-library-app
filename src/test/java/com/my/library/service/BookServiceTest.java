package com.my.library.service;

import com.my.library.BookRepositoryStub;
import com.my.library.exception.BookNotFoundException;
import com.my.library.model.Book;
import com.my.library.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;

import static com.my.library.util.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BookServiceTest {

    private final String BOOK_GRUFF_472_REF = BOOK_PREFIX + "GRUFF472";
    private final String BOOK_GRUFF_472_TITLE = "The Gruffalo";
    private final String BOOK_GRUFF_472_REVIEW = "A mouse taking a walk in the woods.";
    private final String BOOK_WILL987_REF = BOOK_PREFIX + "WILL987";
    private final String BOOK_POOH222_REF = BOOK_PREFIX + "POOH222";
    private BookService bookService;
    private BookRepository bookRepository;

    @Before
    public void setUp() {
        this.bookRepository = new BookRepositoryStub();
        this.bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    public void givenBook_whenRetrieveWithNoPrefix_thenThrowException() {
        Exception bookNotFoundException = assertThrows(BookNotFoundException.class, () -> {
            bookService.retrieveBook(INVALID_TEXT);
        });
        assertEquals(EXCEPTION_MESSAGE_NO_PREFIX, bookNotFoundException.getMessage());
    }

    @Test
    public void givenBook_whenRetrievedWithInvalidReference_thenThrowException() {
        BookNotFoundException bookNotFoundException = assertThrows(BookNotFoundException.class, () -> {
            bookService.retrieveBook(BOOK_999);
        });
        assertEquals(EXCEPTION_MESSAGE_BOOK_NOT_FOUND, bookNotFoundException.getMessage());
    }

    @Test
    public void givenBook_whenRetrieveWithValidReference_thenReturnCorrectBook() throws BookNotFoundException {
        Book result = bookService.retrieveBook(BOOK_GRUFF_472_REF);
        assertEquals(BOOK_GRUFF_472_REF, result.getReference());
        assertEquals(BOOK_GRUFF_472_TITLE, result.getTitle());
        assertEquals(BOOK_GRUFF_472_REVIEW, result.getReview());
    }

    @Test
    public void givenBook_whenGetBookSummaryWithNoPrefix_thenThrowException() {
        BookNotFoundException bookNotFoundException = assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookSummary(INVALID_TEXT);
        });
        assertEquals(EXCEPTION_MESSAGE_NO_PREFIX, bookNotFoundException.getMessage());
    }

    @Test
    public void givenBook_whenGetBookSummaryWithInvalidReference_thenThrowException() {
        BookNotFoundException bookNotFoundException = assertThrows(BookNotFoundException.class, () -> {
           bookService.getBookSummary(BOOK_999);
        });
        assertEquals(EXCEPTION_MESSAGE_BOOK_NOT_FOUND, bookNotFoundException.getMessage());
    }

    @Test
    public void givenBook_whenGetLongBookSummaryWithValidReference_thenReturnCorrectBookSummaryFormat() throws BookNotFoundException {
        String bookGruff472Result = bookService.getBookSummary(BOOK_GRUFF_472_REF);
        String bookPooh222Result = bookService.getBookSummary(BOOK_POOH222_REF);
        String bookWill987Result = bookService.getBookSummary(BOOK_WILL987_REF);
        assertEquals("[BOOK-GRUFF472] The Gruffalo - A mouse taking a walk in the woods.", bookGruff472Result);
        assertEquals("[BOOK-POOH222] Winnie The Pooh - In this first volume, we meet all the friends...", bookPooh222Result);
        assertEquals("[BOOK-WILL987] The Wind In The Willows - With the arrival of spring and fine weather outside...", bookWill987Result);
    }

}
