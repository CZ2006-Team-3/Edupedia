package com.example.edupedia.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.widget.SearchView;

import com.example.edupedia.R;
import com.example.edupedia.controller.UserController;
import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.model.UserID;
import com.example.edupedia.ui.WatchList.WatchListFragment;
import com.example.edupedia.ui.home.HomeFragment;
import com.example.edupedia.ui.Compare.CompareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainNavigationUI extends AppCompatActivity {
    private SharedPreferences sharedPref;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation_ui);
        Intent intent = getIntent();
//        uid = intent.getStringExtra(StartUI.firebase_key);
        uid = UserID.getInstance().getID();
        WatchlistController.init(uid);
        UserController.init(FirebaseAuth.getInstance().getCurrentUser());
        BottomNavigationView bottomMenu = findViewById(R.id.bottom_menu);
        bottomMenu.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new SearchFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.searchIcon:
                            selectedFragment = new SearchFragment();
                            break;

                        case R.id.watchListIcon:
                            selectedFragment = new WatchListFragment();
                            break;
                        case R.id.homeIcon:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.compareIcon:
                            selectedFragment = new CompareFragment();
                            break;
                        case R.id.settingsIcon:
                            selectedFragment = new SettingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };


}



