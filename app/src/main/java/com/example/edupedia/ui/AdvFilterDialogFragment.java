package com.example.edupedia.ui;

import android.app.Dialog;
import android.os.Bundle;


import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.edupedia.R;
import com.example.edupedia.controller.SortController;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvFilterDialogFragment extends DialogFragment {
    private ArrayList<String> region;

    private String TAG = "SortByDialogFragment";
    private int SORT_VARIABLE = SortController.NAME;
    private boolean SORT_ASCENDING = true;
    private SortByDialogListener listener;

    public interface SortByDialogListener {
        void onChangeDialog(int sort_variable, boolean sort_ascending);
    }

    public AdvFilterDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.filter, container, false);


        CheckBox checkNorth = (CheckBox) layout.findViewById(R.id.checkBoxNorth);
        checkNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked)
                    region.add("NORTH");
                else if (region.contains("NORTH"))
                    region.remove()
            }
        });
        CheckBox checkEast = (CheckBox) layout.findViewById(R.id.checkBoxEast);
        checkEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                region = "EAST";
            }
        });
        CheckBox checkWest = (CheckBox) layout.findViewById(R.id.checkBoxWest);
        checkWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                region = "WEST";
            }
        });
        CheckBox checkSouth = (CheckBox) layout.findViewById(R.id.checkBoxSouth);
        checkSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                region = "SOUTH";
            }
        });

        CheckBox checkIpNo = (CheckBox) layout.findViewById(R.id.checkBoxIPNo);
        checkIpNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CheckBox checkIpYes = (CheckBox) layout.findViewById(R.id.checkBoxIPYes);
        checkIpYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        CheckBox checkGovtAid = (CheckBox) layout.findViewById(R.id.checkBoxGovtAid);
        checkGovtAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CheckBox checkSpecInd = (CheckBox) layout.findViewById(R.id.checkBoxSpecInd);
        checkSpecInd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CheckBox checkGovernment = (CheckBox) layout.findViewById(R.id.checkBoxGovernment);
        checkGovernment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CheckBox checkSpecialised = (CheckBox) layout.findViewById(R.id.checkBoxSpecialised);
        checkSpecialised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CheckBox checkIndependent = (CheckBox) layout.findViewById(R.id.checkBoxIndependent);
        checkIndependent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return layout;

    }

}