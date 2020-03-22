package com.example.edupedia.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.edupedia.R;
import com.example.edupedia.controller.UserController;
import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.model.School;
import com.example.edupedia.ui.Compare.CompareViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsFragment extends Fragment {
    private TextView Name;
    private TextView EducationLevel;
    private TextView Email;
    private FirebaseUser user;
    private static UserController userController = null;

    public static UserController getInstance() {
        if (userController == null)
            userController = new UserController();
        return userController;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.fragment_settings, container, false);

        Name = rootview.findViewById(R.id.SettingName);
        Name.setText(userController.getName());

        EducationLevel = rootview.findViewById(R.id.SettingEdu);
        EducationLevel.setText(userController.getEdLevel());

        Email = rootview.findViewById(R.id.SettingEmail);
        Email.setText(userController.getEmail());

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

        RadioGroup sortBy = (RadioGroup) rootview.findViewById(R.id.radioGender);
        sortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
            }
        });

        return rootview;
    }

}