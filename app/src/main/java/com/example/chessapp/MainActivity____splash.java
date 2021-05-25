package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity____splash extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    //Variables
    Animation topAnim,bottomAnim;
    ImageView iamge;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //Hooks
        iamge =findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);

        iamge.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable (){
            @Override
            public void run() {
                //Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                //startActivity(intent);
                finish();
            }
            },SPLASH_SCREEN);
    }
}