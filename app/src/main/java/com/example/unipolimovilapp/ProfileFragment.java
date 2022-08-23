package com.example.unipolimovilapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ablanco.zoomy.Zoomy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class ProfileFragment extends Fragment implements FirebaseAuth.AuthStateListener{

    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    View view;
    TextView userName, registration, career, group, period;
    ImageView schedule;
    Button btnLogout;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        super.onCreate(savedInstanceState);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userName = view.findViewById(R.id.home_username);
        registration = view.findViewById(R.id.home_registration);
        career = view.findViewById(R.id.home_career);
        group = view.findViewById(R.id.home_group);
        period = view.findViewById(R.id.home_period);
        schedule = view.findViewById(R.id.home_schedule);
        btnLogout = view.findViewById(R.id.btn_LogOut);

        //funcion para guardar consultas offline
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this::onAuthStateChanged);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this::onAuthStateChanged);
    }
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user= firebaseAuth.getCurrentUser();
        if(user != null){
            String id = user.getUid();
            mFirestore.collection("Students").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    String careerName = getCareer(document.getString("career"));
                    String curUserName =document.getString("name");
                    String curUserRegistration ="Matricula: " + document.getString("registration");
                    String curUserCareer ="Carrera: " + careerName;
                    String curUserGroup ="Grupo: " + document.getString("grade") + " '" + document.getString("group") + "'";
                    if(document.getBoolean("BIS")){
                        curUserGroup += " BIS";
                    }

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
                    String curUserPeriod ="Ciclo escolar: " + year + p;

                    userName.setText(curUserName);
                    registration.setText(curUserRegistration);
                    career.setText(curUserCareer);
                    group.setText(curUserGroup);
                    period.setText(curUserPeriod);
                    mFirestore.collection("Schedules")
                            .whereEqualTo("career", document.getString("career"))
                            .whereEqualTo("grade",document.getString("grade"))
                            .whereEqualTo("group", document.getString("group"))
                            .whereEqualTo("period", year + p)
                            .whereEqualTo("BIS", document.getBoolean("BIS"))
                                    .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            Picasso.get()
                                                                    .load(document.getString("imageURL"))
                                                                    .into(schedule);
                                                            Zoomy.Builder builder = new Zoomy.Builder((Activity) view.getContext())
                                                                    .target(schedule)
                                                                    .enableImmersiveMode(false)
                                                                    .animateZooming(false);
                                                            builder.register();
                                                        }
                                                    }
                                                }
                                            });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText((Activity) mContext, "no se logro obtener la informacion", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private String getCareer (String career){
        switch (career){
        case "ISW":
            return "Ing. en software";
        case "IC":
            return "Ing. civil";
        case "IRT":
            return "Ing. en redes y telecomunicaciones";
        case "ITA":
            return "Ing. en tecnologia ambiental";
        case "ITM":
            return "Ing. en tecnologias de manufactura";
        case "LAGE":
            return "Lic. en administracion y gestion empresarial";
        }
        return "";
    }
}