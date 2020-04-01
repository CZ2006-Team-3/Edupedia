package com.example.edupedia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.edupedia.R;
import com.example.edupedia.controller.UserController;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {
    private TextView Name;
    private TextView EducationLevel;
    private TextView Email;
    private Switch locationAccessSwitch;
    private FirebaseUser user;
    private String name, edlevel, email;
    private boolean locationAccess;
    private UserController userController = UserController.getInstance();

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = userController.getName();
        edlevel = userController.getEdLevel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.fragment_settings, container, false);

        Name = rootview.findViewById(R.id.SettingName);
        name = userController.getName();

        EducationLevel = rootview.findViewById(R.id.SettingEdu);
        edlevel = userController.getEdLevel();

        Email = rootview.findViewById(R.id.SettingEmail);
        email = userController.getEmail();
        Email.setText(email);

        locationAccessSwitch = rootview.findViewById(R.id.switch1);
        locationAccess = userController.getLocationAccess();
        locationAccessSwitch.setChecked(locationAccess);


        ImageButton changePassword=(ImageButton) rootview.findViewById(R.id.ChangePasswordButton);
        ImageButton changeUsername=(ImageButton) rootview.findViewById(R.id.ChangeEmailButtonSettings);
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

        RadioGroup sortBy = (RadioGroup) rootview.findViewById(R.id.radioGender);
        sortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
            }
        });


        Name.setText(name);
        EducationLevel.setText(edlevel);
        return rootview;
    }

    public void updateInfo() {
        name = userController.getName();
        edlevel = userController.getEdLevel();
        locationAccess = userController.getLocationAccess();
        email = userController.getEmail();
        Name.setText(name);
        EducationLevel.setText(edlevel);
        locationAccessSwitch.setChecked(locationAccess);
        Email.setText(email);
    }

}