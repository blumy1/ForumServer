package com.sb.blumek.models;

public class Subcategory {
    private int id;
    private String name;
    private String slug;
    private Category category;
    private int threadsAmount;
    private int postsAmount;

    public Subcategory() {
    }

    public Subcategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subcategory(int id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Subcategory(int id, String name, Category category, int threadsAmount, int postsAmount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.threadsAmount = threadsAmount;
        this.postsAmount = postsAmount;
    }

    public Subcategory(int id, String name, String slug, Category category, int threadsAmount, int postsAmount) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.category = category;
        this.threadsAmount = threadsAmount;
        this.postsAmount = postsAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getThreadsAmount() {
        return threadsAmount;
    }

    public void setThreadsAmount(int threadAmount) {
        this.threadsAmount = threadAmount;
    }

    public int getPostsAmount() {
        return postsAmount;
    }

    public void setPostsAmount(int postsAmount) {
        this.postsAmount = postsAmount;
    }
}
