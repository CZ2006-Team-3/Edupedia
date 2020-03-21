package com.example.edupedia.controller;

import android.util.Log;

import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WatchlistController {
    public String[] getWatchlist() {
        return watchlist;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setWatchlist(String[] watchlist) {
        this.watchlist = watchlist;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    // Watchlist attribute of String and School classes
    private String[] watchlist;

    private FirebaseUser user;

    // static variable watchlistController of type WatchlistController
    private static WatchlistController watchlistController = null;

    // private constructor restricted to this class itself
    private WatchlistController() {
        watchlist = new String[10];
        pullWatchlist();
    }


    // Static method to create instance of Singleton class
    public static WatchlistController getInstance() {
        if (watchlistController == null)
            watchlistController = new WatchlistController();
        return watchlistController;
    }


    // Method for user to add a result to the watchlist
    public void addSchool(String school, int toAdd) {
        watchlist[toAdd] = school;
    }

    // Method for user to swap 2 result positions in the watchlist
    public void changePosition(int pos1, int pos2) {
        String temp = watchlist[pos1];
        watchlist[pos1] = watchlist[pos2];
        watchlist[pos2] = temp;
    }

    // To remove a result from the watchlist
    public void removeSchool(int toRemove) {
        watchlist[toRemove] = null;
    }

    final String user_id = "INmxYLFnjyRM2tO8PujPc9KyFxD2";
    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User_DB").child(user_id);

    // Push watchlist to database
    public void pushWatchlist() {
        Log.d("PUSHWATCHLIST", watchlist[0]);
        List<String> watchlist_List =  new ArrayList<>();
        for (int i =0; i<10; i++){
            watchlist_List.add(watchlist[i]);
        }
        current_user_db.child("watchlist").setValue(watchlist_List);
    }

    // Retrieve watchlist from database
    public void pullWatchlist() {
        current_user_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("watchlist").getValue() != null) {
                    String key = dataSnapshot.getKey();
                    Log.d("FireBase REAADDD", key);
                    watchlist = (String[]) dataSnapshot.child("watchlist").getValue();
                    Log.d("FireBase REAADDD", watchlist[0]);
                } else {
                    watchlist = new String[10];
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });


    }

}
