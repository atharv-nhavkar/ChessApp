package com.example.chessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    TextView challengelink1;
    Button challengelink2;
    TextView currentusername;
    Button challenge,accept,trial;

    ListView challengesview ;

   String value1="0";
   String value2 ="0";

   ArrayList<Challenge> allchallenges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenger);
        String name = getIntent().getExtras().getString("username");

        allchallenges = new ArrayList<Challenge>(7);
        CustomAdapter adapter = new CustomAdapter(this,allchallenges,name);
        challengesview = findViewById(R.id.challengesview);
        challengesview.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

        //trial = (Button)findViewById(R.id.trial);
        challengesview = findViewById(R.id.challengesview);
        currentusername = (TextView)findViewById(R.id.currentusername);
        currentusername.setText(name);
        opponantname = (EditText) findViewById(R.id.editTextTextPersonName2);
        challengelink1 = (TextView) findViewById(R.id.textView7);
        challengelink2 = (Button) findViewById(R.id.button3);
        gamelink = (EditText)findViewById(R.id.link);
        challenge = (Button)findViewById(R.id.challenge);
        accept = (Button)findViewById(R.id.accept);
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("users").child(name).child("challengers");
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


//        trial.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Challenger.this,APItrial.class);
//                startActivity(intent);
//            }
//        });

        challengelink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = challengelink1.getText().toString();
                if(key != "Share") {

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, key);
                    sendIntent.setType("text/plain");

                    try {
                        startActivity(sendIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(Challenger.this, "Cant call explicit intent "+ e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        // Define what your app should do if no activity can handle the intent.
                    }

                    // external Intent calling
                }
            }
        });

        challengelink2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // INtent sathi;

                String key = challengelink1.getText().toString();
                if(key != "Share"){
                    Intent intent = new Intent(getApplicationContext(), gametryscreen.class);
                    intent.putExtra("activeplayer", 1);
                    intent.putExtra("link", key);
                    intent.putExtra("myname", name);
                    startActivity(intent);

                }

            }
        });





        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opponantName = opponantname.getText().toString();
                if(opponantName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), " Write a Name please  ", Toast.LENGTH_SHORT).show();
                    Game game = new Game("NOTYET",name,"mahitnai",0,0,0,false,false,1);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("games");
                    String key = ref.push().getKey();
                    ref.child(key).setValue(game);
                    challengelink1.setText(key);
//                    Intent intent = new Intent(getApplicationContext(), GameScreen.class);
//                    intent.putExtra("activeplayer", 1);
//                    intent.putExtra("link", key);
//                    intent.putExtra("myname", name);
//                    startActivity(intent);



                }
                else {
                    // check if ebtred playere exixts or not here

                    // code to be writtened
                    Game game = new Game("NOTYET",name,"mahitnai",0,0,0,false,false,1);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("games");
                    String key = ref.push().getKey();
                    ref.child(key).setValue(game);


                    DatabaseReference opponentref = FirebaseDatabase.getInstance().getReference().child("users").child(opponantName).child("challengers");
                    String refkey = opponentref.push().getKey();
                    Challenge challenge = new Challenge(refkey,key,name,opponantName,"Classic");
                    opponentref.child(refkey).setValue(challenge);
                    Intent intent = new Intent(getApplicationContext(), gametryscreen.class);
                    intent.putExtra("activeplayer", 1);
                    intent.putExtra("link", key);
                    intent.putExtra("myname", name);
                    startActivity(intent);
                }
            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gamelink0 = gamelink.getText().toString();
                Intent intent = new Intent(getApplicationContext(),gametryscreen.class);
                intent.putExtra("activeplayer",0);
                intent.putExtra("myname", name);
                intent.putExtra("link",gamelink0);
                Toast.makeText(getApplicationContext(), "Challenge zala baba tayar ", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

//        ChildEventListener childEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                  //Challenge newchallenge = dataSnapshot.getValue(Challenge.class);
//                  //allchallenges.add(newchallenge);
//
////                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
////
////                // A new comment has been added, add it to the displayed list
////                Comment comment = dataSnapshot.getValue(Comment.class);
//
//                // ...
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//
//                Toast.makeText(getApplicationContext(), "oooooooo my gooooddd (with janice smile )", Toast.LENGTH_SHORT).show();
//
//
//
//
////                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
////
////                // A comment has changed, use the key to determine if we are displaying this
////                // comment and if so displayed the changed comment.
////                Comment newComment = dataSnapshot.getValue(Comment.class);
////                String commentKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    Challenge removedchallenge = dataSnapshot.getValue(Challenge.class);
//                    allchallenges.remove(removedchallenge);
//
////                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                //String commentKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//
//
//                Toast.makeText(getApplicationContext(), "oooooooo my gooooddd (with janice smile )", Toast.LENGTH_SHORT).show();
//
////                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
////
////                // A comment has changed position, use the key to determine if we are
////                // displaying this comment and if so move it.
////                Comment movedComment = dataSnapshot.getValue(Comment.class);
////                String commentKey = dataSnapshot.getKey();
////
////                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                //Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//                Toast.makeText(getApplicationContext(), "Failed to load comments.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        };
//        userref.addChildEventListener(childEventListener);


        // LATEEST COMMENTED CODE


        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot){
                allchallenges.clear();

                for (DataSnapshot it : snapshot.getChildren()) {


                    //String s = it.getValue(String.class);

                    //currentusername.setText(s);

                    Challenge c = it.getValue(Challenge.class);

                    allchallenges.add(c);
                    Toast.makeText(Challenger.this, c.getChallenger(), Toast.LENGTH_SHORT).show();
                }

                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),allchallenges,name);
                challengesview = findViewById(R.id.challengesview);
                challengesview.setAdapter(adapter);


            }

            @Override
            public void onCancelled (@NonNull DatabaseError error){
                Toast.makeText(Challenger.this, " Database Error", Toast.LENGTH_SHORT).show();
                Log.d("Database Error",error.getMessage().toString());
            }

        });



        // LATEEST COMMENTED CODE ENDS


//        userref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot it:snapshot.getChildren()){
//                    if(value1=="0")
//                        value1= it.getValue().toString();
//                    else
//                        value2 = it.getValue().toString();
//                }
//                challengelink1.setText(value1);
//                challengelink2.setText(value2);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
}