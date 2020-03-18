package com.example.edupedia;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ImageButton changePassword = (ImageButton) view.findViewById(R.id.ChangePasswordButton);

        changePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }

            public void onVlick(View v){
                startActivity(new Intent (SettingsFragment.this, PopUpClass.class));
            }

        });

        return view;


    }
}
