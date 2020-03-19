package com.example.edupedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edupedia.AdapterClass;
import com.example.edupedia.R;
import com.example.edupedia.SchoolItem;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList <SchoolItem> mSchoolList;
    private RecyclerView mRecyclerView;
    private AdapterClass mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        createSchoolList();
        buildRecyclerView(layout);
        return layout;
    }

    public void createSchoolList() {
        mSchoolList = new ArrayList<>();
        mSchoolList.add(new SchoolItem(R.drawable.school_icon, "RJC", "2 Points", "4 km"));
        mSchoolList.add(new SchoolItem(R.drawable.school_icon, "RI", "4 Points", "2 km"));
        mSchoolList.add(new SchoolItem(R.drawable.school_icon, "AJC", "6 Points", "3 km"));
        mSchoolList.add(new SchoolItem(R.drawable.school_icon, "AGS", "8 Points", "5 km"));

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
            }
        });


    }


}
