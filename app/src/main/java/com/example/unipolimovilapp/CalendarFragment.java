package com.example.unipolimovilapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unipolimovilapp.adapter.agendaAdapter;
import com.example.unipolimovilapp.model.Agenda;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

public class CalendarFragment extends Fragment {

    RecyclerView mRecycler;
    agendaAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    View view;
    Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_calendar, container, false);
        super.onCreate(savedInstanceState);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mRecycler = view.findViewById(R.id.recyclerViewAgendasSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setItemAnimator(null);

        //funcion para guardar la informacion en cache para consultas offline
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        Query query = mFirestore.collection("Agendas")
                .orderBy("ciclo", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Agenda> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Agenda>().setQuery(query,Agenda.class).build();

        mAdapter = new agendaAdapter(firestoreRecyclerOptions, (Activity) mContext);

        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);


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
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}