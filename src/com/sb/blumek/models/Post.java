package com.sb.blumek.models;

public class Post {
    private int id;
    private User author;
    private Thread thread;
    private String text;
    private String createdAt;

    public Post() {
    }

    public Post(int id, User author, Thread thread, String text, String createdAt) {
        this.id = id;
        this.author = author;
        this.thread = thread;
        this.text = text;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
