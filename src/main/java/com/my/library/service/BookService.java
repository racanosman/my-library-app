package com.my.library.service;

import com.my.library.model.Book;
import com.my.library.exception.BookNotFoundException;

public interface BookService {

    Book retrieveBook(String bookReference) throws BookNotFoundException;
    String getBookSummary(String bookReference) throws BookNotFoundException;

}