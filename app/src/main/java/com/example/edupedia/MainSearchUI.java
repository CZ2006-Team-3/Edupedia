package com.example.edupedia;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;

public class MainSearchUI extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search_ui);

        Spinner dropdown_education = (Spinner) findViewById(R.id.dropdown_education);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.educationLevel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_education.setAdapter(adapter);
        dropdown_education.setOnItemSelectedListener(this);

        Spinner dropdown_gradeCut_Off = (Spinner) findViewById(R.id.gradeCut_Off);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.gradeCutOff, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_gradeCut_Off.setAdapter(adapter2);
        dropdown_gradeCut_Off.setOnItemSelectedListener(this);

        Spinner dropdown_preffered_stream = (Spinner) findViewById(R.id.preferred_Stream);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.preferredStream, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdown_preffered_stream.setAdapter(adapter3);
        dropdown_preffered_stream.setOnItemSelectedListener(this);

    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String text = parent.getItemAtPosition(pos).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent){

    }

}
