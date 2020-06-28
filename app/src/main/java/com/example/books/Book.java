package com.example.books;

public class Book {
    public String id;
    public String title;
    public String subTitle;
    public String []author;
    public String publisher;
    public String publishDate;

    public Book(String id, String title, String subTitle, String[] author, String publisher, String publishDate) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.author = author;
        this.publisher = publisher;
        this.publishDate = publishDate;
    }
}
