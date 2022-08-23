package com.example.unipolimovilapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class verificationLobbyActivity extends AppCompatActivity {
    //aplicacion que se muestra si el usuario no ha verificado su cuenta por medio del correo
    Button btnSendVerification, btnLogOut;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_lobby);

        btnSendVerification = findViewById(R.id.btn_sendemailVerification);
        btnLogOut = findViewById(R.id.btn_vLobby_logOut);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btnSendVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(verificationLobbyActivity.this, "la solicitud ha sido enviada a su correo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(verificationLobbyActivity.this, LoginActivity.class));
            }
        });
    }
}