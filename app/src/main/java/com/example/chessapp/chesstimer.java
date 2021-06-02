package com.example.chessapp;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class chesstimer {
    CountDownTimer ct;
    TextView tv;
    int remtime;
    public  void settimer(int millisecs){
        if(ct!=null){
            ct.cancel();
        }
        remtime = millisecs;
        ct = new CountDownTimer(millisecs,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                tv.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                remtime--;
            }

            @Override
            public void onFinish() {
                tv.setText(00 + ":" + 00 + ":" + 00 );
            }
        };
    }

    public void canceltime(){
        ct.cancel();
        remtime=-1;
    }

    public CountDownTimer getCt() {
        return ct;
    }

    public void setCt(CountDownTimer ct) {
        this.ct = ct;
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public int getRemtime() {
        return remtime;
    }

    public void setRemtime(int remtime) {
        this.remtime = remtime;
    }

    public chesstimer(CountDownTimer ct, TextView tv, int remtime) {
        this.ct = ct;
        this.tv = tv;
        this.remtime = remtime;
    }
}
