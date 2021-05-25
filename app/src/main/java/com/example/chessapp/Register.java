package com.example.chessapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private EditText username , password1,password2,name;
    Button signup;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth= FirebaseAuth.getInstance();
        username = findViewById(R.id.newemail);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        signup= findViewById(R.id.button);
        name = findViewById(R.id.newusername);
        database = FirebaseDatabase.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String passwordstring1 = password1.getText().toString();
                String passwordstring2 = password2.getText().toString();
                String newusername = name.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
                }
                else if(isEmailValid(email)){
                    if(passwordstring1.isEmpty() || passwordstring2.isEmpty()){
                        Toast.makeText(getApplicationContext(),"enter password please ",Toast.LENGTH_SHORT).show();
                    }

                    else {
                        if(passwordstring1.equals(passwordstring2))
                        {
                            if(newusername.isEmpty())
                                Toast.makeText(getApplicationContext(),"enter username please ",Toast.LENGTH_SHORT).show();
                            else{
                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference userNameRef = rootRef.child("Users").child(newusername);
                                ValueEventListener eventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(!dataSnapshot.exists()) {
                                            createAccount(email,passwordstring1,newusername);
                                            //create new user
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),  "Be creative find new username ", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                        Toast.makeText(getApplicationContext(),   databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                        //Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
                                    }
                                };
                                userNameRef.addListenerForSingleValueEvent(eventListener);

                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Password does n0t match ",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"enter valid email address",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createAccount(String email, String password,String newusername) {
        //[START create_user_with_email]

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userID= mAuth.getCurrentUser().getUid();
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                            User newuser = new User(newusername,"NOGAMENOW",true,true);
                            myRef.child("users").child(newusername).setValue(newuser);
                            myRef.child("config").child(userID).setValue(newusername);

                            Toast.makeText(getApplicationContext(), " Registred Succesfully ", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // [END create_user_with_email]
    }


    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
