package com.example.edupedia.model;

import com.google.firebase.auth.FirebaseUser;


/**
 * singleton instance to store userid string of the
 * user logged into the app
 */
public class UserID {
    /**
     * ID of the user
     */
    private String ID;
    /**
     * instance of userID
     */
    private static UserID userID = null;

    /**
     * Constructor for UserID
     */
    private UserID() {
    }

    /**
     * Gets the instance of the UserID
     * @return the instance of the UserID
     */
    public static UserID getInstance() {
        if (userID == null)
            userID = new UserID();
        return userID;
    }

    /**
     * gets the ID of the user
     * @return the ID of the user
     */
    public String getID() {
        return ID;
    }

    /**
     * changes the ID of the user
     * @param ID this user's ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }
}
