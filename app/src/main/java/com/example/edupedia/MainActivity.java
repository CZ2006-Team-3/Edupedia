package com.example.edupedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_main);

        BottomNavigationView bottomMenu = findViewById(com.example.edupedia.R.id.bottom_menu);
        bottomMenu.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case com.example.edupedia.R.id.searchIcon:
                            selectedFragment = new SearchFragment();
                            break;

                        case com.example.edupedia.R.id.watchListIcon:
                            selectedFragment = new WatchListFragment();
                            break;
                        case com.example.edupedia.R.id.homeIcon:
                            selectedFragment = new HomeFragment();
                            break;
                        case com.example.edupedia.R.id.compareIcon:
                            selectedFragment = new CompareFragment();
                            break;
                        case com.example.edupedia.R.id.settingsIcon:
                            selectedFragment = new SettingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(com.example.edupedia.R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

}