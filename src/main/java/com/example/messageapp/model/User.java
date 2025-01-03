package com.example.messageapp.model;

public class User {
    private String fullName;
    private String email;
    private String password;
    private String dob; // Ngày sinh
    private String gender; // Giới tính

    public User(String fullName, String email, String password, String dob, String gender) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.gender = gender;
    }

    // Getter và Setter
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
