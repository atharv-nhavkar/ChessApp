package com.example.chessapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    ArrayList<Challenge> data;
    Context context;

    public CustomAdapter(@NonNull Context context, ArrayList<Challenge>data) {
        super(context, R.layout.challengerrow,data);
        this.data=data;
        this.context=context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.challengerrow,parent,false);
        TextView oneline = view.findViewById(R.id.textView6);
        Button acceptb = view.findViewById(R.id.acceptbutton);
        Button rejectb = view.findViewById(R.id.rejectbutton);

        Challenge c = data.get(position);
        String name = c.getChallenger();
        String key = c.getLink();
        String time = c.getTimeControl();


        oneline.setText(name + "challenged for " + time);
        acceptb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),GameScreen.class);
                intent.putExtra("activeplayer", 0);
                intent.putExtra("link", key);
            }
        });

        return view;
    }


}
