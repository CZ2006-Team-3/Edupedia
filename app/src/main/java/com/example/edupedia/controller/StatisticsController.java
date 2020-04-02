package com.example.edupedia.controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Controller class to manage the display the number of people who have placed through notifying relevant observers (Observer) of any changes
 * in the MutableLiveData<Integer> watchCount.
 */
public class StatisticsController extends ViewModel {
    private DatabaseReference user_db = FirebaseDatabase.getInstance().getReference().child("User_DB");
    private MutableLiveData<Integer> watchCount = new MutableLiveData<Integer>();

    /**
     * Subscribe to Firebase Realtime Database to observe for changes in all users' information, particularly the changes made in watchlist.
     * Sets MutableLiveData<Integer> watchCount attribute with a count of number of people having a particular school (schoolName) in the watchlist.
     * @param schoolName name of a particular school to display its popularity
     */
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
