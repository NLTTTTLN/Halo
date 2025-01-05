package com.example.messageapp.features.contacts.model;

public class Contact {
    private String fullName;
    private String email;
    private String avatarUrl; // Optional: URL hoặc ID hình ảnh avatar

    // Constructor
    public Contact(String fullName, String email, String avatarUrl) {
        this.fullName = fullName;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}