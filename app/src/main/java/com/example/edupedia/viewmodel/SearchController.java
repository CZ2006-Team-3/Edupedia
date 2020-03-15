package com.example.edupedia.viewmodel;

import com.example.edupedia.model.DataStoreInterface;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class SearchController extends ViewModel {


    private MutableLiveData<String> textFilterEdLevel, textFilterGradeCutOff, textFilterPrefStream, textFilterLocation;

    public void onSearch() {

        //1. Save Filter Settings to Internal Storage

        JSONObject jsonFilter = new JSONObject();
        try {
            jsonFilter.put("EdLevel", textFilterEdLevel.getValue());
            jsonFilter.put("GradeCutOff", textFilterGradeCutOff.getValue());
            jsonFilter.put("PrefStream", textFilterPrefStream.getValue());
            jsonFilter.put("Location", textFilterLocation.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataStoreInterface dataStore = DataStoreFactory.getDatastore("Filter");
        dataStore.storeToMap(jsonFilter);


    }

    public HashMap<String, String> retrieveFilterSettings() {
        DataStoreInterface dataStore = DataStoreFactory.getDatastore("Filter");
        JSONObject jsonFilter = dataStore.retrieveData();
        if (jsonFilter == null) {
            jsonFilter = new JSONObject();
        }
        HashMap<String, String> map = new HashMap<String, String>();
        Iterator<String> keys = jsonFilter.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                String value = (String) jsonFilter.get(key);
                map.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public MutableLiveData<String> getTextFilterEdLevel() {
        HashMap<String, String> filterSettings = retrieveFilterSettings();
        String s = filterSettings.get("EdLevel");
        if (textFilterEdLevel == null) {
            textFilterEdLevel = new MutableLiveData<String>();
        }
        if (s != null) {
            textFilterEdLevel.setValue(s);
        }
        return textFilterEdLevel;
    }

    public MutableLiveData<String> getTextFilterGradeCutOff() {
        HashMap<String, String> filterSettings = retrieveFilterSettings();
        String s = filterSettings.get("GradeCutOff");
        if (textFilterGradeCutOff == null) {
            textFilterGradeCutOff = new MutableLiveData<String>();
        }
        if (s != null) {
            textFilterGradeCutOff.setValue(s);
        }
        return textFilterGradeCutOff;
    }

    public MutableLiveData<String> getTextFilterPrefStream() {
        HashMap<String, String> filterSettings = retrieveFilterSettings();
        String s = filterSettings.get("PrefStream");
        if (textFilterPrefStream == null) {
            textFilterPrefStream = new MutableLiveData<String>();
        }
        if (s != null) {
            textFilterPrefStream.setValue(s);
        }
        return textFilterPrefStream;
    }

    public MutableLiveData<String> getTextFilterLocation() {
        HashMap<String, String> filterSettings = retrieveFilterSettings();
        String s = filterSettings.get("Location");
        if (textFilterLocation == null) {
            textFilterLocation = new MutableLiveData<String>();
        }
        if (s != null) {
            textFilterLocation.setValue(s);
        }
        return textFilterLocation;
    }


    public void setTextFilterEdLevel(String s) {
        MutableLiveData<String> liveData = this.getTextFilterEdLevel();
        liveData.setValue(s);
    }

    public void setTextFilterPrefStream(String s) {
        MutableLiveData<String> liveData = this.getTextFilterPrefStream();
        liveData.setValue(s);
    }

    public void setTextFilterGradeCutOff(String s) {
        MutableLiveData<String> liveData = this.getTextFilterGradeCutOff();
        liveData.setValue(s);
    }

    public void setTextFilterLocation(String s) {
        MutableLiveData<String> liveData = this.getTextFilterLocation();
        liveData.setValue(s);
    }
}
