package com.example.chessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Challenger extends AppCompatActivity {

    EditText opponantname ,gamelink;
    TextView challengelink1,challengelink2;
    TextView currentusername;
    Button challenge,accept;

   String value1="0";
   String value2 ="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenger);
        String name = getIntent().getExtras().getString("username");

        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();


        currentusername = (TextView)findViewById(R.id.currentusername);
        opponantname = (EditText) findViewById(R.id.editTextTextPersonName2);
        challengelink1 = (TextView) findViewById(R.id.textView7);
        challengelink2 = (TextView) findViewById(R.id.textView8);
        gamelink = (EditText)findViewById(R.id.link);
        challenge = (Button)findViewById(R.id.challenge);
        accept = (Button)findViewById(R.id.accept);
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("users").child(name);
//        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

//        final String[] username = new String[1];
//        ValueEventListener forusername = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                username[0] = snapshot.getValue(String.class);
//                Toast.makeText(getApplicationContext(), "text is " + username[0], Toast.LENGTH_SHORT).show();
//                currentusername.setText(username[0]);
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "Shetti madam zidabad ", Toast.LENGTH_SHORT).show();
//
//            }
//        };
//        FirebaseDatabase.getInstance().getReference().child("config").child(userID).addValueEventListener(forusername);
//
//        String s = currentusername.getText().toString();
//        userref = FirebaseDatabase.getInstance().getReference().child("users").child(s).child("challengers");





        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game  game = new Game("NOTYET",1);
                String opponantName = opponantname.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("games");
                String key = ref.push().getKey();
                ref.child(key).setValue(game);


                DatabaseReference opponentref = FirebaseDatabase.getInstance().getReference().child("users").child(opponantName).child("challengers");
                opponentref.push().setValue(key);

                Intent intent = new Intent(getApplicationContext(),GameScreen.class);
                intent.putExtra("activeplayer",1);
                intent.putExtra("link",key);
                //startActivity(intent);

            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gamelink0 = gamelink.getText().toString();
                Intent intent = new Intent(getApplicationContext(),GameScreen.class);
                intent.putExtra("activeplayer",0);
                intent.putExtra("link",gamelink0);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Challenge zala baba tayar ", Toast.LENGTH_SHORT).show();
            }
        });


        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot it:snapshot.getChildren()){
                    if(value1=="0")
                        value1= it.getValue().toString();
                    else
                        value2 = it.getValue().toString();
                }
                challengelink1.setText(value1);
                challengelink2.setText(value2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}