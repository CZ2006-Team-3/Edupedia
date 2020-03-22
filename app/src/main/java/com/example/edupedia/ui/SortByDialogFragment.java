package com.example.edupedia.ui;

import android.app.Dialog;
import android.os.Bundle;


import android.util.Log;
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

    private String TAG = "SortByDialogFragment";
    private int SORT_VARIABLE = SortController.NAME;
    private boolean SORT_ASCENDING = true;
    private SortByDialogListener listener;

    public interface SortByDialogListener {
        void onChangeDialog(int sort_variable, boolean sort_ascending);
    }

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
                        break;

                    case R.id.radioDrivingTime:
                        SORT_VARIABLE = SortController.DRIVING_TIME;
                        break;

                    case R.id.radioName:
                        SORT_VARIABLE = SortController.NAME;
                        break;

                    case R.id.radioPT:
                        SORT_VARIABLE = SortController.TRANSPORT_TIME;
                        break;

                    default:
                        SORT_VARIABLE = SortController.NAME;
                        break;
                }
                listener.onChangeDialog(SORT_VARIABLE, SORT_ASCENDING);
                Log.d(TAG, Integer.toString(SORT_VARIABLE));
            }
        });

        RadioGroup sortOrderBy = layout.findViewById(R.id.radioOrder);
        sortOrderBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            /**
             * onCheckedChanged returns the checkedId of the radio button that the user selects
             */
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                SORT_ASCENDING = checkedId == R.id.radioscending;
                listener.onChangeDialog(SORT_VARIABLE, SORT_ASCENDING);
                Log.d(TAG, Boolean.toString(SORT_ASCENDING));
            }
        });

        return layout;
    }

    public void setDialogFragmentListener(SortByDialogListener listener) {
        this.listener = listener;
    }

}