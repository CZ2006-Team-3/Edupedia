package com.example.edupedia.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edupedia.R;
import com.example.edupedia.controller.SortController;
import com.example.edupedia.ui.home.HomeFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Class SortBy is a pop up activity which can be called by Home fragment
 */
public class SortBy extends Activity {
    private int sort_variable = SortController.NAME;
    private boolean sort_ascending = true;

    @Override
    /**
     * onCreate method creates the pop up window and instantiates all the radio buttons in sort_by.xml
     */
    protected void onCreate(Bundle savedInstanceState){
        //to show the pop up:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_by);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*0.8), (int) (height*0.6));

        //for selecting radio buttons
        RadioGroup sortBy = (RadioGroup) findViewById(R.id.sortBy);
        sortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            /**
             * onCheckedChanged returns the checkedId of the radio button that the user selects
             */
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
            }
        });

        RadioGroup sortOrderBy = (RadioGroup) findViewById(R.id.radioOrder);
        sortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            /**
             * onCheckedChanged returns the checkedId of the radio button that the user selects
             */
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
            }
        });
    }

    @Override
    /**
     * Once the pop up window is exited, the HomeFragment is sorted accordingly using setResult
     */
    protected void onDestroy() {
        super.onDestroy();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(HomeFragment.SORT_VARIABLE_NAME, sort_variable);
        returnIntent.putExtra(HomeFragment.ASCENDING_SORT, sort_ascending);
        setResult(HomeFragment.RESULT_SUCCESS, returnIntent);
        finish();
    }

}