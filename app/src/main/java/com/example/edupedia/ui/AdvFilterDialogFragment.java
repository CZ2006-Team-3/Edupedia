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
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvFilterDialogFragment extends DialogFragment {
    private ArrayList<String> region = new ArrayList<>(
            Arrays.asList("NORTH",
                    "SOUTH", "EAST", "WEST")
    );
    private ArrayList<String> IP = new ArrayList<>(
            Arrays.asList("Yes","No")
    );
    private ArrayList<String> type = new ArrayList<>(
            Arrays.asList("GOVERNMENT-AIDED SCH","SPECIALISED INDEPENDENT SCHOOL","GOVERNMENT SCHOOL","SPECIALISED SCHOOL","INDEPENDENT SCHOOL")
    );

    private String TAG = "SortByDialogFragment";
    private int SORT_VARIABLE = SortController.NAME;
    private boolean SORT_ASCENDING = true;

    public void setListener(AdvFilterDialogListener listener) {
        this.listener = listener;
    }

    private AdvFilterDialogListener listener;

    public interface AdvFilterDialogListener {
        void onChangeCheckbox(ArrayList<String> region,  ArrayList<String> IP, ArrayList<String> type);
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
                if (checked && !region.contains("NORTH"))
                    region.add("NORTH");
                else if (region.contains("NORTH"))
                    region.remove("NORTH");
                listener.onChangeCheckbox(region, IP, type);
            }
        });
        CheckBox checkEast = (CheckBox) layout.findViewById(R.id.checkBoxEast);
        checkEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !region.contains("EAST"))
                    region.remove("EAST");
                else if (region.contains("EAST"))
                    region.remove("EAST");
                listener.onChangeCheckbox(region, IP, type);
            }
        });
        CheckBox checkWest = (CheckBox) layout.findViewById(R.id.checkBoxWest);
        checkWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !region.contains("WEST"))
                    region.remove("WEST");
                else if (region.contains("WEST"))
                    region.remove("WEST");
                listener.onChangeCheckbox(region, IP, type);
            }
        });
        CheckBox checkSouth = (CheckBox) layout.findViewById(R.id.checkBoxSouth);
        checkSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !region.contains("SOUTH"))
                    region.remove("SOUTH");
                else if (region.contains("SOUTH"))
                    region.remove("SOUTH");
                listener.onChangeCheckbox(region, IP, type);
            }
        });

        CheckBox checkIpNo = (CheckBox) layout.findViewById(R.id.checkBoxIPNo);
        checkIpNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !IP.contains("Yes"))
                    IP.add("Yes");
                else if (IP.contains("Yes")){
                    IP.remove("Yes");
                    listener.onChangeCheckbox(region, IP, type);
                }
            }
        });
        CheckBox checkIpYes = (CheckBox) layout.findViewById(R.id.checkBoxIPYes);
        checkIpYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !IP.contains("No"))
                    IP.add("No");
                else if (IP.contains("No")){
                    IP.remove("No");
                }
                listener.onChangeCheckbox(region, IP, type);
            }
        });

        CheckBox checkGovtAid = (CheckBox) layout.findViewById(R.id.checkBoxGovtAid);
        checkGovtAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !type.contains("GOVERNMENT-AIDED SCH"))
                    type.add("GOVERNMENT-AIDED SCH");
                else if (type.contains("GOVERNMENT-AIDED SCH")){
                    type.remove("GOVERNMENT-AIDED SCH");
                }
                listener.onChangeCheckbox(region, IP, type);
            }
        });
        CheckBox checkSpecInd = (CheckBox) layout.findViewById(R.id.checkBoxSpecInd);
        checkSpecInd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !type.contains("SPECIALISED INDEPENDENT SCHOOL"))
                    type.add("SPECIALISED INDEPENDENT SCHOOL");
                else if (type.contains("SPECIALISED INDEPENDENT SCHOOL")){
                    type.remove("SPECIALISED INDEPENDENT SCHOOL");
                }
                listener.onChangeCheckbox(region, IP, type);

            }
        });
        CheckBox checkGovernment = (CheckBox) layout.findViewById(R.id.checkBoxGovernment);
        checkGovernment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !type.contains("GOVERNMENT SCHOOL"))
                    type.add("GOVERNMENT SCHOOL");
                else if (type.contains("GOVERNMENT SCHOOL")){
                    type.remove("GOVERNMENT SCHOOL");
                }
                listener.onChangeCheckbox(region, IP, type);
            }
        });
        CheckBox checkSpecialised = (CheckBox) layout.findViewById(R.id.checkBoxSpecialised);
        checkSpecialised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !type.add("SPECIALISED SCHOOL"))
                    type.add("SPECIALISED SCHOOL");
                else if (type.contains("SPECIALISED SCHOOL")){
                    type.remove("SPECIALISED SCHOOL");
                }
                listener.onChangeCheckbox(region, IP, type);
            }
        });
        CheckBox checkIndependent = (CheckBox) layout.findViewById(R.id.checkBoxIndependent);
        checkIndependent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked && !type.add("INDEPENDENT SCHOOL"))
                    type.add("INDEPENDENT SCHOOL");
                else if (type.contains("INDEPENDENT SCHOOL")){
                    type.remove("INDEPENDENT SCHOOL");
                }
                listener.onChangeCheckbox(region, IP, type);
            }
        });


        return layout;

    }

}