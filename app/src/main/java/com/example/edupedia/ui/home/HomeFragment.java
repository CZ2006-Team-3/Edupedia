package com.example.edupedia.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.edupedia.R;
import com.example.edupedia.ui.RegisterUI;
import com.example.edupedia.viewmodel.SearchController;

public class HomeFragment extends Fragment {

    private SearchController viewModel;
    private TextView textFilterEdLevel, textFilterGradeCutOff, textFilterPrefStream;
    private EditText textFilterLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(SearchController.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        ///Spinner 1 ////////////////////////////////
        Spinner spinner1 = (Spinner) root.findViewById(R.id.textFilterEdLevel);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                textFilterEdLevel = v.findViewById(android.R.id.text1);


                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.add("Primary Level");
        adapter1.add("Secondary Level");
        adapter1.add("Tertiary Level");
        adapter1.add("Select Education Level (Future)"); //This is the text that will be displayed as hint.

        spinner1.setAdapter(adapter1);
        String s = viewModel.getTextFilterEdLevel().getValue();
        if (s == null) {
            Log.d("String From View Model","is null");
        }
        else Log.d("String From View Model:",s);

        spinner1.setSelection((s == null)?  adapter1.getCount() : adapter1.getPosition(s) ); //set the hint the default selection so it appears on launch.
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                textFilterEdLevel = (TextView) view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        ///Spinner 2 ////////////////////////////////
        Spinner spinner2 = (Spinner) root.findViewById(R.id.textFilterGradeCutOff);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                textFilterGradeCutOff = v.findViewById(android.R.id.text1);


                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.add("Primary Level");
        adapter2.add("Secondary Level");
        adapter2.add("Tertiary Level");
        adapter2.add("Select Education Level (Future)"); //This is the text that will be displayed as hint.

        spinner2.setAdapter(adapter2);
        s = viewModel.getTextFilterGradeCutOff().getValue();
        spinner2.setSelection((s == null)?  adapter2.getCount() : adapter2.getPosition(s) ); //set the hint the default selection so it appears on launch.
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                textFilterPrefStream = (TextView) view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        ///Spinner 3 ////////////////////////////////
        Spinner spinner3 = (Spinner) root.findViewById(R.id.textFilterPrefStream);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                textFilterPrefStream = v.findViewById(android.R.id.text1);


                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.add("Primary Level");
        adapter3.add("Secondary Level");
        adapter3.add("Tertiary Level");
        adapter3.add("Select Education Level (Future)"); //This is the text that will be displayed as hint.

        spinner3.setAdapter(adapter3);
        s = viewModel.getTextFilterPrefStream().getValue();
        spinner3.setSelection((s == null)?  adapter3.getCount() : adapter3.getPosition(s) ); //set the hint the default selection so it appears on launch.
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                textFilterPrefStream = (TextView) view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });

        textFilterLocation = (EditText) root.findViewById(R.id.textFilterLocation);
        textFilterLocation.setText(viewModel.getTextFilterLocation().getValue());



        Button searchButton = (Button) root.findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                viewModel.setTextFilterEdLevel(textFilterEdLevel.getText().toString());
                viewModel.setTextFilterGradeCutOff(textFilterGradeCutOff.getText().toString());
                viewModel.setTextFilterPrefStream(textFilterPrefStream.getText().toString());
                viewModel.setTextFilterLocation(textFilterLocation.getText().toString());
                viewModel.onSearch();

            }
        });

        return root;
    }


}
