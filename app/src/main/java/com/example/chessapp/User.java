package com.example.chessapp;

public class User {
    String user;
    String fen ;
    Boolean online ;
    Boolean free;
    String email;
    String password;
    int wins;
    int draws ;
    int loss;

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

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

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

    public User(String user, String fen, Boolean online, Boolean free, String email, String password, int wins, int draws, int loss) {
        this.user = user;
        this.fen = fen;
        this.online = online;
        this.free = free;
        this.email = email;
        this.password = password;
        this.wins = wins;
        this.draws = draws;
        this.loss = loss;
    }


}

