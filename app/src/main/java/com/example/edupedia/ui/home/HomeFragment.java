package com.example.edupedia.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import com.example.edupedia.ui.AdvFilterDialogFragment;
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
import java.util.List;

public class HomeFragment extends Fragment implements SortByDialogFragment.SortByDialogListener,
        View.OnClickListener, AdvFilterDialogFragment.AdvFilterDialogListener{
    public static final String SORT_VARIABLE_NAME = "sort";
    public static final String ASCENDING_SORT = "ascending_sort";
    public static final int RESULT_SUCCESS = 1;
    private String TAG = "HomeFragment";

    public interface SortEventListener{
        public void onRequestSort(int sort_variable, boolean sort_ascending);
    }

    private int SORT_VARIABLE = SortController.NAME;
    private boolean SORT_ASCENDING = true;

    private ArrayList<String> region;
    private ArrayList<String> IP;
    private ArrayList<String> type;

    private ArrayList<SchoolItem> mSchoolList;
    private RecyclerView mRecyclerView;
    private AdapterClass mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SortController sortController;
    private SchoolDB schoolDB;
    private HashMap<String, School> schools;
    private ArrayList<School> schoolArrayList;

    private WatchlistController watchlistController = WatchlistController.getInstance();
    private AdvFilterDialogFragment advFilter = new AdvFilterDialogFragment();
    private SearchController searchController;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       /* if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            //ok this runs while the background gets the google maps
            Log.e("UIStuff", "Home Fragment On UI Thread");
        } else {
            Log.e("UIStuff", "Home Fragment Not on UI Thread");
        }*/

        View layout = inflater.inflate(R.layout.fragment_home, container, false);
      /*  if (getFragmentManager().findFragmentById(R.id.fragment_container) != null) {
            Fragment currentFragment = getFragmentManager().findFragmentById(R.id.fragment_container);
            if (currentFragment instanceof SearchFragment) {
                Log.e("UIStuff", "Is Instance of Search");
            }
            if (currentFragment instanceof HomeFragment) {
                Log.e("UIStuff", "Is Instance of Home");
            }
            if (currentFragment.isVisible()) {
                Log.e("UIStuff", "Is Vis");
            }
        }*/
        searchController = new ViewModelProvider(this).get(SearchController.class);
        ImageButton toSort = (ImageButton) layout.findViewById(R.id.sortButton);
        ImageButton filter = (ImageButton) layout.findViewById(R.id.filterButton);
        SearchView searchButton = (SearchView) layout.findViewById(R.id.searchButton);

        schoolDB = new SchoolDB(getContext());
        schools = schoolDB.getValue();
        Log.d("SchDB", "Loaded");

        //retrieving results from background files
        //whenever home is called the distance is not updated in these schools
        ArrayList<String> results = searchController.retrieveResults(schools);
        Log.d("HomeFragment", String.valueOf(results));
        //gets a list of schools based on a string of school names
        schoolArrayList = searchController.generateSchools(schools, results);
        Toast.makeText(getContext(), "Home Fragment", Toast.LENGTH_SHORT);
        Log.d("UIStuff", "here");
        //schoolArrayList = searchController.getDistances(schoolArrayList);
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
        //toSort.setOnClickListener(this);
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
//        filter.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent myIntent2 = new Intent(v.getContext(), FilterUI.class);
//                startActivityForResult(myIntent2, RESULT_SUCCESS);
//            }
//        });
        filter.setOnClickListener(this);

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
        Log.d("UIStuff", "Built Here");
        return layout;
    }

    public void createSchoolList() {
        mSchoolList = new ArrayList<>();
        for (School school : schoolArrayList) {
            switch (school.getMainCode()) {
                case "SECONDARY":
                    mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                            "Grade Cut-Off: " + school.getGradePSLE(),
                            "Distance: " + school.getDistance(),
                            Double.toString(school.getPublicTime()),
                            Double.toString(school.getDrivingTime()),
                            school.getZoneCode(),
                            school.getTypeCode(),
                            (school.isIp())? "Yes" : "No"));
                    break;

                case "JUNIOR COLLEGE":
                    mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                            "Grade Cut-Off: " + school.getGradeO(),
                            "Distance: " + school.getDistance(),
                            Double.toString(school.getPublicTime()),
                            Double.toString(school.getDrivingTime()),
                            school.getZoneCode(),
                            school.getTypeCode(),
                            (school.isIp())? "Yes" : "No"));
                    break;

                case "MIXED LEVEL":
                    mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                            "Grade Cut-Off for A-level: " + school.getGradePSLE() + " Grade Cut-Off for O-level: " + Integer.toString(school.getGradeO()),
                            "Distance: " + school.getDistance(),
                            Double.toString(school.getPublicTime()),
                            Double.toString(school.getDrivingTime()),
                            school.getZoneCode(),
                            school.getTypeCode(),
                            (school.isIp())? "Yes" : "No"));
                    break;

                default:
                    mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                            "Grade Cut-Off: Not Applicable ",
                            "Distance: " + Double.toString(school.getDistance()),
                            Double.toString(school.getPublicTime()),
                            Double.toString(school.getDrivingTime()),
                            school.getZoneCode(),
                            school.getTypeCode(),
                            (school.isIp())? "Yes" : "No"));
                    break;
            }


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
                mSchoolList = mAdapter.getSchoolItemList();
                SchoolItem schoolItem = mSchoolList.get(position);
                Intent intent = new Intent(HomeFragment.super.getContext(), schoolInfoUI.class);
                School school = schools.get(schoolItem.getSchoolName());

                assert school != null;
                String schoolName = school.getSchoolName();
                String course = school.getMainCode();
                Integer grade;
                String url = school.getUrlAddress();
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
                intent.putExtra("url", url);
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
                        mAdapter.notifyItemChanged(position);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onChangeCheckbox(ArrayList<String> region,  ArrayList<String> IP, ArrayList<String> type){
        this.region = region;
        this.IP = IP;
        this.type = IP;
        String message = "REGION";
        String encodedRegion = new String();
        for (String s : region){
            switch(s){
                case "NORTH":
                    encodedRegion = encodedRegion.concat("N");
                    break;
                case "SOUTH":
                    encodedRegion = encodedRegion.concat("S");
                    break;
                case "EAST":
                    encodedRegion = encodedRegion.concat("E");
                    break;
                case "WEST":
                    encodedRegion = encodedRegion.concat("W");
                    break;
            }
        }
        message = message.concat(" "+encodedRegion + " TYPE");
        String encodedType =  new String();
        for (String s : type){
            switch(s){
                case "GOVERNMENT SCHOOL":
                    encodedType=encodedType.concat("G");
                    break;
                case "GOVERNMENT-AIDED SCH":
                    encodedType=encodedType.concat("A");
                    break;
                case "INDEPENDENT SCHOOL":
                    encodedType=encodedType.concat("I");
                    break;
                case "SPECIALISED SCHOOL":
                    encodedType=encodedType.concat("S");
                    break;
                case "SPECIALISED INDEPENDENT SCHOOL":
                    encodedType=encodedType.concat("Z");
                    break;
            }
        }
        message = message.concat(" "+encodedType + " IP");
        String encodedIP = new String();
        for (String s : IP) {
            switch (s) {
                case "Yes":
                    encodedIP=encodedIP.concat("Y");
                    break;
                case "No":
                    encodedIP=encodedIP.concat("N");
                    break;
            }
        }
        message = message.concat(" "+encodedIP);
        mAdapter.getAdvancedFilter().filter(message);
    }

    @Override
    public void onClick(View view) {
        //FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        switch(view.getId()) {
            case R.id.sortButton:
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                SortByDialogFragment sortBy = new SortByDialogFragment();
                sortBy.setDialogFragmentListener(this);
                sortBy.show(ft, "Sort By");
                break;

            case R.id.filterButton:
                FragmentTransaction ft1 = getChildFragmentManager().beginTransaction();
                advFilter.setListener(this);
                advFilter.show(ft1, "Filter");
                break;

            case R.id.searchButton:
        }
    }




    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }
    public ArrayList<SchoolItem> getSchoolItemList() {
        return this.mSchoolList;
    }
    public HashMap<String, School> getSchoolDB() {
        return schools;
    }
    public AdapterClass getAdapter() {
        return this.mAdapter;
    }
    public void setSchoolList(ArrayList<School> schoolList) {
        this.schoolArrayList = schoolList;
    }
    public void setSchoolDB(HashMap<String, School> schools) {this.schools = schools;}
}
