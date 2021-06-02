package com.example.chessapp;

public class Game {
    String FEN ;
    String challeger;
    String Opponant;
    int ChalengerTimer;
    int OpponantTimer;
    boolean drawn;
    boolean free;

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

    public Game(String FEN, String challeger, String opponant, int chalengerTimer, int opponantTimer, boolean drawn, boolean free, int turn) {
        this.FEN = FEN;
        this.challeger = challeger;
        Opponant = opponant;
        ChalengerTimer = chalengerTimer;
        OpponantTimer = opponantTimer;
        this.drawn = drawn;
        this.free = free;
        this.turn = turn;
    }


    public Game() {
            // aise hi sexy lag raha tha
    }


}
