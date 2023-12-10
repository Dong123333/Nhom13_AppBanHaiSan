package com.example.nhom13_appbanhaisan.Model;

public class Comment {
    private String image;
    private String name;
    private String description;
    private String date;

    public Comment() {
    }

    public Comment(String image, String name, String description, String date) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
