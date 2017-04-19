package com.example.alex.scangoals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Squat extends AppCompatActivity {

    private Button submitBtn;
    private Button backBtn;
    private EditText userInputTxt;
    public String username = LoginActivity.stuffToGrab.getString("username");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treadmill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Squat.this, mainMenu.class));
            }
        });
        userInputTxt = (EditText) findViewById(R.id.userInputTxt);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userInput = userInputTxt.getText().toString();

                //create response listener
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                Intent intent = new Intent(Squat.this, Journal.class);
                                Squat.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder log = new AlertDialog.Builder(Squat.this);
                                log.setMessage("Failed To Log To Journal.").setNegativeButton("Retry", null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //create a request
                LogToJournalRequest logToJournalRequest = new LogToJournalRequest(userInput, username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Squat.this);
                queue.add(logToJournalRequest);
            }
        });

    }

}