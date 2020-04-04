package com.example.edupedia.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edupedia.controller.SearchController;
import com.example.edupedia.ui.AdapterClass;
import com.example.edupedia.R;
import com.example.edupedia.ui.AdvFilterDialogFragment;
import com.example.edupedia.ui.Compare.CompareFragment;
import com.example.edupedia.ui.MainNavigationUI;
import com.example.edupedia.ui.SchoolItem;
import com.example.edupedia.controller.SortController;
import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;
import com.example.edupedia.ui.SortByDialogFragment;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Home Fragment class - all the functions on home page
 */
public class HomeFragment extends Fragment implements SortByDialogFragment.SortByDialogListener,
        View.OnClickListener, AdvFilterDialogFragment.AdvFilterDialogListener {

    private String TAG = "HomeFragment";

    public interface SortEventListener {
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
    //private SortController sortController;
    private SchoolDB schoolDB;
    private HashMap<String, School> schools;
    private ArrayList<School> schoolArrayList;

    private MainNavigationUI mainNavigationUI;
    private WatchlistController watchlistController = WatchlistController.getInstance();
    private AdvFilterDialogFragment advFilter = new AdvFilterDialogFragment();
    private SortByDialogFragment sortByFragment = new SortByDialogFragment();
    private SearchController searchController;

    /**
     * default method that occurs upon the creation of the activity
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("HomeFragment", "Here");
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        searchController = new ViewModelProvider(this).get(SearchController.class);
        ImageButton toSort = (ImageButton) layout.findViewById(R.id.sortButton);
        ImageButton filter = (ImageButton) layout.findViewById(R.id.filterButton);
        SearchView searchButton = (SearchView) layout.findViewById(R.id.searchButton);
        schoolDB = new SchoolDB(getContext());
        schools = schoolDB.getValue();
        //Log.d("SchDB", "Loaded");
        //retrieving results from background files
        ArrayList<String> results = searchController.retrieveResults();
        Log.d("HomeFragment", "Here");
        if (results != null)
            Log.d("HomeFragment", String.valueOf(results.size()));
        //gets a list of schools based on a string of school names
        schoolArrayList = searchController.generateSchools(schools, results);
        Toast.makeText(getContext(), "Home Fragment", Toast.LENGTH_SHORT);
        //schoolArrayList = searchController.getDistances(schoolArrayList);
        toSort.setOnClickListener(this);
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
        return layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateInfo() {
        ArrayList<String> results = searchController.retrieveResults();
        Log.d("HomeFragment", "Here");
        if (results != null)
            Log.d("HomeFragment", String.valueOf(results.size()));
        schoolArrayList = searchController.generateSchools(schools, results);
        createSchoolList();
        mAdapter.setSchoolItemList(mSchoolList);
        mAdapter.notifyDataSetChanged();
    }

    /**
     *  method that creates a list of school to be displayed from the school class and database
     */
    public void createSchoolList() {
        mSchoolList = new ArrayList<>();
        for (School school : schoolArrayList) {
            switch (school.getMainCode()) {
                case "SECONDARY":
                    mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                            "Cut-Off Grade Score: " + school.getGradePSLE() + " (PSLE)",
                            "Distance: " + Double.toString(school.getDistance()) + " km",
                            Double.toString(school.getPublicTime()),
                            Double.toString(school.getDrivingTime()),
                            school.getZoneCode(),
                            school.getTypeCode(),
                            (school.isIp()) ? "Yes" : "No"));
                    break;

                case "JUNIOR COLLEGE":
                    mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                            "Cut-Off Score: " + school.getGradeO() + " (O-Level)",
                            "Distance: " + Double.toString(school.getDistance()) + " km",
                            Double.toString(school.getPublicTime()),
                            Double.toString(school.getDrivingTime()),
                            school.getZoneCode(),
                            school.getTypeCode(),
                            (school.isIp()) ? "Yes" : "No"));
                    break;

                case "MIXED LEVEL":
                    int gradeCheckPSLE = school.getGradePSLE();
                    int gradeCheckO = school.getGradeO();
                    mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                            "PSLE: " + (gradeCheckPSLE == -1 ? "Not Applicable" : gradeCheckPSLE)  + "\nO-Level: " + (gradeCheckO == -1 ? "Not Applicable" : gradeCheckO),
                            "Distance: " + Double.toString(school.getDistance()) + " km",
                            Double.toString(school.getPublicTime()),
                            Double.toString(school.getDrivingTime()),
                            school.getZoneCode(),
                            school.getTypeCode(),
                            (school.isIp()) ? "Yes" : "No"));
                    break;

                default:
                    mSchoolList.add(new SchoolItem(R.drawable.school_icon, school.getSchoolName(),
                            "Cut-Off Score: Not Applicable",
                            "Distance: " + Double.toString(school.getDistance()) + " km",
                            Double.toString(school.getPublicTime()),
                            Double.toString(school.getDrivingTime()),
                            school.getZoneCode(),
                            school.getTypeCode(),
                            (school.isIp()) ? "Yes" : "No"));
                    break;
            }
            if (watchlistController.exists(school.getSchoolName())){

            }
        }
    }

    /**
     *  method that builds a recycler view to display all the school items
     */
    public void buildRecyclerView(View layout) {

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
                /**
                 * method that opens up school information page upon click
                 */
                mSchoolList = mAdapter.getSchoolItemList();
                SchoolItem schoolItem = mSchoolList.get(position);
                Intent intent = new Intent(HomeFragment.super.getContext(), schoolInfoUI.class);
                School school = schools.get(schoolItem.getSchoolName());

                assert school != null;
                String schoolName = school.getSchoolName();
                String course = school.getMainCode();
                String printGrade;
                String url = school.getUrlAddress();

                int gradePSLE = school.getGradePSLE();
                int gradeO = school.getGradeO();
                if ((gradeO!= -1) && (gradePSLE != -1)) {
                    printGrade = "PSLE: " + gradePSLE + "\nO-Level: " + gradeO;
                } else if ((gradeO == -1) && (gradePSLE != -1)) {
                    printGrade = "PSLE: " + gradePSLE;
                } else if (gradeO != -1) {
                    printGrade = "O-Level: " + gradeO;
                } else {
                    printGrade = "Not Applicable";
                }

                Double drive = school.getDrivingTime();
                Double dist = school.getDistance();
                Double publicTime = school.getPublicTime();
                intent.putExtra("schoolName", schoolName);
                intent.putExtra("course", course);
                intent.putExtra("grade", printGrade);
                intent.putExtra("drive", drive);
                intent.putExtra("dist", dist);
                intent.putExtra("publicTime", publicTime);
                intent.putExtra("url", url);
                startActivity(intent);
            }

            /**
             * method that adds school to the watchlist upon click of the star button
             */
            @Override
            public void onWatchListSelect(int position) {
                String schoolToAdd = mSchoolList.get(position).getSchoolName();
                if (watchlistController.exists(schoolToAdd)) {
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
//                        mainNavigationUI.watchlistFragment.updateInfo(schools.get(schoolToAdd));
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
            /**
             * method that adds school to the compare upon click of the compare button
             */
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCompareSelect(int position) {
                String schoolToCompare = mSchoolList.get(position).getSchoolName();
                CompareFragment comparison = (CompareFragment) mainNavigationUI.compareFragment;
                Bundle args = new Bundle();
                args.putString("SchoolName", schoolToCompare);
                comparison.setArguments(args);
                comparison.update(comparison.getView());
                FragmentTransaction ft = mainNavigationUI.fm.beginTransaction().hide(mainNavigationUI.currentFragment).show(comparison);
                mainNavigationUI.currentFragment = comparison;
                ft.commit();
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
        mAdapter.onRequestSort(sort_variable, sort_ascending);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onChangeCheckbox(ArrayList<String> region, ArrayList<String> IP, ArrayList<String> type) {
        this.region = region;
        this.IP = IP;
        this.type = IP;
        String message = "REGION";
        String encodedRegion = new String();
        for (String s : region) {
            switch (s) {
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
        message = message.concat(" " + encodedRegion + " TYPE");
        String encodedType = new String();
        for (String s : type) {
            switch (s) {
                case "GOVERNMENT SCHOOL":
                    encodedType = encodedType.concat("G");
                    break;
                case "GOVERNMENT-AIDED SCH":
                    encodedType = encodedType.concat("A");
                    break;
                case "INDEPENDENT SCHOOL":
                    encodedType = encodedType.concat("I");
                    break;
                case "SPECIALISED SCHOOL":
                    encodedType = encodedType.concat("S");
                    break;
                case "SPECIALISED INDEPENDENT SCHOOL":
                    encodedType = encodedType.concat("Z");
                    break;
            }
        }
        message = message.concat(" " + encodedType + " IP");
        String encodedIP = new String();
        for (String s : IP) {
            switch (s) {
                case "Yes":
                    encodedIP = encodedIP.concat("Y");
                    break;
                case "No":
                    encodedIP = encodedIP.concat("N");
                    break;
            }
        }
        message = message.concat(" " + encodedIP);
        mAdapter.getAdvancedFilter().filter(message);
    }

    @Override
    public void onClick(View view) {
        //FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.sortButton:
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                sortByFragment.setDialogFragmentListener(this);
                sortByFragment.show(ft, "Sort By");
                break;

            case R.id.filterButton:
                FragmentTransaction ft1 = getChildFragmentManager().beginTransaction();
                advFilter.setListener(this);
                advFilter.show(ft1, "Filter");
                break;

            case R.id.searchButton:
        }
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

   /* public void setSchoolList(ArrayList<School> schoolList) {
        this.schoolArrayList = schoolList;
    } */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSchoolDB(HashMap<String, School> schools) {
        this.schools = schools;
        mainNavigationUI.setSchoolDB(schools);
    }

    public int getSORT_VARIABLE() {
        return SORT_VARIABLE;
    }

    public boolean isSORT_ASCENDING() {
        return SORT_ASCENDING;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainNavigationUI) {
            mainNavigationUI = (MainNavigationUI) context;
        }
    }
}
