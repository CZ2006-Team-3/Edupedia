package com.example.edupedia.model;

import com.google.firebase.auth.FirebaseUser;

public class UserID {
    private String ID;
    private static UserID userID = null;

    private UserID() {
    }

    public static UserID getInstance() {
        if (userID == null)
            userID = new UserID();
        return userID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
