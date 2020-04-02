package com.example.edupedia.controller;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.edupedia.ui.SettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserController {

    private final String TAG = "UserController";
    private String name, edLevel;
    private boolean locationAccess;
    private DatabaseReference current_user_db;

    private FirebaseUser user;

    // static variable watchlistController of type WatchlistController
    private static UserController userController = null;

    // private constructor restricted to this class itself
    private UserController(FirebaseUser usr) {
        this.user = usr;
        this.current_user_db = FirebaseDatabase.getInstance().getReference().child("User_DB").child(user.getUid());
        current_user_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = (String) dataSnapshot.child("name").getValue();
                edLevel = (String) dataSnapshot.child("ed_level").getValue();
                locationAccess = dataSnapshot.child("location").getValue() != null;


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }


    // Static method to create instance of Singleton class
    public static void init() {
        FirebaseUser usr = FirebaseAuth.getInstance().getCurrentUser();
        userController = new UserController(usr);
    }

    public static UserController getInstance() {
        if(userController==null) init();

        return userController;
    }

    // Prompt the user to re-provide their sign-in credentials. Returns true if it is correct.
    public void reAuthenticateUser(String email, String password){
        // Prompt the user to re-provide their sign-in credentials
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);


        user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User re-authenticated.");
                                //Intent Start UI

                            } else {
                                //
                            }

                        }
                    });

    }

    public void changePassword(String newPwd){
        user.updatePassword(newPwd)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
    }

    public void changeEmail(String newEmail){
        user.updateEmail(newEmail);
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public String getName(){
        return name;
    }

    public String getEdLevel(){
        return edLevel;
    }

    public String getEmail(){
        String email;
        if (user!=null)
            email = user.getEmail();
        else email = "User Invalid";
        return email;
    }

    public boolean getLocationAccess(){
        String user_id = user.getUid();
       return locationAccess;
    }
}
