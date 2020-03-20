package com.example.edupedia.controller;

import com.example.edupedia.model.School;

public class WatchlistController {
    private School[] watchList;

    private static WatchlistController watchlistController = null;

    private WatchlistController() {
        watchList = new School[10];
    }

    public static WatchlistController getInstance() {
        if (watchlistController == null)
            watchlistController = new WatchlistController();
        return watchlistController;
    }

    public School[] getWatchList() {
        return watchList;
    }

    void addSchool(School school, int toAdd) {
        watchList[toAdd] = school;
    }

    void changePosition(int pos1, int pos2) {
        School temp = watchList[pos1];
        watchList[pos1] = watchList[pos2];
        watchList[pos2] = temp;
    }

    void removeSchool(int toRemove) {
        watchList[toRemove] = null;
    }

}
