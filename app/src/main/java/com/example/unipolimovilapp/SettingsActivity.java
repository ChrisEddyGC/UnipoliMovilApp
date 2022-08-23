package com.example.unipolimovilapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Context;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btn_update, btn_delete;
    Switch btn_bis;
    EditText name, registration, emailUpdate, emailDelete;
    Spinner spinnerCareer, spinnerGrade, spinnerGroup;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    Context mContext;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_update = findViewById(R.id.btn_UpdateUser);
        btn_delete = findViewById(R.id.btn_deleteUser);
        name = findViewById(R.id.NewName);
        registration = findViewById(R.id.newRegistration);
        emailUpdate = findViewById(R.id.EmailConfirmationUpdate);
        emailDelete = findViewById(R.id.EmailConfirmationdelete);
        btn_bis = findViewById(R.id.switch_newBIS);

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

        //funcion para guardar consultas offline
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            mFirestore.collection("Students")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            name.setText(documentSnapshot.getString("name"));
                            registration.setText(documentSnapshot.getString("registration"));
                            spinnerCareer.setSelection(getIndex(spinnerCareer,documentSnapshot.getString("career")));
                            spinnerGrade.setSelection(getIndex(spinnerGrade,documentSnapshot.getString("grade")));
                            spinnerGroup.setSelection(getIndex(spinnerGroup,documentSnapshot.getString("group")));
                            btn_bis.setChecked(documentSnapshot.getBoolean("BIS"));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SettingsActivity.this, "no se logro obtener la informacion", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString().trim();
                String userRegistration = registration.getText().toString().trim();
                String userEmail = emailUpdate.getText().toString().trim();
                String career = spinnerCareer.getSelectedItem().toString();
                String newCareer = "";
                String grade = spinnerGrade.getSelectedItem().toString();
                String group = spinnerGroup.getSelectedItem().toString();
                Boolean BISstatus = btn_bis.isChecked();
                if(userName.isEmpty()){
                    Toast.makeText(SettingsActivity.this, "Ingrese todos sus datos", Toast.LENGTH_SHORT).show();
                }else {
                    if(userEmail.isEmpty()){
                        Toast.makeText(SettingsActivity.this, "Ingrese correo para verificar su identidad", Toast.LENGTH_SHORT).show();
                    }else{
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String id = mAuth.getCurrentUser().getUid();
                            if(userEmail.equals(user.getEmail())){
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
                                Map<String, Object> map = new HashMap<>();
                                map.put("name",userName);
                                map.put("registration", userRegistration);
                                map.put("career", newCareer);
                                map.put("grade", grade);
                                map.put("group", group);
                                map.put("BIS", BISstatus);
                                mFirestore.collection("Students").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SettingsActivity.this, "Su informacion ha sido actualizada", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(SettingsActivity.this, MenuNavBar.class));
                                    }
                                });

                            }
                            else{
                                Toast.makeText(SettingsActivity.this, "revise que su correo este bien escrito", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }

            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = emailDelete.getText().toString().trim();
                if(userEmail.isEmpty()){
                    Toast.makeText(SettingsActivity.this, "Ingrese correo para verificar su identidad", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        String id = mAuth.getCurrentUser().getUid();
                        String email = mAuth.getCurrentUser().getEmail();
                        if (userEmail.equals(email)) {
                            mFirestore.collection("Students").document(id).delete();
                            user.delete();
                            finish();
                            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                        }
                        else{
                            Toast.makeText(SettingsActivity.this, "revise que su correo este bien escrito", Toast.LENGTH_SHORT).show();
                        }

                    }
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


    private int getIndex(Spinner spinner, String s){
        for(int i = 0; i< spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }
}