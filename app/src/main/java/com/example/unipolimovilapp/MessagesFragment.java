package com.example.unipolimovilapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.unipolimovilapp.adapter.messageAdapter;
import com.example.unipolimovilapp.model.message;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MessagesFragment extends Fragment{

    RecyclerView mRecycler;
    ProgressBar mProgressBar;
    messageAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    View view;
    Context mContext;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_messages, container, false);
        super.onCreate(savedInstanceState);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mProgressBar = view.findViewById(R.id.PB_messageRecyclerView);
        mProgressBar.setVisibility(View.VISIBLE);

        mRecycler = view.findViewById(R.id.recyclerViewMessagesSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setItemAnimator(null);

        //funcion para guardar consultas offline
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        FirebaseUser user= mAuth.getCurrentUser();
        if(user != null) {
            String id = user.getUid();
            mFirestore.collection("Students")
                    .document(id)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String career = documentSnapshot.getString("career");

                            //obtener las colecciones de la base de datos
                            Query query = mFirestore.collection("Messages")
                                    .whereEqualTo(career, true)
                                    .orderBy("createdAt", Query.Direction.DESCENDING)
                                    .limit(50);
                            FirestoreRecyclerOptions<message> firestoreRecyclerOptions =
                                    new FirestoreRecyclerOptions
                                            .Builder<message>()
                                            .setQuery(query, message.class)
                                            .setLifecycleOwner((LifecycleOwner) mContext)
                                            .build();

                            mAdapter = new messageAdapter(firestoreRecyclerOptions);

                            mAdapter.notifyDataSetChanged();
                            mRecycler.setAdapter(mAdapter);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText((Activity) mContext, "no se logro obtener la informacion", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


}