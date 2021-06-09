package com.example.api_crud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKy extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText edPaas, edPass2, edEmail;
    private Button btnRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        auth = FirebaseAuth.getInstance();

        edEmail = findViewById(R.id.edtEmail_regis);
        edPaas = findViewById(R.id.edPass_regis);
        edPass2 = findViewById(R.id.edPass2_regis);
        btnRegis = findViewById(R.id.btnRegis_regis);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                String pass = edPaas.getText().toString();
                String pass2 = edPass2.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass2)){
                    Toast.makeText(getApplicationContext(), "Confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pass.equalsIgnoreCase(pass2)==false){
                    Toast.makeText(getApplicationContext(), "Password is not matched", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(DangKy.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(DangKy.this, "Registration failed: "+task.getException(), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(DangKy.this, "Registration success: ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DangKy.this, com.example.api_crud.MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });


    }
}