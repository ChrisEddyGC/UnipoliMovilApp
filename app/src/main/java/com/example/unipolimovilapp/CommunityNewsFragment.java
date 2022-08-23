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

import com.example.unipolimovilapp.adapter.communitynewAdapter;
import com.example.unipolimovilapp.adapter.messageAdapter;
import com.example.unipolimovilapp.model.communitynew;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

public class CommunityNewsFragment extends Fragment{

    RecyclerView mRecycler;
    ProgressBar mProgressBar;
    communitynewAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    View view;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_community_news, container, false);
        super.onCreate(savedInstanceState);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mProgressBar = view.findViewById(R.id.PB_communitynewsRecyclerView);
        mProgressBar.setVisibility(View.VISIBLE);

        mRecycler = view.findViewById(R.id.recyclerViewCommunityNewsSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setItemAnimator(null);

        //funcion para guardar la informacion en cache para consultas offline
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        Query query = mFirestore.collection("CommunityNews")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(50);
        FirestoreRecyclerOptions<communitynew> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions
                        .Builder<communitynew>()
                        .setQuery(query, communitynew.class)
                        .setLifecycleOwner((LifecycleOwner) mContext)
                        .build();

        mAdapter = new communitynewAdapter(firestoreRecyclerOptions, (Activity) mContext);

        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

}