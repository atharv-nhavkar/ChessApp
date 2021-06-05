package com.example.chessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class APItrial extends AppCompatActivity {

    EditText fen;
    Button b;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apitrial);
        fen = (EditText)findViewById(R.id.editTextTextPersonName3);
        b = (Button)findViewById(R.id.button4);
        tv = (TextView)findViewById(R.id.textView10);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = fen.getText().toString();

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://lichess.org/api/cloud-eval";

                String formedurl = url + "?format=json&fen=" +ip + "&multiPv=1&variant=standard";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, formedurl, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(APItrial.this, " got the response "  , Toast.LENGTH_SHORT).show();
                                try {
                                    String opt = response.getString("knodes");
                                    tv.setText(opt);
                                    String op = response.getString("pvs");
                                    tv.setText(opt +"  " + op);

                                } catch (JSONException e) {
                                    Toast.makeText(APItrial.this, " wrong " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                                //textView.setText("Response: " + response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                tv.setText("aagaya swad");
                                // TODO: Handle error

                            }
                        });


                // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);
            }
        });
    }

}
