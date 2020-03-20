package com.example.edupedia.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.edupedia.R;

public class SearchFragment extends Fragment implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_search, container, false);

        Spinner dropdown_education = (Spinner) rootview.findViewById(R.id.dropdown_education);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.educationLevel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_education.setAdapter(adapter);
        dropdown_education.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Spinner dropdown_gradeCut_Off = (Spinner) rootview.findViewById(R.id.gradeCut_Off);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.gradeCutOff, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown_gradeCut_Off.setAdapter(adapter2);
        dropdown_gradeCut_Off.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Spinner dropdown_preffered_stream = (Spinner) rootview.findViewById(R.id.preferred_Stream);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.preferredStream, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdown_preffered_stream.setAdapter(adapter3);
        dropdown_preffered_stream.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        return rootview;
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String text = parent.getItemAtPosition(pos).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {

    }
}

