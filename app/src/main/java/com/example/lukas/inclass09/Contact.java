package com.example.lukas.inclass09;

import android.graphics.drawable.Drawable;

//Lukas Newman

public class Contact {
    String name;
    String email;
    String phone;
    String dept;
    int avatarID;

    public Contact(String name, String email, String phone, String dept, int avatarID) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dept = dept;
        this.avatarID = avatarID;
    }

    public Contact() {
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dept='" + dept + '\'' +
                ", avatarID='" + avatarID + '\'' +
                '}';
    }
}
