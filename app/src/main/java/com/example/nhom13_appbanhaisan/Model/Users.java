package com.example.nhom13_appbanhaisan.Model;

import com.google.firebase.database.Exclude;

public class Users {
    @Exclude
    private String id;
    private String image;
    private String fullName;
    private String gender;
    private String date;
    private String phone;

    public Users() {

    }

    public Users(String image, String fullName, String gender, String date, String phone) {
        this.image = image;
        this.fullName = fullName;
        this.gender = gender;
        this.date = date;
        this.phone = phone;
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
