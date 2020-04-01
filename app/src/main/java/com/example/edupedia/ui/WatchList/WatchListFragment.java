package com.example.edupedia.ui.WatchList;

import android.content.SharedPreferences;
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
import com.example.edupedia.ui.Compare.CompareFragment;
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

    private WatchListViewModel WatchListViewModel;

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
        schoolDB = new SchoolDB(getContext());
        schools = schoolDB.getValue();
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

    private ArrayList<School> generateSchools(HashMap<String, School> db, String[] results) {
        ArrayList<School> schoolList = new ArrayList<>();
        for (String name : results) {
            schoolList.add(db.get(name));
        }

        return schoolList;
    }

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
            @Override
            public void onItemClick(int position) {
                wSchoolList.get(position).openSchoolInfo();
                wAdapter.notifyItemChanged(position);

            }

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

    public void updateInfo() {
        ArrayList<SchoolItem> schoolItemList = wAdapter.getSchoolItemList();
        watchList = watchlistController.getWatchlist();
        ArrayList<String> schoolNames = new ArrayList<>();
        for(SchoolItem schoolItem : schoolItemList) {
            if(schoolItem!=null)
                schoolNames.add(schoolItem.getSchoolName());
        }
        for(String schoolName : watchList) {
            if(!schoolNames.contains(schoolName) && schoolName!=null) {
                schoolItemList.add(createSchoolItem(schools.get(schoolName)));
            }
        }

        wAdapter.notifyDataSetChanged();
    }

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
        SchoolItem schoolItem = new SchoolItem(
                R.drawable.school_icon,
                school.getSchoolName(),
                printGrade,
                "Distance: " + Double.toString(school.getDistance())
        );

        return schoolItem;
    }

}
