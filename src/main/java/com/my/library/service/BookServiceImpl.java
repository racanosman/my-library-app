package com.my.library.service;

import com.my.library.exception.BookNotFoundException;
import com.my.library.model.Book;
import com.my.library.repository.BookRepository;

import java.util.regex.Pattern;

import static com.my.library.util.Constants.*;

public class BookServiceImpl implements BookService {

    private static final int WORD_LIMIT = 9;
    private static final String REGEX_PUNCTUATION = "\\p{Punct}";
    private static final String REGEX_WORD = "\\s+";
    private final BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book retrieveBook(String bookReference) throws BookNotFoundException {
        validateBookReference(bookReference);
        Book book = bookRepository.retrieveBook(bookReference);
        if(book == null) {
            throw new BookNotFoundException(EXCEPTION_MESSAGE_BOOK_NOT_FOUND);
        }
        return book;
    }

    @Override
    public String getBookSummary(String bookReference) throws BookNotFoundException {
        validateBookReference(bookReference);
        Book book = bookRepository.retrieveBook(bookReference);
        if(book == null) {
            throw new BookNotFoundException(EXCEPTION_MESSAGE_BOOK_NOT_FOUND);
        }
        return buildBookSummary(book);
    }

    private void validateBookReference(String bookReference) throws BookNotFoundException {
        if (!bookReference.startsWith(BOOK_PREFIX)) {
            throw new BookNotFoundException(EXCEPTION_MESSAGE_NO_PREFIX);
        }
    }

    private String buildBookSummary(Book book) {
        String[] words = book.getReview().split(REGEX_WORD);
        if(words.length > WORD_LIMIT) {
            StringBuilder summary = new StringBuilder();
            for(int i = 0; i < WORD_LIMIT; i++ ) {
                // if it's the last word, don't append a blank space.
                if(i == WORD_LIMIT - 1) {
                    summary.append(words[i]);
                    break;
                }
                summary.append(words[i]).append(BLANK_SPACE);
            }
            String lastWord = words[WORD_LIMIT - 1];
            if(Pattern.matches(REGEX_PUNCTUATION, lastWord.substring(lastWord.length() - 1))) {
                summary.deleteCharAt(summary.length() - 1);
            }
            summary.append(ELLIPSIS);
            return String.format("[%s] %s - %s", book.getReference(), book.getTitle(), summary.toString());
        }
        return String.format("[%s] %s - %s", book.getReference(), book.getTitle(), book.getReview());
    }

}