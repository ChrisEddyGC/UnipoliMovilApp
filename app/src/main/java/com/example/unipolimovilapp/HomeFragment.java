package com.example.unipolimovilapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    View view;
    Button hlWebPage, hlControl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        hlWebPage = view.findViewById(R.id.btn_webPage);
        hlControl = view.findViewById(R.id.btn_control);

        hlWebPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.unipolidgo.edu.mx/sitio/");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
        hlControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.unipolidgo.edu.mx/sitio/?page_id=248");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
        return view;
    }
}