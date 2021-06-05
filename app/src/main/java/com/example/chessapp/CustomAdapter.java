package com.example.chessapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Challenge> {
    String name ;


    public CustomAdapter(@NonNull Context context, ArrayList<Challenge>data,String username) {
        super(context, 0, data);
        name = username;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Challenge challenge = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.challengerrow, parent, false);
        }
        TextView oneline0 = convertView.findViewById(R.id.textView6);
        oneline0.setText(challenge.getChallenger()+" challenge for" + challenge.getTimeControl());
        Button acceptb0 = convertView.findViewById(R.id.acceptbutton);
        acceptb0.setTag(position);
        acceptb0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Challenge challenge1 = getItem(position);
                Toast.makeText(getContext(), challenge1.getLink() +" mast bhai", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference().child("users").child(name).child("challengers").child(challenge1.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(), "challenge being removed ", Toast.LENGTH_SHORT).show();
                    }
                });
                String gamelink0 = challenge1.getLink();
                Intent intent = new Intent(parent.getContext(),gametryscreen.class);
                intent.putExtra("activeplayer",0);
                intent.putExtra("myname", name);
                intent.putExtra("link",gamelink0);
                Toast.makeText(parent.getContext(), "Challenge zala baba tayar ", Toast.LENGTH_SHORT).show();
                parent.getContext().startActivity(intent);
            }
        });
        Button rejectb0 = convertView.findViewById(R.id.rejectbutton);
        rejectb0.setTag(position);
        rejectb0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                Challenge challenge1 = getItem(position);
                Toast.makeText(getContext(), challenge1.getLink() +" mast bhai", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference().child("users").child(name).child("challengers").child(challenge1.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(), "challenge being removed ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





//        View view= LayoutInflater.from(context).inflate(R.layout.challengerrow,parent,false);
//        TextView oneline = view.findViewById(R.id.textView6);
//        Button acceptb = view.findViewById(R.id.acceptbutton);
//        Button rejectb = view.findViewById(R.id.rejectbutton);
//
//        Challenge c = data.get(position);
//        String name = c.getChallenger();
//        String key = c.getLink();
//        String time = c.getTimeControl();
//
//
//        oneline.setText(name + "challenged for " + time);
//        acceptb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(getContext(),GameScreen.class);
//                intent.putExtra("activeplayer", 0);
//                intent.putExtra("link", key);
//            }
//        });

        return convertView;
    }


}
