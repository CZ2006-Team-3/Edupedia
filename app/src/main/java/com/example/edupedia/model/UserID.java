package com.example.edupedia.model;

import com.google.firebase.auth.FirebaseUser;

public class UserID {
    private FirebaseUser ID;
    private static UserID userID = null;

    private UserID() {
    }

    public static UserID getInstance() {
        if (userID == null)
            userID = new UserID();
        return userID;
    }

    public FirebaseUser getID() {
        return ID;
    }

    public void setID(FirebaseUser ID) {
        this.ID = ID;
    }
}
