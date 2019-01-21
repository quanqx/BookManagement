package com.example.quand.bookmanagement.Entities;

import java.io.Serializable;

public class Book implements Serializable {

    private int id;
    private String title;
    private String author;
    private String publisher;
    private String category;
    private byte[] image ;
    private String description;

    public Book(){}

    public Book(int id, String title, String author, String publisher, String category, byte[] image, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.image = image;
        this.description = description;
    }

    public Book(String title, String author, String publisher, String category, byte[] image, String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.image = image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilter () {
        return this.title + " " + this.author + " " + this.category + this.publisher;
    }
}
