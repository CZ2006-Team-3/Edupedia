package com.example.edupedia.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.edupedia.R;
import com.example.edupedia.controller.SortController;
import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;
import com.example.edupedia.controller.SearchController;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchFragment extends Fragment implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {

    //for debugging purpose
    private static final String TAG = "SearchFragment";

    private SearchController viewModel;
    private TextView textFilterEdLevel, textFilterGradeCutOff, textFilterPrefStream;
    private EditText textFilterLocation;
    private SortController sortController;
    private HashMap<String, School> schools;
    private ArrayList<School> schoolArrayList;
    private SchoolDB schoolDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(SearchController.class);
        View rootview = inflater.inflate(R.layout.fragment_search, container, false);

        schoolDB = new SchoolDB(getContext());
        schools = schoolDB.getValue();

        ///Spinner 1 ////////////////////////////////
        Spinner dropdown_education = (Spinner) rootview.findViewById(R.id.dropdown_education);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");//set default value as null
                }
                ((TextView)v.findViewById(android.R.id.text1)).setTextColor(Color.BLACK);
                textFilterEdLevel = v.findViewById(android.R.id.text1);
                return v;
            }
            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("Primary Level");
        adapter.add("Secondary Level");
        adapter.add("Tertiary Level");
        adapter.add("");

        dropdown_education.setAdapter(adapter);
        String s = viewModel.getTextFilterEdLevel().getValue();  //retrieve from filter.json
        dropdown_education.setSelection((s!=null)? adapter.getPosition(s):adapter.getCount());
        textFilterEdLevel = (TextView) dropdown_education.getSelectedView();


        ///Spinner 2 ////////////////////////////////
        final Spinner dropdown_gradeCut_Off = (Spinner) rootview.findViewById(R.id.gradeCut_Off);
        //ArrayAdapter for Olvl grade
        final ArrayAdapter<CharSequence> adapter2_olvl = new ArrayAdapter<CharSequence>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");//set default value as null
                }
                ((TextView)v.findViewById(android.R.id.text1)).setTextColor(Color.BLACK);
                textFilterGradeCutOff = v.findViewById(android.R.id.text1);
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        adapter2_olvl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(int grade=0; grade<=20; grade++) {
            adapter2_olvl.add(String.valueOf(grade));
        }
        adapter2_olvl.add("");

        //ArrayAdapter for PSLE grade
        final ArrayAdapter<CharSequence> adapter2_psle = new ArrayAdapter<CharSequence>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");//set default value as null
                }
                ((TextView)v.findViewById(android.R.id.text1)).setTextColor(Color.BLACK);
                textFilterGradeCutOff = v.findViewById(android.R.id.text1);
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        adapter2_psle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(int grade=0; grade<=300; grade++) {
            adapter2_psle.add(String.valueOf(grade));
        }
        adapter2_psle.add("");

        dropdown_education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //when Education Level is changed
                TextView tv = (TextView) selectedItemView;
                Log.d(TAG, "ItemSelectedListener activated " + tv.getText().toString());
                int pos;
                String s;
                //Grade Cut Off changes ArrayAdapter between Olvl and PSLE
                switch(tv.getText().toString()){
                    case "Secondary Level":
                        dropdown_gradeCut_Off.setClickable(true);
                        dropdown_gradeCut_Off.setAdapter(adapter2_psle);
                        s = viewModel.getTextFilterGradeCutOff().getValue(); //retrieve from filter.json
                        pos = adapter2_psle.getPosition(s);
                        dropdown_gradeCut_Off.setSelection((s!=null && pos!=-1)? adapter2_psle.getPosition(s):adapter2_psle.getCount());
                        break;
                    case "Tertiary Level":
                        dropdown_gradeCut_Off.setClickable(true);
                        dropdown_gradeCut_Off.setAdapter(adapter2_olvl);
                        s = viewModel.getTextFilterGradeCutOff().getValue(); //retrieve from filter.json
                        pos = adapter2_psle.getPosition(s);
                        dropdown_gradeCut_Off.setSelection((s!=null&&pos!=-1)? adapter2_olvl.getPosition(s):adapter2_olvl.getCount());
                        break;
                    default:
                        dropdown_gradeCut_Off.setClickable(false);
                        ArrayAdapter<CharSequence> emptyAdapter =
                                new ArrayAdapter<>(getActivity().getApplicationContext(),
                                        android.R.layout.simple_spinner_dropdown_item, new CharSequence[] {""});
                        dropdown_gradeCut_Off.setSelection(0);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //if Education Level is changed
                int pos;
                String s;
                switch(textFilterEdLevel.toString()){
                    case "Secondary Level":
                        dropdown_gradeCut_Off.setClickable(true);
                        dropdown_gradeCut_Off.setAdapter(adapter2_psle);
                        s = viewModel.getTextFilterGradeCutOff().getValue(); //retrieve from filter.json
                        pos = adapter2_psle.getPosition(s);
                        dropdown_gradeCut_Off.setSelection((s!=null && pos!=-1)? adapter2_psle.getPosition(s):adapter2_psle.getCount());
                        break;
                    case "Tertiary Level":
                        dropdown_gradeCut_Off.setClickable(true);
                        dropdown_gradeCut_Off.setAdapter(adapter2_olvl);
                        s = viewModel.getTextFilterGradeCutOff().getValue(); //retrieve from filter.json
                        pos = adapter2_psle.getPosition(s);
                        dropdown_gradeCut_Off.setSelection((s!=null&&pos!=-1)? adapter2_olvl.getPosition(s):adapter2_olvl.getCount());
                        break;
                    default:
                        dropdown_gradeCut_Off.setClickable(false);
                        ArrayAdapter<CharSequence> emptyAdapter =
                                new ArrayAdapter<>(getActivity().getApplicationContext(),
                                        android.R.layout.simple_spinner_dropdown_item, new CharSequence[] {""});
                        dropdown_gradeCut_Off.setSelection(0);
                        break;

                }
            }
        });
        textFilterGradeCutOff = (TextView) dropdown_gradeCut_Off.getSelectedView();

        ///Spinner 3 ////////////////////////////////
        Spinner dropdown_preffered_stream = (Spinner) rootview.findViewById(R.id.preferred_Stream);
        ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter<CharSequence>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");//set default value as null
                }
                ((TextView)v.findViewById(android.R.id.text1)).setTextColor(Color.BLACK);
                textFilterPrefStream = v.findViewById(android.R.id.text1);
                return v;
            }
            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.add("Not Applicable");
        adapter3.add("Science Stream");
        adapter3.add("Art Stream");
        adapter3.add("");
        dropdown_preffered_stream.setAdapter(adapter3);
        s = viewModel.getTextFilterPrefStream().getValue(); //retrieve from filter.json
        dropdown_preffered_stream.setSelection((s!=null)? adapter3.getPosition(s):adapter3.getCount());
        textFilterPrefStream = (TextView) dropdown_preffered_stream.getSelectedView();


        ///EditText Location ////////////////////////////////
        textFilterLocation = (EditText) rootview.findViewById(R.id.locationEnter);
        textFilterLocation.setText(viewModel.getTextFilterLocation().getValue());


        ImageButton searchButton = (ImageButton) rootview.findViewById(R.id.findInstitute);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "searchButton is clicked!");
                viewModel.setTextFilterEdLevel(textFilterEdLevel.getText().toString());
                viewModel.setTextFilterGradeCutOff(textFilterGradeCutOff.getText().toString());
                viewModel.setTextFilterPrefStream(textFilterPrefStream.getText().toString());
                viewModel.setTextFilterLocation(textFilterLocation.getText().toString());
                viewModel.onBasicSearch(schools);

            }
        });

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

