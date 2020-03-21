package com.example.edupedia.ui.WatchList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.edupedia.R;
import com.example.edupedia.controller.WatchlistController;

import static com.example.edupedia.controller.WatchlistController.*;

public class WatchListFragment extends Fragment {

    private WatchListViewModel WatchListViewModel;

    // Instantiating watchlistController class
    private WatchlistController watchlistController = WatchlistController.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        watchlistController.setWatchlist(new String[] {"alal", "asd","as","",""});
        watchlistController.pushWatchlist();
        watchlistController.pullWatchlist();
        return inflater.inflate(R.layout.fragment_watchlist, container, false);


    }



}
