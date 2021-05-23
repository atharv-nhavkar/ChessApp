package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class After_login extends AppCompatActivity {

    TextView tv ;
    EditText et;
    Button b,black,white;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login2);
        tv = (TextView)findViewById(R.id.textView5);
        et= (EditText) findViewById(R.id.editTextTextPersonName);
        database = FirebaseDatabase.getInstance().getReference().child("Note");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String post = dataSnapshot.getValue(String.class);
                Toast.makeText(getApplicationContext(), "text is " + post, Toast.LENGTH_SHORT).show();
                tv.setText(post);

                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        database.addValueEventListener(postListener);
        b= (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st= et.getText().toString();
                database.setValue(st);
            }
        });

        white = (Button) findViewById(R.id.white_);
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GameScreen.class);
                intent.putExtra("activeplayer",1);
                startActivity(intent);
            }
        });
        black = (Button) findViewById(R.id.black);
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GameScreen.class);
                intent.putExtra("activeplayer",0);
                startActivity(intent);

            }
        });
    }
}