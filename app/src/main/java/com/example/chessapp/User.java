package com.example.chessapp;

public class User {
    String user;
    String fen ;
    Boolean online ;
    Boolean free;




    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }


    public User(){
    }


    public User(String user, String fen, Boolean online, Boolean free) {
        this.user = user;
        this.fen = fen;

        this.online = online;
        this.free = free;
    }
}

