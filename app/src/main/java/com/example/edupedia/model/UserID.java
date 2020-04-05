package com.example.edupedia.model;

import com.google.firebase.auth.FirebaseUser;


/**
 * singleton instance to store userid string of the
 * user logged into the app
 */
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
