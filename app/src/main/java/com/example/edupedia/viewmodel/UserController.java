package com.example.edupedia.viewmodel;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


}
