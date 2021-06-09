package com.example.api_crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView tvEmail;
    private Button btnLogOut, btnCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gd);

        tvEmail = findViewById(R.id.tvEmail);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnCon = findViewById(R.id.btnCon);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user != null){
            String email = user.getEmail();
            tvEmail.setText(email);
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.api_crud.Welcome.this, com.example.api_crud.MainActivity.class);
                startActivity(intent);
            }
        });

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.api_crud.Welcome.this, List.class);
                startActivity(intent);
            }
        });



    }
}