package com.example.edupedia.ui;

import android.app.Activity;
import android.app.assist.AssistStructure;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.edupedia.R;

/**
 * Class FilterUI is a pop up activity which can be called by Home fragment
 */
public class FilterUI extends Activity implements View.OnClickListener {
    @Override
    /**
     * onCreate method creates the pop up window and instantiates all the check boxes in filter.xml
     */
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * Setting the dimensions for the pop up
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_by);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*0.8), (int) (height*0.6));


        /**
         * instatiates all the check boxes
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        // initiate views
        CheckBox checkNorth = (CheckBox) findViewById(R.id.checkBoxNorth);
        checkNorth.setOnClickListener(this);
        CheckBox checkEast = (CheckBox) findViewById(R.id.checkBoxEast);
        checkEast.setOnClickListener(this);
        CheckBox checkWest = (CheckBox) findViewById(R.id.checkBoxWest);
        checkWest.setOnClickListener(this);
        CheckBox checkSouth = (CheckBox) findViewById(R.id.checkBoxSouth);
        checkWest.setOnClickListener(this);

        CheckBox checkIpNo = (CheckBox) findViewById(R.id.checkBoxIPNo);
        checkIpNo.setOnClickListener(this);
        CheckBox checkIpYes = (CheckBox) findViewById(R.id.checkBoxIPYes);
        checkIpYes.setOnClickListener(this);

        CheckBox checkGovtAid = (CheckBox) findViewById(R.id.checkBoxGovtAid);
        checkGovtAid.setOnClickListener(this);
        CheckBox checkSpecInd = (CheckBox) findViewById(R.id.checkBoxSpecInd);
        checkSpecInd.setOnClickListener(this);
        CheckBox checkGovernment = (CheckBox) findViewById(R.id.checkBoxGovernment);
        checkGovernment.setOnClickListener(this);
        CheckBox checkSpecialised = (CheckBox) findViewById(R.id.checkBoxSpecialised);
        checkSpecialised.setOnClickListener(this);
        CheckBox checkIndependent = (CheckBox) findViewById(R.id.checkBoxIndependent);
        checkIndependent.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}