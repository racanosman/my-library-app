package com.my.library.repository;

import com.my.library.model.Book;

public interface BookRepository {

    Book retrieveBook(String reference);

}

