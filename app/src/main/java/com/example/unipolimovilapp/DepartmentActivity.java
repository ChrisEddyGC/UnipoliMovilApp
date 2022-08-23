package com.example.unipolimovilapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ablanco.zoomy.Zoomy;
import com.example.unipolimovilapp.adapter.departmentInfoAdapter;
import com.example.unipolimovilapp.model.departmentInfo;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class DepartmentActivity extends AppCompatActivity {

    View view;
    RecyclerView mRecycler;
    departmentInfoAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    ImageView depImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        depImage = findViewById(R.id.DepartmentImage);

        mRecycler = findViewById(R.id.recyclerViewDepartmentInfoSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setItemAnimator(null);

        String id = getIntent().getStringExtra("id_department");

        //funcion para guardar consultas offline
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        mFirestore.collection("Departments")
                .document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String imageURl = documentSnapshot.getString("imageURL");

                        Picasso.get()
                                .load(imageURl)
                                .placeholder(R.drawable.unipolilogo)
                                .into(depImage);

                        Zoomy.Builder builder = new Zoomy.Builder(DepartmentActivity.this)
                                .target(depImage)
                                .enableImmersiveMode(false)
                                .animateZooming(false);
                        builder.register();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DepartmentActivity.this, "no se logro obtener la informacion", Toast.LENGTH_SHORT).show();
                    }
                });

        Query query = mFirestore.collection("UniversityStaff")
                .whereEqualTo("department", id);
        FirestoreRecyclerOptions<departmentInfo> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<departmentInfo>().setQuery(query,departmentInfo.class).build();

        mAdapter = new departmentInfoAdapter(firestoreRecyclerOptions);

        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}