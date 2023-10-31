package com.my.library.model;

public class Book {

    private String reference;
    private String title;
    private String review;

    public Book(String reference, String title, String description) {
        this.reference = reference;
        this.title = title;
        this.review = description;
    }

    public String getReview() {
        return review;
    }

    public String getReference() {
        return reference;
    }

    public String getTitle() {
        return title;
    }

}

