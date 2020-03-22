package com.example.edupedia.ui;

import android.app.Dialog;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.edupedia.R;
import com.example.edupedia.controller.SortController;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortByDialogFragment extends DialogFragment {


    private int SORT_VARIABLE;
    private boolean SORT_ASCENDING;

    public SortByDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.sort_by, container, false);
        RadioGroup sortBy = layout.findViewById(R.id.sortBy);

        sortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            /**
             * onCheckedChanged returns the checkedId of the radio button that the user selects
             */
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.radioDist:
                        SORT_VARIABLE = SortController.DIST;

                    case R.id.radioDrivingTime:
                        SORT_VARIABLE = SortController.DRIVING_TIME;

                    case R.id.radioName:
                        SORT_VARIABLE = SortController.NAME;

                    case R.id.radioPT:
                        SORT_VARIABLE = SortController.TRANSPORT_TIME;

                    default:
                        SORT_VARIABLE = SortController.NAME;
                }
            }
        });

        RadioGroup sortOrderBy = layout.findViewById(R.id.radioOrder);
        sortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            /**
             * onCheckedChanged returns the checkedId of the radio button that the user selects
             */
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                SORT_ASCENDING = checkedId == R.id.radioAsc;
            }
        });

        return layout;
    }



}
