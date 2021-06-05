package com.example.chessapp;

public class Game {
    String FEN ;
    String challeger;
    String Opponant;

    int ChalengerTimer;
    int OpponantTimer;
    int offer;
    // 0--> dafult
    // 1--> white resigns
    // 2 --> black resigns
    // 3--> white offers draw
    // 4--> black offers draw
    boolean drawn;
    boolean free;


    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public String getChalleger() {
        return challeger;
    }

    public void setChalleger(String challeger) {
        this.challeger = challeger;
    }

    public String getOpponant() {
        return Opponant;
    }

    public void setOpponant(String opponant) {
        Opponant = opponant;
    }

    public Integer getChalengerTimer() {
        return ChalengerTimer;
    }

    public void setChalengerTimer(int chalengerTimer) {
        ChalengerTimer = chalengerTimer;
    }

    public Integer getOpponantTimer() {
        return OpponantTimer;
    }

    public void setOpponantTimer(int opponantTimer) {
        OpponantTimer = opponantTimer;
    }

    int turn; // 1 for white // 0 for black

    public String getFEN() {
        return FEN;
    }

    public void setFEN(String FEN) {
        this.FEN = FEN;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Game(String FEN, String challeger, String opponant, int chalengerTimer, int opponantTimer, int offer, boolean drawn, boolean free, int turn) {
        this.FEN = FEN;
        this.challeger = challeger;
        Opponant = opponant;
        ChalengerTimer = chalengerTimer;
        OpponantTimer = opponantTimer;
        this.offer = offer;
        this.drawn = drawn;
        this.free = free;
        this.turn = turn;
    }



    public Game() {
            // aise hi sexy lag raha tha
    }


}
