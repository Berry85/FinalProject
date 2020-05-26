package com.example.finalproject.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Data implements Serializable {
    //姓名
    private String name;
    //    电话
    private String phone;
    private boolean favorite;
    private String email;
    private String note;
    private String imagePath;


    public Data(String name, String phone, boolean favorite, String email, String note, String imagePath) {
        this.name = name;
        this.phone = phone;
        this.favorite = favorite;
        this.email = email;
        this.note = note;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name:" + this.getName() + "/n" +
                "phone:" + this.getPhone() + "/n" +
                "note:" + this.getNote() + "/n" +
                "email:" + this.getEmail() + "/n" +
                "imagePath:" + this.getImagePath() + "/n";
    }
}
