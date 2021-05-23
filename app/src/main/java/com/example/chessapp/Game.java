package com.example.chessapp;

public class Game {
    String FEN ;
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

    public Game(String FEN, int turn) {
        this.FEN = FEN;
        this.turn = turn;
    }

    public Game() {
            // aise hi sexy lag raha tha
    }


}
