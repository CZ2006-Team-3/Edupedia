package com.example.edupedia.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edupedia.controller.DataStoreFactory;
import com.example.edupedia.controller.SearchController;
import com.example.edupedia.model.DataStoreInterface;
import com.example.edupedia.model.Filter;
import com.example.edupedia.ui.AdapterClass;
import com.example.edupedia.R;
import com.example.edupedia.ui.FilterUI;
import com.example.edupedia.ui.SchoolItem;
import com.example.edupedia.controller.SortController;
import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;
import com.example.edupedia.ui.AdapterClass;
import com.example.edupedia.ui.FilterUI;
import com.example.edupedia.ui.SchoolItem;
import com.example.edupedia.ui.SortBy;
import com.example.edupedia.ui.StartUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    public static final String SORT_VARIABLE_NAME = "sort";
    public static final String ASCENDING_SORT = "ascending_sort";
    public static final int RESULT_SUCCESS = 1;


    private int SORT_VARIABLE = SortController.NAME;
    private boolean SORT_ASCENDING = true;

    private ArrayList <SchoolItem> mSchoolList;
    private RecyclerView mRecyclerView;
    private AdapterClass mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SortController sortController;
    private HashMap<String, School> schools;
    private ArrayList<School> schoolArrayList;
    private SchoolDB schoolDB;

    private WatchlistController watchlistController = WatchlistController.getInstance();

    private SearchController searchController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        searchController = new ViewModelProvider(this).get(SearchController.class);

        ImageButton toSort = (ImageButton) layout.findViewById(R.id.sortButton);
        ImageButton filter = (ImageButton) layout.findViewById(R.id.filterButton);
        schoolDB = new SchoolDB(getContext());
        schools = schoolDB.getValue();

        //retrieving results from background files
        ArrayList<String> results = searchController.retrieveResults(schools);
        schoolArrayList = searchController.generateSchools(schools, results);
        //////Testing
//        ArrayList<String> results = new ArrayList<String>() ;
//        results.add("asd");
//        results.add("123");
//        results.add("122");
//        searchController = new ViewModelProvider(this).get(SearchController.class);
//        searchController.storeResults(results);
//        searchController.retrieveResults();
        /////

        toSort.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent myIntent2 = new Intent(v.getContext(), SortBy.class);
                    startActivityForResult(myIntent2, RESULT_SUCCESS);
                }
            });
        filter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(v.getContext(), FilterUI.class);
                startActivityForResult(myIntent2, RESULT_SUCCESS);
            }
        });

        createSchoolList();
        buildRecyclerView(layout);
        return layout;
    }

    public void createSchoolList() {
        mSchoolList = new ArrayList<>();
        for (int i =0; i<schoolArrayList.size(); i++) {
           mSchoolList.add(new SchoolItem(R.drawable.school_icon, schoolArrayList.get(i).getSchoolName(), Integer.toString(schoolArrayList.get(i).getGradeCutOff()), Double.toString(schoolArrayList.get(i).getDistance())));
            //mSchoolList.add(new SchoolItem(R.drawable.school_icon, "RI", "4 Points", "2 km"));
            //mSchoolList.add(new SchoolItem(R.drawable.school_icon, "AJC", "6 Points", "3 km"));
            //mSchoolList.add(new SchoolItem(R.drawable.school_icon, "AGS", "8 Points", "5 km"));
        }
    }


    public void buildRecyclerView(View layout){
        mRecyclerView = layout.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new AdapterClass(mSchoolList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterClass.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mSchoolList.get(position).openSchoolInfo();
                mAdapter.notifyItemChanged(position);

            }
            @Override
            public void onWatchListSelect(int position) {
                String schoolToAdd = mSchoolList.get(position).getSchoolName();
                String[] watchlist = watchlistController.getWatchlist();
                int i = 0;
                boolean added = false;
                while ((i < 10) && !added) {
                    if (watchlist[i] == null) {
                        watchlistController.addSchool(schoolToAdd, i);
                        Toast toast = Toast.makeText(getActivity(), "School has been added", Toast.LENGTH_SHORT);
                        toast.show();
                        added = true;
                    }
                    i++;

                    if ((i == 10) && !added) {
                        String text = Integer.toString(i);
                        //String text = "The watchlist already contains a maximum of 10 schools!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getActivity(), text, duration);
                        toast.show();

                    }
                }

                    mAdapter.notifyItemChanged(position);
                    //mSchoolList.get(position).addToWatchList();

            }

            @Override
            public void onCompareSelect(int position) {
                mSchoolList.get(position).addToCompare();
                mAdapter.notifyItemChanged(position);
                //mSchoolList.get(position).addToWatchList();
            }
        });


    }


    // to do
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_SUCCESS) {
            Bundle extras = data.getExtras();
            SORT_VARIABLE = extras.getInt(SORT_VARIABLE_NAME);
            SORT_ASCENDING = extras.getBoolean(ASCENDING_SORT);
            if(sortController==null) {
                sortController = SortController.getInstance();
            }
//            sortController.sortBy(SORT_VARIABLE, SORT_ASCENDING, );
        }

    }


}
