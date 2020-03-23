package com.example.edupedia.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.edupedia.R;
import com.example.edupedia.controller.GoogleMapsActivity;
import com.example.edupedia.controller.SortController;
import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;
import com.example.edupedia.controller.SearchController;
import com.example.edupedia.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Search Fragment class
 */
public class SearchFragment extends Fragment implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {

    //for debugging purpose
    private static final String TAG = "SearchFragment";

    /**
     * view model is an instantiation of the Search Controller
     */
    private SearchController viewModel;
    /**
     * textFilterEdLevel is the text view for education level filter
     * textFilterGradeCutOff is the text view for the grade cut off filter
     * textFilterNature is the text view for the nature of the school filter
     * textFilterLocation is the text view for the location filter
     */
    private TextView textFilterEdLevel, textFilterGradeCutOff, textFilterNature, textFilterLocation;
    /**
     *spinner for the user to select their grade cut off
     */
    private Spinner dropdown_gradeCut_Off;
    /**
     * hashmap for all the schools
     */
    private HashMap<String, School> schools;
    /**
     * School database is instantiated as schools
     */
    private SchoolDB schoolDB;
    /**
     * request code is set to 0
     */
    private static final int REQUEST_CODE = 0;
    /**
     * result ok is set to -1
     */
    public static final int RESULT_OK = -1;
    /**
     * results cancelled is set to 0
     */
    public static final int RESULT_CANCELED = 0;


    @Nullable
    @Override
    /**
     * default method that occurs upon the creation of the activity
     */
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
                Log.d(TAG,  "at spinner 1 " + textFilterEdLevel.getText().toString());
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
        dropdown_education.setSelection((s!=null)? adapter.getPosition(s): adapter.getCount());
        textFilterEdLevel = (TextView) dropdown_education.getSelectedView();


        ///Spinner 2 ////////////////////////////////
        dropdown_gradeCut_Off = (Spinner) rootview.findViewById(R.id.gradeCut_Off);
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
                Log.d(TAG, " at spinner 2 olvl " + textFilterGradeCutOff.getText().toString());
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
                Log.d(TAG, "at spinner 2 psle " + textFilterGradeCutOff.getText().toString());
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
                                new ArrayAdapter<CharSequence>(getActivity().getApplicationContext(),
                                        android.R.layout.simple_spinner_dropdown_item, new CharSequence[] {""}) {

                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {

                                        View v = super.getView(position, convertView, parent);
                                        if (position == 0) {
                                            ((TextView)v.findViewById(android.R.id.text1)).setText("");//set default value as null
                                            ((TextView)v.findViewById(android.R.id.text1)).setHint("Not Applicable");
                                        }
                                        ((TextView)v.findViewById(android.R.id.text1)).setTextColor(Color.BLACK);
                                        textFilterGradeCutOff = v.findViewById(android.R.id.text1);
                                        Log.d(TAG, "at spinner 2 primary " + textFilterGradeCutOff.getText().toString());
                                        return v;
                                    }
                                };
                        dropdown_gradeCut_Off.setAdapter(emptyAdapter);
                        dropdown_gradeCut_Off.setSelection(0);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        textFilterGradeCutOff = (TextView) dropdown_gradeCut_Off.getSelectedView();

        ///Spinner 3 ////////////////////////////////
        Spinner dropdown_preffered_stream = (Spinner) rootview.findViewById(R.id.nature);
        ArrayAdapter<CharSequence> adapter3 = new ArrayAdapter<CharSequence>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");//set default value as null
                }
                ((TextView)v.findViewById(android.R.id.text1)).setTextColor(Color.BLACK);
                textFilterNature = v.findViewById(android.R.id.text1);
                return v;
            }
            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.add("BOYS' SCHOOL");
        adapter3.add("GIRLS' SCHOOL");
        adapter3.add("CO-ED SCHOOL");
        adapter3.add("");
        dropdown_preffered_stream.setAdapter(adapter3);
        s = viewModel.getTextFilterNature().getValue(); //retrieve from filter.json
        dropdown_preffered_stream.setSelection((s!=null)? adapter3.getPosition(s):adapter3.getCount());
        textFilterNature = (TextView) dropdown_preffered_stream.getSelectedView();


        textFilterLocation = (TextView) rootview.findViewById(R.id.locationEnter);
        ///Click on Location button brings you to map view
        //Starts GoogleMapsActivity-> GoogleMapsActivity returns a result to be displayed in text views
        Button button = rootview.findViewById(R.id.locationButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), GoogleMapsActivity.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });


        ImageButton searchButton = (ImageButton) rootview.findViewById(R.id.findInstitute);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "searchButton is clicked!");
                Log.d(TAG, "textFilterEdLevel " + textFilterEdLevel.getText().toString());
                Log.d(TAG, "textFilterGradeCutOff " + textFilterGradeCutOff.getText().toString());
                Log.d(TAG, "textFilterNature " + textFilterNature.getText().toString());
                Log.d(TAG, "textFilterLocation " + textFilterLocation.getText().toString());
                viewModel.setTextFilterEdLevel(textFilterEdLevel.getText().toString());
                viewModel.setTextFilterGradeCutOff(textFilterGradeCutOff.getText().toString());
                viewModel.setTextFilterNature(textFilterNature.getText().toString());
                viewModel.setTextFilterLocation(textFilterLocation.getText().toString());
                viewModel.storeFilterSettings();
                //Filters applied and School Names are returned
                ArrayList<String> results = viewModel.onBasicSearch(schools);
                //Store List of School Names
                viewModel.storeResults(results);
                //shift Fragment here
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, homeFragment).commit();
            }
        });

        return rootview;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("Address");
                //Toast.makeText()
                Log.d("Address:", result);
                textFilterLocation.setText(result);

            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
                Log.d("There is nothing!", "NOTHING");
            }
        }
    }//onActivityResult

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