package com.example.unipolimovilapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuNavBar extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    MessagesFragment messagesFragment = new MessagesFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    CommunityNewsFragment communityNewsFragment = new CommunityNewsFragment();
    DepartmentsFragment departmentFragment = new DepartmentsFragment();
    CalendarFragment calendarFragment = new CalendarFragment();
    int curFragment = 0;

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nav_bar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        curFragment = 1;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.nav_messages:
                        curFragment = 2;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,messagesFragment).commit();
                        return true;
                    case R.id.nav_communitynews:
                        curFragment = 3;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,communityNewsFragment).commit();
                        return true;
                    case R.id.nav_departments:
                        curFragment = 4;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,departmentFragment).commit();
                        return true;
                    case R.id.nav_calendar:
                        curFragment = 5;
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,calendarFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.action_nav_menu,menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.nav_settings:
                startActivity(new Intent(MenuNavBar.this, SettingsActivity.class));
                return true;
            case R.id.nav_profile:
                curFragment = 6;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                break;
            case 2:
                curFragment = 2;
                getSupportFragmentManager().beginTransaction().replace(R.id.container,messagesFragment).commit();
                break;
            case 3:
                curFragment = 3;
                getSupportFragmentManager().beginTransaction().replace(R.id.container,communityNewsFragment).commit();
                break;
            case 4:
                curFragment = 4;
                getSupportFragmentManager().beginTransaction().replace(R.id.container,departmentFragment).commit();
                break;
            case 5:
                curFragment = 5;
                getSupportFragmentManager().beginTransaction().replace(R.id.container,calendarFragment).commit();
                break;
            case 6:
                curFragment = 6;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                break;
            default:
                curFragment = 1;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                break;
        }
    }
}