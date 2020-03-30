package com.example.edupedia.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StatisticsController extends ViewModel {
    private DatabaseReference user_db = FirebaseDatabase.getInstance().getReference().child("User_DB");
    private MutableLiveData<Integer> watchCount = new MutableLiveData<Integer>();

    public void subscribeUserDB(String schoolName)
    {
        user_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    if (userDataSnapshot != null && userDataSnapshot.hasChild("watchlist")) {
                        for (DataSnapshot watchSchool : userDataSnapshot.child("watchlist").getChildren()){
                            if(watchSchool.getValue().equals(schoolName)){
                                count++;
                                break;
                            }
                        }
                    }
                    System.out.println("User : " +  userDataSnapshot.getValue());
                }
                watchCount.setValue(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public LiveData<Integer> getWatchCount(){
        return watchCount;
    }
}
