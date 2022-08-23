package com.example.unipolimovilapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btn_register;
    Switch btn_bis;
    EditText name, email, registration, password, passwordConfirm;
    Spinner spinnerCareer, spinnerGrade, spinnerGroup;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_register = findViewById(R.id.btn_CreateUser);
        name = findViewById(R.id.UserName);
        email = findViewById(R.id.UserEmail);
        registration = findViewById(R.id.UserRegistration);
        password = findViewById(R.id.UserPassword);
        passwordConfirm = findViewById(R.id.UserPasswordConfirm);
        btn_bis = findViewById(R.id.switch_BIS);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //spiner para seleccionar la carrera
        spinnerCareer = findViewById(R.id.spinner_career);
        ArrayAdapter<CharSequence> adapterCareer = ArrayAdapter.createFromResource(this,R.array.Career, android.R.layout.simple_spinner_item);
        adapterCareer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCareer.setAdapter(adapterCareer);
        spinnerCareer.setOnItemSelectedListener(this);

        //spiner para seleccionar el ciclo escolar
        spinnerGrade = findViewById(R.id.spinner_Grade);
        ArrayAdapter<CharSequence> adapterGrade = ArrayAdapter.createFromResource(this,R.array.Grade, android.R.layout.simple_spinner_item);
        adapterGrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(adapterGrade);
        spinnerGrade.setOnItemSelectedListener(this);

        //spiner para seleccionar el ciclo escolar
        spinnerGroup = findViewById(R.id.spinner_Group);
        ArrayAdapter<CharSequence> adapterGroup = ArrayAdapter.createFromResource(this,R.array.Group, android.R.layout.simple_spinner_item);
        adapterGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroup.setAdapter(adapterGroup);
        spinnerGroup.setOnItemSelectedListener(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userRegistration = registration.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                String userPasswordConfirm = passwordConfirm.getText().toString().trim();
                String career = spinnerCareer.getSelectedItem().toString();
                String newCareer = "";
                Boolean BISstatus = btn_bis.isChecked();

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                String p = "";
                if(((month >= 1) && (day >= 1)) && ((month <= 4) && (day <= 15))){
                    p = "-1";
                }else if (((month >= 4) && (day > 15)) && ((month <= 8) && (day <= 30))){
                    p = "-2";
                }else if (((month >= 8) && (day > 30)) && ((month <= 12) && (day <= 31))){
                    p = "-3";
                }
                String period = year + p;
                String grade = spinnerGrade.getSelectedItem().toString();
                String group = spinnerGroup.getSelectedItem().toString();

                if(userEmail.isEmpty() || userName.isEmpty() || userRegistration.isEmpty() || userPassword.isEmpty() || userPasswordConfirm.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Ingrese todos sus datos", Toast.LENGTH_SHORT).show();
                }else{
                    if(userPassword.equals(userPasswordConfirm)){
                        switch (career){
                            case "Software":
                                newCareer = "ISW";
                                break;
                            case "Civil":
                                newCareer = "IC";
                                break;
                            case "Redes y telecomunicaciones":
                                newCareer = "IRT";
                                break;
                            case "Tecnologia ambiental":
                                newCareer = "ITA";
                                break;
                            case "Tecnologias de manufactura":
                                newCareer = "ITM";
                                break;
                            case "Administracion y gestion empresarial":
                                newCareer = "LAGE";
                                break;
                        }
                        SignUpUser(userName,userEmail,userRegistration,userPassword,newCareer,period,grade,group,BISstatus);
                    }
                }
            }
        });
    }

    private void SignUpUser(String userName, String userEmail, String userRegistration, String userPassword, String career, String period, String grade, String group,Boolean BIS) {
        mAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("name", userName);
                    map.put("email", userEmail);
                    map.put("registration", userRegistration);
                    map.put("career", career);
                    map.put("period", period);
                    map.put("grade", grade);
                    map.put("group", group);
                    map.put("BIS", BIS);

                    mFirestore.collection("Students").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                            startActivity(new Intent(SignUpActivity.this, verificationLobbyActivity.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(SignUpActivity.this, "Revise que sus datos esten correctos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}