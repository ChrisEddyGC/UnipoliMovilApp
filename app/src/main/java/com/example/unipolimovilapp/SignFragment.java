package com.example.unipolimovilapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SignFragment extends Fragment {

    //fragmento que redirige a los formularios activiti_login y activity_sign_up
    Button btn_Signin, btn_Signup;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign, container, false);
        btn_Signin = view.findViewById(R.id.goToLogin);
        btn_Signup = view.findViewById(R.id.goToSignUp);

        btn_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), LoginActivity.class));
            }
        });
        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SignUpActivity.class));
            }
        });

        return view;
    }
}