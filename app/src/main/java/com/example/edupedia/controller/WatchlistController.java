package com.example.edupedia.controller;

import com.example.edupedia.model.School;

public class WatchlistController {
    // watchlist variable which hold the user's saved results
    private School[] watchList;

    // static variable watchlistController  of type WatchlistController
    private static WatchlistController watchlistController = null;

    // private constructor restricted to this class itself
    private WatchlistController() {
        watchList = new School[10];
    }

    // Static method to create instance of Singleton class
    public static WatchlistController getInstance() {
        if (watchlistController == null)
            watchlistController = new WatchlistController();
        return watchlistController;
    }

    // Getter method for retrieving watchlist
    public School[] getWatchList() {
        return watchList;
    }

    // Method for user to add a result to the watchlist
    public void addSchool(School school, int toAdd) {
        watchList[toAdd] = school;
    }

    // Method for user to swap 2 result positions in the watchlist
    public void changePosition(int pos1, int pos2) {
        School temp = watchList[pos1];
        watchList[pos1] = watchList[pos2];
        watchList[pos2] = temp;
    }

    // method to remove a result from the watchlist
    public void removeSchool(int toRemove) {
        watchList[toRemove] = null;
    }

}
