package com.example.edupedia.ui.WatchList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edupedia.R;
import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;
import com.example.edupedia.ui.AdapterClass;
import com.example.edupedia.ui.SchoolItem;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.edupedia.controller.WatchlistController.*;

public class WatchListFragment extends Fragment {

    private WatchListViewModel WatchListViewModel;

    //
    private ArrayList<SchoolItem> wSchoolList;
    private RecyclerView wRecyclerView;
    private AdapterClass wAdapter;
    private RecyclerView.LayoutManager wLayoutManager;
    private String[] watchList;
    private ArrayList<School> schoolList;
    private HashMap<String, School> schools;
    private SchoolDB schoolDB;


    // Instantiating watchlistController class
    private WatchlistController watchlistController = WatchlistController.getInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        /*
//        watchlistController.setWatchlist(new String[] {"zero","one", "two","three","four",null,"six","seven","eight","nine"});
//        watchlistController.addSchool("five",5);
//        watchlistController.changePosition(3,7);
//        watchlistController.removeSchool(9);
//        watchlistController.pushWatchlist();
//        watchlistController.pullWatchlist();
//         */
        watchList = watchlistController.getWatchlist();
        boolean empty = true;
        for (String schoolName : watchList) {
            if (schoolName != null) {
                empty = false;
                break;
            }
        }
        if (empty) {
            Toast toast = Toast.makeText(getActivity(), "None of the schools have been added to the WatchList", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            schoolDB = new SchoolDB(getContext());
            schools = schoolDB.getValue();
            schoolList = generateSchools(schools, watchList);
            if (schoolList.size() != 0) {
                wSchoolList = new ArrayList<>();
                for (School school : schoolList) {
                    if(school!=null)
                        wSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(), Integer.toString(school.getGradeCutOff()), Double.toString(school.getDistance())));
                }
            }
        }

        return inflater.inflate(R.layout.fragment_watchlist, container, false);

    }

    public ArrayList<School> generateSchools(HashMap<String, School> db, String[] results) {
        ArrayList<School> schoolList = new ArrayList<>();
        for (String name : results) {
            schoolList.add(db.get(name));
        }

        return schoolList;
    }

    public void buildRecyclerView(View layout) {
        wRecyclerView = layout.findViewById(R.id.recycler_view);
        wRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        wRecyclerView.setHasFixedSize(true);
        wLayoutManager = new LinearLayoutManager(getActivity());
        wAdapter = new AdapterClass(wSchoolList);

        wRecyclerView.setLayoutManager(wLayoutManager);
        wRecyclerView.setAdapter(wAdapter);

        wAdapter.setOnItemClickListener(new AdapterClass.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                wSchoolList.get(position).openSchoolInfo();
                wAdapter.notifyItemChanged(position);

            }

            @Override
            public void onWatchListSelect(int position) {
                String schoolToremove = wSchoolList.get(position).getSchoolName();
                String[] watchlist = watchlistController.getWatchlist();
                int i = 0;
                while (i<10){
                    if (watchlist[i] == schoolToremove){
                        watchlistController.removeSchool(i);
                        wSchoolList.remove(i);
                        Toast toast = Toast.makeText(getActivity(), "School has been removed", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
                wAdapter.notifyItemChanged(position);
                //mSchoolList.get(position).addToWatchList();
            }

            @Override
            public void onCompareSelect(int position) {
                wSchoolList.get(position).addToCompare();
                wAdapter.notifyItemChanged(position);
                //mSchoolList.get(position).addToWatchList();
            }
        });


    }
}
