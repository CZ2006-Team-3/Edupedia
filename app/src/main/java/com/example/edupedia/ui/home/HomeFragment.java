package com.example.edupedia.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edupedia.controller.DataStoreFactory;
import com.example.edupedia.controller.SearchController;
import com.example.edupedia.model.DataStoreInterface;
import com.example.edupedia.model.Filter;
import com.example.edupedia.ui.AdapterClass;
import com.example.edupedia.R;
import com.example.edupedia.ui.Compare.CompareFragment;
import com.example.edupedia.ui.FilterUI;
import com.example.edupedia.ui.SchoolItem;
import com.example.edupedia.controller.SortController;
import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;
import com.example.edupedia.ui.AdapterClass;
import com.example.edupedia.ui.FilterUI;
import com.example.edupedia.ui.SchoolItem;
import com.example.edupedia.ui.SearchFragment;
import com.example.edupedia.ui.SettingsFragment;
import com.example.edupedia.ui.SortBy;
import com.example.edupedia.ui.SortByDialogFragment;
import com.example.edupedia.ui.StartUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements SortByDialogFragment.SortByDialogListener,
                                                        View.OnClickListener{
    public static final String SORT_VARIABLE_NAME = "sort";
    public static final String ASCENDING_SORT = "ascending_sort";
    public static final int RESULT_SUCCESS = 1;
    private String TAG = "HomeFragment";


    public interface SortEventListener{
        public void onRequestSort(int sort_variable, boolean sort_ascending);
    }

    private int SORT_VARIABLE = SortController.NAME;
    private boolean SORT_ASCENDING = true;

    private ArrayList<SchoolItem> mSchoolList;
    private RecyclerView mRecyclerView;
    private AdapterClass mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SortController sortController;
    private HashMap<String, School> schools;
    private ArrayList<School> schoolArrayList;
    private SchoolDB schoolDB;
    private WatchlistController watchlistController = WatchlistController.getInstance();

    private SearchController searchController;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        searchController = new ViewModelProvider(this).get(SearchController.class);

        ImageButton toSort = (ImageButton) layout.findViewById(R.id.sortButton);
        ImageButton filter = (ImageButton) layout.findViewById(R.id.filterButton);
        SearchView searchButton = (SearchView) layout.findViewById(R.id.searchButton);

        schoolDB = new SchoolDB(getContext());
        schools = schoolDB.getValue();

        //retrieving results from background files
        ArrayList<String> results = searchController.retrieveResults(schools);
        schoolArrayList = searchController.generateSchools(schools, results);
        //idk why this is called twice tbh.
        schoolArrayList = searchController.getDistances(schoolArrayList);
        toSort.setOnClickListener(this);
/*
        toSort.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
//                    Intent myIntent2 = new Intent(v.getContext(), SortBy.class);
//                    startActivityForResult(myIntent2, RESULT_SUCCESS);
                 FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                  DialogFragment sortBy = new SortByDialogFragment();
                  sortBy.show(ft, "dialog");
              }
        });*/
        toSort.setOnClickListener(this);
//        toSort.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
////                    Intent myIntent2 = new Intent(v.getContext(), SortBy.class);
////                    startActivityForResult(myIntent2, RESULT_SUCCESS);
//                 FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                  SortByDialogFragment sortBy = new SortByDialogFragment();
//                  sortBy.setDialogFragmentListener(this);
//                  sortBy.show(ft, "Sort By");
//
//                                      }
//                                  });
        filter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(v.getContext(), FilterUI.class);
                startActivityForResult(myIntent2, RESULT_SUCCESS);
            }
        });

        searchButton.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });

        createSchoolList();
        buildRecyclerView(layout);
        return layout;
    }

    public void createSchoolList() {
        mSchoolList = new ArrayList<>();
        for(School school : schoolArrayList) {
            if (school.getMainCode().equals("SECONDARY")) {
                mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                        "Grade Cut-Off: " + Integer.toString(school.getGradePSLE()),
                        "Distance: " + Double.toString(school.getDistance())));
            }
            else if (school.getMainCode().equals("JUNIOR COLLEGE")){
                mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                        "Grade Cut-Off: " + Integer.toString(school.getGradeO()),
                        "Distance: " + Double.toString(school.getDistance())));
            }
            else if (school.getMainCode().equals("MIXED LEVEL")){
                mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                        "Grade Cut-Off for A-level: " + Integer.toString(school.getGradePSLE()) + " Grade Cut-Off for O-level: " + Integer.toString(school.getGradeO()) ,
                        "Distance: " + Double.toString(school.getDistance())));
            }
            else
                mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                        "Grade Cut-Off: Not Applicable " ,
                        "Distance: " + Double.toString(school.getDistance())));
            }

            //mSchoolList.add(new SchoolItem(R.drawable.school_icon, "RI", "4 Points", "2 km"));
            //mSchoolList.add(new SchoolItem(R.drawable.school_icon, "AJC", "6 Points", "3 km"));
            //mSchoolList.add(new SchoolItem(R.drawable.school_icon, "AGS", "8 Points", "5 km"));
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
                Intent intent = new Intent(HomeFragment.super.getContext(), schoolInfoUI.class);
                School school = schoolArrayList.get(position);

                String schoolName = school.getSchoolName();
                String course = school.getMainCode();
                Integer grade;
                if (school.getMainCode().equals("SECONDARY")) {
                    grade = school.getGradePSLE();
                }
                else if (school.getMainCode().equals("JUNIOR COLLEGE")){
                    grade = school.getGradeO();
                }
                else{
                    grade = school.getGradeCutOff();
                }
                Double drive = school.getDrivingTime();
                Double dist = school.getDistance();
                Double publicTime = school.getPublicTime();
                intent.putExtra("schoolName", schoolName);
                intent.putExtra("course", course);
                intent.putExtra("grade", grade);
                intent.putExtra("drive", drive);
                intent.putExtra("dist", dist);
                intent.putExtra("publicTime", publicTime);
                startActivity(intent);
            }
            @Override
            public void onWatchListSelect(int position) {
                String schoolToAdd = mSchoolList.get(position).getSchoolName();
                if(watchlistController.exists(schoolToAdd)){
                    Toast toast = Toast.makeText(getActivity(), "School already exists!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
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
                        String text = "The watchlist already contains a maximum of 10 schools!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getActivity(), text + i, duration);
                        toast.show();
                    }
                }

                    mAdapter.notifyItemChanged(position);
                    //mSchoolList.get(position).addToWatchList();
            }

            @Override
            public void onCompareSelect(int position) {
                String schoolToCompare = mSchoolList.get(position).getSchoolName();
                CompareFragment comparison = new CompareFragment();
                Bundle args = new Bundle();
                args.putString("SchoolName", schoolToCompare);
                comparison.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        comparison).commit();
                Toast toast = Toast.makeText(getActivity(), "School has been added to comparison", Toast.LENGTH_LONG);
                toast.show();
                mAdapter.notifyItemChanged(position);
                //mSchoolList.get(position).addToWatchList();
            }
        });

    }

    // to do
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onChangeDialog(int sort_variable, boolean sort_ascending) {
        SORT_VARIABLE = sort_variable;
        SORT_ASCENDING = sort_ascending;
        createSchoolList();
        mAdapter.onRequestSort(sort_variable,sort_ascending);
    }




    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.sortButton:
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                SortByDialogFragment sortBy = new SortByDialogFragment();
                sortBy.setDialogFragmentListener(this);
                sortBy.show(ft, "Sort By");
                break;

            case R.id.filterButton:


            case R.id.searchButton:
        }
    }
}
