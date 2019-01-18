package com.sb.blumek.models;

public class Thread {
    private int id;
    private String title;
    private String slug;
    private User author;
    private Subcategory subcategory;
    private String createdAt;
    private int status;

    public Thread() {
    }

    public Thread(int id, String title, String createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }

    public Thread(int id, String title, Subcategory subcategory, String createdAt, int status) {
        this.id = id;
        this.title = title;
        this.subcategory = subcategory;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Thread(int id, String title, String slug, User author, Subcategory subcategory, String createdAt, int status) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.author = author;
        this.subcategory = subcategory;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Thread(int id, String title, User author, Subcategory subcategory, String createdAt, int status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.subcategory = subcategory;
        this.createdAt = createdAt;
        this.status = status;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
