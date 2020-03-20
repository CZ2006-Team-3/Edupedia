package com.example.edupedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.edupedia.ui.FrontPageUI;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.fragment_settings, container, false);

        ImageButton changePassword=(ImageButton) rootview.findViewById(R.id.ChangePasswordButton);
        ImageButton changeUsername=(ImageButton) rootview.findViewById(R.id.ChangeUsernameButton);
        ImageButton logOut=(ImageButton) rootview.findViewById(R.id.LogOutButton);

        changeUsername.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ChangeUsername.class);
                SettingsFragment.this.startActivity(myIntent);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(v.getContext(), ChangePassword.class);
                SettingsFragment.this.startActivity(myIntent2);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent3 = new Intent(v.getContext(), FrontPageUI.class);
                SettingsFragment.this.startActivity(myIntent3);
            }
        });



        return rootview;
    }

}