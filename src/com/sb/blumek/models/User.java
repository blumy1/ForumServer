package com.sb.blumek.models;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Rank rank;
    private String createdAt;
    private String imageUrl;

    public User() {
    }

    public User(int id, String username, String createdAt, String imageUrl) {
        this.id = id;
        this.username = username;
        this.rank = rank;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }

    public User(int id, String username, Rank rank, String createdAt, String imageUrl) {
        this.id = id;
        this.username = username;
        this.rank = rank;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }

    public User(int id, String username, String password, String email, Rank rank, String createdAt, String imageUrl) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.rank = rank;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
