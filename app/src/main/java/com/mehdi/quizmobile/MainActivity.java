package com.mehdi.quizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register,map;
    Button blogin,brecord;
    EditText e1,e2;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        e1=(EditText) findViewById(R.id.etemail);
        e2=(EditText) findViewById(R.id.etpassword);
        brecord =(Button) findViewById(R.id.record);

        register =(TextView) findViewById(R.id.etregister) ;
        map =(TextView) findViewById(R.id.etmap);


        blogin=(Button) findViewById(R.id.blogin);
        blogin.setOnClickListener((View.OnClickListener) this);

        mAuth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,register.class));
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,maps.class));
            }
        });

        brecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,record.class));
            }
        });

    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.blogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String emailuser=e1.getText().toString().trim();
        String  passuser=e2.getText().toString().trim();
        if(emailuser.isEmpty()){
            e1.setError("entre le emailuser");
            e1.requestFocus();
            return;
        }

        if(passuser.isEmpty()){
            e2.setError("entre le password");
            e2.requestFocus();
            return;
        }

        if(passuser.length()<6){
            e2.setError("entre le password 6 caractere minimum");
            e2.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailuser,passuser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to quiz
                    startActivity(new Intent(MainActivity.this,quiz1.class));
                }else{
                    Toast.makeText(MainActivity.this,"falaid to Login Username or password Incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}



        /*

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString().equals("toto")){
                    startActivity(new Intent(MainActivity.this,quiz1.class));
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,register.class));
            }
        });
        */

