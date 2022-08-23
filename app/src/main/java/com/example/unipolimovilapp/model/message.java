package com.example.unipolimovilapp.model;


import com.google.firebase.Timestamp;

public class message {
    //las variables en esta seccion deben tener el mismo nombre que el campo dentro de la coleccion Messages
    Timestamp createdAt;
    String message,title,user;


    public message(){

    }

    public message(Timestamp createdAt, String title, String message, String user) {
        this.createdAt = createdAt;
        this.title = title;
        this.message = message;
        this.user = user;
    }
    public Timestamp getCreatedAt() { return  createdAt;}

    public String getTitle() {
        return title;
    }


    public String getMessage() {
        return message;
    }


    public String getUser() {
        return user;
    }

}
