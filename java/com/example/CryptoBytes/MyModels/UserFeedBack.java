package com.example.CryptoBytes.MyModels;

public class UserFeedBack {
    private String name=null,email=null,suggestion=null,rating=null;

    public UserFeedBack(String name, String email, String suggestion, String rating) {
        this.name = name;
        this.email = email;
        this.suggestion = suggestion;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
