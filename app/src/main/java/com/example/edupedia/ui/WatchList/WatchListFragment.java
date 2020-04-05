package com.example.edupedia.ui.WatchList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edupedia.R;
import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;
import com.example.edupedia.ui.AdapterClass;
import com.example.edupedia.ui.Compare.CompareFragment;
import com.example.edupedia.ui.MainNavigationUI;
import com.example.edupedia.ui.SchoolItem;
import com.example.edupedia.ui.StartUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static com.example.edupedia.controller.WatchlistController.*;

public class WatchListFragment extends Fragment {


    /**
     * ArrayList containing schoolItems for display
     */
    private ArrayList<SchoolItem> wSchoolList;
    /**
     * Recycler View to implement recycler view of displaying schools
     */
    private RecyclerView wRecyclerView;
    /**
     * Adapter to implement recycler view
     */
    private AdapterClass wAdapter;
    /**
     * Layout Manager to implement recycler view
     */
    private RecyclerView.LayoutManager wLayoutManager;
    /**
     * String array for all the school names stored in watchlist
     */
    private String[] watchList;
    /**
     * hashmap for all the schools
     */
    private ArrayList<School> schoolList;
    /**
     * hashmap for all the schools
     */
    private HashMap<String, School> schools;
    /**
     * School database is instantiated as schoolDB
     */
    private SchoolDB schoolDB;

    private MainNavigationUI mainNavigationUI;

    /**
     * view model is an instantiation of the Search Controller
     */
    private WatchlistController watchlistController = WatchlistController.getInstance();

    /**
     * default method that executes upon creation of a watchlist fragment
     */
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        watchList = watchlistController.getWatchlist();
        schoolDB = new SchoolDB(getContext());
        schools = schoolDB.getValue();
        //schools = mainNavigationUI.getSchoolDB();
        if (schools != null) { Log.d("WatchList", String.valueOf(schools.size()));}
        boolean empty = true;
        for (String schoolName : watchList) {
            if (schoolName != null) {
                empty = false;
                break;
            }
        }
        if (empty) {
            wSchoolList = new ArrayList<>();
            Toast toast = Toast.makeText(getActivity(), "None of the schools have been added to the WatchList", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            schoolList = generateSchools(schools, watchList);
            if (schoolList.size() != 0) {
                wSchoolList = new ArrayList<>();
                for (School school : schoolList) {
                    if(school!=null)
                        wSchoolList.add(createSchoolItem(school));
                }
            }
        }
        View layout = inflater.inflate(R.layout.fragment_watchlist, container, false);
        buildRecyclerView(layout);

        return layout;

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainNavigationUI) {
            mainNavigationUI = (MainNavigationUI) context;
        }
    }
    /**
     * method to generate a list of school from the database
     */
    private ArrayList<School> generateSchools(HashMap<String, School> db, String[] results) {
        ArrayList<School> schoolList = new ArrayList<>();
        for (String name : results) {
            schoolList.add(db.get(name));
        }

        return schoolList;
    }
    /**
     *  method that builds a recycler view to display all the school items
     */
    private void buildRecyclerView(View layout) {
        wRecyclerView = layout.findViewById(R.id.recycler_watchList_view);
        wRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        wRecyclerView.setHasFixedSize(false);
        //wLayoutManager = new LinearLayoutManager(getActivity());
        //wRecyclerView.setLayoutManager(wLayoutManager);
        wAdapter = new AdapterClass(wSchoolList);
        //wRecyclerView.setLayoutManager(wLayoutManager);
        wRecyclerView.setAdapter(wAdapter);

        wAdapter.setOnItemClickListener(new AdapterClass.OnItemClickListener() {
            /**
             * method that opens up school information page upon click
             */
            @Override
            public void onItemClick(int position) {
                wSchoolList.get(position).openSchoolInfo();
                wAdapter.notifyItemChanged(position);
            }

            /**
             * method that adds school to the watchlist upon click of the star button
             */
            @Override
            public void onWatchListSelect(int position) {
                String schoolToRemove = wSchoolList.get(position).getSchoolName();
                String[] watchlist = watchlistController.getWatchlist();
                int i = 0;
                while (i < 10) {
                    if (watchlist[i] != null) {
                        if (watchlist[i].equals(schoolToRemove)) {
                            watchlistController.removeSchool(i);
                            wSchoolList.remove(position);
                            wAdapter.notifyItemChanged(position);
                            Toast toast = Toast.makeText(getActivity(), "School has been removed", Toast.LENGTH_LONG);
                            toast.show();
                            break;
                        }
                    }
                    i++;
                }
                watchlistController.pushWatchlist();
                //wAdapter.notifyItemChanged(position);
                //mSchoolList.get(position).addToWatchList();
            }
            /**
             * method that adds school to the compare upon click of the compare button
             */
            @Override
            public void onCompareSelect(int position) {

                String schoolToCompare = wSchoolList.get(position).getSchoolName();
                CompareFragment comparison = new CompareFragment();
                Bundle args = new Bundle();
                args.putString("SchoolName", schoolToCompare);
                comparison.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        comparison).commit();
                Toast toast = Toast.makeText(getActivity(), "School has been added to comparison", Toast.LENGTH_LONG);
                toast.show();
                wAdapter.notifyItemChanged(position);
                //mSchoolList.get(position).addToWatchList();
            }
        });


    }
    /**
     * method that updates information on watchlist fragment if any change occurs
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateInfo() {
        Log.d("WatchListFragment", "There");
        try {
            schools = mainNavigationUI.getSchoolDB();
            ArrayList<SchoolItem> schoolItemList = wAdapter.getSchoolItemList();
            for (SchoolItem schoolItem : schoolItemList) {
                Log.d("WatchListFragment", schoolItem.getDistanceInfo());
            }
            watchList = watchlistController.getWatchlist();
            ArrayList<String> schoolNames = new ArrayList<>();
            for (int i = 0; i < schoolItemList.size(); i++) {//get all the current schools in schoolItemList
                if (schoolItemList.get(i) != null) {
                    schoolNames.add(schoolItemList.get(i).getSchoolName());
                    wAdapter.notifyItemChanged(i);
                    Log.d("WatchListFragment", "Here");
                }
            }
            for (String schoolName : watchList) { //if school in w atchlist but not currently in schoolitem list
                if (!schoolNames.contains(schoolName) && schoolName != null) {
                    schoolItemList.add(createSchoolItem(schools.get(schoolName)));
                }
            }
            wAdapter.notifyDataSetChanged();
        }
        catch (NullPointerException e) {
            return;
        }
    }
    /**
     * method that creates schoolItem from school
     */
    private SchoolItem createSchoolItem(School school) {
        String printGrade;
        int gradePSLE = school.getGradePSLE();
        int gradeO = school.getGradeO();
        if ((gradeO != -1) && (gradePSLE != -1)) {
            printGrade = "\nPSLE: " + gradePSLE + "  /  O-Level: " + gradeO;
        } else if ((gradeO == -1) && (gradePSLE != -1)) {
            printGrade = "\nCut-Off Score: " + gradePSLE + " (PSLE)";
        } else if (gradeO != -1) {
            printGrade = "\nCut-Off Score: " + gradeO + " (O-Level)";
        } else {
            printGrade = "Cut-Off Score: Not Applicable";
        }
        Log.d("WatchListFragment", String.valueOf(school.getDistance()));
        SchoolItem schoolItem = new SchoolItem(
                R.drawable.school_icon,
                school.getSchoolName(),
                printGrade,
                school.getDistance() + " km"
        );

        return schoolItem;
    }

}
