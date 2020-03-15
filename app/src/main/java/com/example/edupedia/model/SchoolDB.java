package com.example.edupedia.model;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;



public class SchoolDB {
    private HashMap<String, School> db;

    public void loadDB(){
        try {
            db = SchoolReader.retrieveSchools();
            Log.d("Successfully Loaded", String.valueOf(db.size()));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, School> getDB() {
        return db;
    }

}
