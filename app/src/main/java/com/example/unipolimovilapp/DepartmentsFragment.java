package com.example.unipolimovilapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.unipolimovilapp.adapter.communitynewAdapter;
import com.example.unipolimovilapp.adapter.departmentAdapter;
import com.example.unipolimovilapp.model.department;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

public class DepartmentsFragment extends Fragment {

    View view;
    SearchView searchView;
    RecyclerView mRecycler;
    departmentAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    Context mContext;
    ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_departments, container, false);
        super.onCreate(savedInstanceState);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mProgressBar = view.findViewById(R.id.PB_deparments);
        mProgressBar.setVisibility(View.VISIBLE);

        searchView = view.findViewById(R.id.deparmentSearch);
        mRecycler = view.findViewById(R.id.recyclerViewDepartmentSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setItemAnimator(null);

        //funcion para guardar la informacion en cache para consultas offline
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        Query query = mFirestore.collection("Departments")
                .orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<department> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<department>().setQuery(query, department.class).build();

        mAdapter = new departmentAdapter(firestoreRecyclerOptions, (Activity) mContext);

        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);

        searchDepartment();

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

    private void searchDepartment() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });
    }

    private void textSearch(String s) {
        Query query = mFirestore.collection("Departments")
                .orderBy("name", Query.Direction.ASCENDING)
                .startAt(s).endAt(s+"~");
        FirestoreRecyclerOptions<department> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<department>().setQuery(query, department.class).build();

        mAdapter = new departmentAdapter(firestoreRecyclerOptions, (Activity) mContext);

        mAdapter.notifyDataSetChanged();
        mAdapter.startListening();
        mRecycler.setAdapter(mAdapter);
    }

}