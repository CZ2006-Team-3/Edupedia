package com.example.edupedia.controller;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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


    private FirebaseUser user;

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

    public void getName(){
        String user_id = user.getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User_DB").child(user_id);

        current_user_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("watchlist").getValue() != null) {
                    String key = dataSnapshot.getKey();
                    Log.d("FireBase REAADDD", key);
                    name = dataSnapshot.child("name").getValue();
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
