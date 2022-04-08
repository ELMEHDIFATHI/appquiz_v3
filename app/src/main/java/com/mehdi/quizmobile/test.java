package com.mehdi.quizmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class test extends AppCompatActivity {
    TextView receiver_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        receiver_msg = (TextView)findViewById(R.id.scoor);

        // create the get Intent object
        Intent intent = getIntent();

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        int str = intent.getIntExtra("message_key",0);

        // display the string into textView
        receiver_msg.setText(str +"  IS YOUR SCOOR");

    }
}