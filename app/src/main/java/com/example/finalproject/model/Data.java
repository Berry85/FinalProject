package com.example.finalproject.model;

import java.io.Serializable;

public class Data implements Serializable {
    //姓名
    private String name;
    //    电话
    private String phone;
    private boolean favorite;
    private String email;
    private String note;


    public Data(String name, String phone, boolean favorite, String email, String note) {
        this.name = name;
        this.phone = phone;
        this.favorite = favorite;
        this.email = email;
        this.note = note;
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
}
