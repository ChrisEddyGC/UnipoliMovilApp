package com.example.unipolimovilapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    Button btn_sendEmail, btn_return;
    EditText email;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        btn_sendEmail = findViewById(R.id.btn_SendResetEmail);
        btn_return = findViewById(R.id.btn_returnToLogin);
        email = findViewById(R.id.resetPassword_UserEmail);

        mAuth = FirebaseAuth.getInstance();

        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailRequester = email.getText().toString().trim();
                //enviar correo de solicitud de reasignacion de contraseña al usuario en sesion activa
                mAuth.sendPasswordResetEmail(emailRequester).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ResetPasswordActivity.this, "la solicitud ha sido enviada a su correo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}