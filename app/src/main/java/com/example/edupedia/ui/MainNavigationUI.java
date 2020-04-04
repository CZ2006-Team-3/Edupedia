package com.example.edupedia.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.widget.SearchView;

import com.example.edupedia.R;
import com.example.edupedia.controller.UserController;
import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.model.School;
import com.example.edupedia.model.UserID;
import com.example.edupedia.ui.WatchList.WatchListFragment;
import com.example.edupedia.ui.home.HomeFragment;
import com.example.edupedia.ui.Compare.CompareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainNavigationUI extends AppCompatActivity {
    private SharedPreferences sharedPref;

    public final HomeFragment homeFragment = new HomeFragment();
    public final SearchFragment searchFragment = new SearchFragment();
    public final SettingsFragment settingsFragment = new SettingsFragment();
    public final WatchListFragment watchlistFragment = new WatchListFragment();
    public final CompareFragment compareFragment = new CompareFragment();
    public final FragmentManager fm = getSupportFragmentManager();
    public Fragment currentFragment = searchFragment;
    private HashMap<String, School> schools;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation_ui);
        Intent intent = getIntent();
//        uid = intent.getStringExtra(StartUI.firebase_key);

//        UserController.init(FirebaseAuth.getInstance().getCurrentUser());
        BottomNavigationView bottomMenu = findViewById(R.id.bottom_menu);
        bottomMenu.setOnNavigationItemSelectedListener(navListener);


        fm.beginTransaction().add(R.id.fragment_container,
                homeFragment).hide(homeFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,
                watchlistFragment).hide(watchlistFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,
                settingsFragment).hide(settingsFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,
                compareFragment).hide(compareFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container,
                currentFragment).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {

                switch (item.getItemId()){
                    case R.id.searchIcon:
                        fm.beginTransaction().hide(currentFragment).show(searchFragment).commit();
                        currentFragment = searchFragment;
                        break;

                    case R.id.watchListIcon:
                        watchlistFragment.updateInfo();
                        fm.beginTransaction().hide(currentFragment).show(watchlistFragment).commit();
                        currentFragment = watchlistFragment;
                        break;

                    case R.id.homeIcon:
                        //homeFragment.updateInfo();
                        fm.beginTransaction().hide(currentFragment).show(homeFragment).commit();
                        currentFragment = homeFragment;
                        break;
                    case R.id.compareIcon:
                        fm.beginTransaction().hide(currentFragment).show(compareFragment).commit();
                        currentFragment = compareFragment;
                        break;
                    case R.id.settingsIcon:
                        settingsFragment.updateInfo();
                        fm.beginTransaction().hide(currentFragment).show(settingsFragment).commit();
                        currentFragment = settingsFragment;
                        break;
                }
                return true;
            };

    public FragmentManager getFm() {
        return fm;
    }

    public void setSchoolDB(HashMap<String, School> schools) {
        this.schools = schools;
    }

    public HashMap<String, School> getSchoolDB() { return this.schools; }
}



