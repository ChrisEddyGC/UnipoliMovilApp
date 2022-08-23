package com.example.unipolimovilapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.unipolimovilapp.adapter.messageAdapter;
import com.example.unipolimovilapp.model.message;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    //la actividad inicial de la aplicacion
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    CommunityNewsFragment communityNewsFragment = new CommunityNewsFragment();
    DepartmentsFragment departmentFragment = new DepartmentsFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    SignFragment signFragment = new SignFragment();
    FirebaseAuth mAuth;
    int curFragment = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_main));
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            if (user.isEmailVerified()) {
                finish();
                startActivity(new Intent(MainActivity.this, MenuNavBar.class));
            }else{
                finish();
                startActivity(new Intent(MainActivity.this, verificationLobbyActivity.class));
            }
        }
        bottomNavigationView = findViewById(R.id.bottomMainNavigationView);

        //funcionalidad del bottom_main_nav_menu
        getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        curFragment = 1;
                        getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,homeFragment).commit();
                        return true;
                    case R.id.nav_communitynews:
                        curFragment = 2;
                        getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,communityNewsFragment).commit();
                        return true;
                    case R.id.nav_departments:
                        curFragment = 3;
                        getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,departmentFragment).commit();
                        return true;
                    case R.id.nav_calendar:
                        curFragment = 4;
                        getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,calendarFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.action_main_nav_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        //funcionalidad del action_main_nav_menu
        switch (item.getItemId()){
            case R.id.nav_sign:
                curFragment = 5;
                getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,signFragment).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurFrag", curFragment);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        switch (savedInstanceState.getInt("curFrag")){
            case 1:
                curFragment = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer, homeFragment).commit();
                break;
            case 2:
                curFragment = 2;
                getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,communityNewsFragment).commit();
                break;
            case 3:
                curFragment = 3;
                getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,departmentFragment).commit();
                break;
            case 4:
                curFragment = 4;
                getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,calendarFragment).commit();
                break;
            case 5:
                curFragment = 5;
                getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer,signFragment).commit();
                break;
            default:
                curFragment = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer, homeFragment).commit();
                break;
        }
    }
}