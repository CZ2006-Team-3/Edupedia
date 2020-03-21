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

public class FilterUI extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_by);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width*0.8), (int) (height*0.6));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        // initiate views
        CheckBox checkNorth = (CheckBox) findViewById(R.id.checkBoxNorth);
        checkNorth.setOnClickListener(this);
        CheckBox checkCentral = (CheckBox) findViewById(R.id.checkBoxCentral);
        checkCentral.setOnClickListener(this);
        CheckBox checkEast = (CheckBox) findViewById(R.id.checkBoxEast);
        checkEast.setOnClickListener(this);
        CheckBox checkNorthEast = (CheckBox) findViewById(R.id.checkBoxNorthEast);
        checkNorthEast.setOnClickListener(this);
        CheckBox checkWest = (CheckBox) findViewById(R.id.checkBoxWest);
        checkWest.setOnClickListener(this);

        CheckBox checkPhysicalAid = (CheckBox) findViewById(R.id.checkBoxPhysicalAid);
        checkPhysicalAid.setOnClickListener(this);
        CheckBox checkBoysOnly = (CheckBox) findViewById(R.id.checkBoxBoysOnly);
        checkBoysOnly.setOnClickListener(this);
        CheckBox checkGirlsOnly = (CheckBox) findViewById(R.id.checkBoxGirlsOnly);
        checkGirlsOnly.setOnClickListener(this);
        CheckBox checkCoed = (CheckBox) findViewById(R.id.checkBoxCoed);
        checkCoed.setOnClickListener(this);
        CheckBox checkGovernment = (CheckBox) findViewById(R.id.checkBoxGovernment);
        checkGovernment.setOnClickListener(this);

        CheckBox checkHearingLoss = (CheckBox) findViewById(R.id.checkBoxHearingLoss);
        checkHearingLoss.setOnClickListener(this);
        CheckBox checkIndependent = (CheckBox) findViewById(R.id.checkBoxIndependent);
        checkIndependent.setOnClickListener(this);
        CheckBox checkMentalDisorder = (CheckBox) findViewById(R.id.checkBoxMentalDisorder);
        checkMentalDisorder.setOnClickListener(this);
        CheckBox checkSpecialised = (CheckBox) findViewById(R.id.checkBoxSpecialised);
        checkSpecialised.setOnClickListener(this);
        CheckBox checkVisualImpairment = (CheckBox) findViewById(R.id.checkBoxVisualImpairment);
        checkVisualImpairment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}