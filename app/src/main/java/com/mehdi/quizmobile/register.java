package com.mehdi.quizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    Button register;
    EditText e1,e2,e3,e4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register=(Button) findViewById(R.id.register);
        e1=(EditText) findViewById(R.id.nom);
        e2=(EditText) findViewById(R.id.email);
        e3=(EditText) findViewById(R.id.pass1);
        e4=(EditText) findViewById(R.id.pass2);
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String nom=e1.getText().toString().trim();
        String email=e2.getText().toString().trim();
        String pass1=e3.getText().toString().trim();
        String pass2=e4.getText().toString().trim();

        if (nom.isEmpty()){
            e1.setError("entre le nom");
            e1.requestFocus();
            return;
        }
        if (email.isEmpty()){
            e2.setError("entre email");
            e2.requestFocus();
            return;
        }
        if (pass1.isEmpty()){
            e3.setError("entre pass1");
            e3.requestFocus();
            return;
        }
        if (pass2.isEmpty()){
            e4.setError("entre pass2");
            e4.requestFocus();
            return;
        }
        if(pass1.length()<6 ){
            e4.setError("vous devez entre un password plus de 6 caractere");
            e4.requestFocus();
            return;
        }


        if(!pass1.equals(pass2)){
            e4.setError("password incoreect");
            e4.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pass1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(nom,email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register.this,"the user insted succesful",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(register.this,"falaid to register",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(register.this,"falaid to register 1",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

