package com.example.chessapp;

public class Challenge {
    String key;
    String link;
    String Challenger;
    String OpponentName;
    String TimeControl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getChallenger() {
        return Challenger;
    }

    public void setChallenger(String challenger) {
        Challenger = challenger;
    }

    public String getOpponentName() {
        return OpponentName;
    }

    public void setOpponentName(String opponentName) {
        OpponentName = opponentName;
    }

    public String getTimeControl() {
        return TimeControl;
    }

    public void setTimeControl(String timeControl) {
        TimeControl = timeControl;
    }

    public Challenge() {

    }

    public Challenge(String key, String link, String challenger, String opponentName, String timeControl) {
        this.key = key;
        this.link = link;
        Challenger = challenger;
        OpponentName = opponentName;
        TimeControl = timeControl;
    }


}
