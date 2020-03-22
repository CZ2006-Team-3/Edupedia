package com.example.edupedia.controller;

import com.example.edupedia.model.DataStoreInterface;
import com.example.edupedia.model.School;

import android.util.Log;
import android.widget.EditText;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class SearchController extends ViewModel {

    private MutableLiveData<String> textFilterEdLevel, textFilterGradeCutOff, textFilterPrefStream,
            textFilterLocation, textFilterRegion, textFilterSchoolType, textFilterSpecialNeeds;
    private EditText editLocation;
    private MutableLiveData<ArrayList<String>> textFilterCCAs;

    public void storeFilterSettings() {
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
        DataStoreInterface dataStore = DataStoreFactory.getDatastore("Filter"); //returns a filter object
        dataStore.storeToMap(jsonFilter);
    }

    public ArrayList<String> onBasicSearch(HashMap<String, School> schoolDB) {
        ArrayList<String> results = new ArrayList<>();
        Iterator dbIterator = schoolDB.entrySet().iterator();
        while (dbIterator.hasNext()) {
            Map.Entry schoolEntry = (Map.Entry) dbIterator.next();
            School school = (School) schoolEntry.getValue();
            String compare1 = school.getMainCode().toLowerCase()+" level";
            String compare2 = textFilterEdLevel.getValue().toLowerCase();
            Log.d("Main code", compare1);
            Log.d("Text Filter Ed Level:", compare2);
            if (compare1.equals(compare2)) {
//                    && school.getGradeCutOff() < Integer.parseInt(textFilterGradeCutOff.getValue())
//                    && school.equals(textFilterPrefStream.getValue())) {
                Log.d("School is added:", school.getSchoolName());
                results.add(school.getSchoolName());
            }
//            }
        }
        //somehow when location is clicked
        return results;
    }
    private HashMap<String, School> onAdvancedSearch(HashMap<String, School> basicResults) {
        JSONObject jsonFilter = new JSONObject();
        try {
            jsonFilter.put("Region", textFilterRegion.getValue());
            jsonFilter.put("Type of School", textFilterSchoolType.getValue());
            jsonFilter.put("Special Needs ", textFilterSpecialNeeds.getValue());
            jsonFilter.put("CCAs", textFilterCCAs.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, School> advancedResults = new HashMap<String, School>();
        Iterator dbIterator = basicResults.entrySet().iterator();
        while (dbIterator.hasNext()) {
            Map.Entry schoolEntry = (Map.Entry) dbIterator.next();
            School school = (School) schoolEntry.getValue();
            if (school.getClusterCode().equals(getTextFilterRegion().getValue())
                    && school.getTypeCode().equals(getTextFilterSchoolType().getValue())
                    && hasOverlap(school.getCcas(), getTextFilterCCAs().getValue())) {
                advancedResults.put(school.getSchoolName(), school);
            }
        }
        return advancedResults;
    }
    private boolean hasOverlap(ArrayList<String> list1, ArrayList<String> list2) {
        HashSet<String> set = new HashSet<>();
        set.addAll(list1);
        set.retainAll(list2); //list of overlapping ccas, hopefully
        if (set.size() > 0)
            return true;
        return false;
    }

    public HashMap<String, Object> retrieveAdvancedFilterSettings() {
        DataStoreInterface dataStore = DataStoreFactory.getDatastore("Filter");
        JSONObject jsonFilter = (JSONObject) dataStore.retrieveData();
        if (jsonFilter == null) {
            jsonFilter = new JSONObject();
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keys = jsonFilter.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                Object value = jsonFilter.get(key);
                map.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public HashMap<String, String> retrieveFilterSettings() {
        DataStoreInterface dataStore = DataStoreFactory.getDatastore("Filter");
        JSONObject jsonFilter = (JSONObject) dataStore.retrieveData();
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
    public void getDistances(ArrayList<School> schools) {
        for (School school: schools) {
            school = GoogleMapsDistance.googleMapsDistance(school, getTextFilterLocation().getValue());
        }
    }

    public ArrayList<String> retrieveResults(HashMap<String, School> db) {
        DataStoreInterface dataStore = DataStoreFactory.getDatastore("Results");
        ArrayList<String> results = (ArrayList<String>) dataStore.retrieveData();
//        Log.d("PRINT ARRAYLIST RESULTS", results.get(0));
        if (results == null) {
            results = new ArrayList<>(db.keySet());

        }
        if (results.isEmpty()){
            Log.d("FDfd","DFDS");
            results = new ArrayList<>(db.keySet());
            //return null;
        }
        return results;
    }

    public void storeResults(ArrayList<String> results) {
        DataStoreInterface dataStore = DataStoreFactory.getDatastore("Results");
        dataStore.storeToMap(results);
    }

    public ArrayList<School> generateSchools(HashMap<String, School> db, ArrayList<String> results) {
        ArrayList<School> schoolList = new ArrayList<>();
        for (String name : results){
            schoolList.add(db.get(name));
        }
        return schoolList;
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
            textFilterGradeCutOff.postValue(s);
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

    public MutableLiveData<String> getTextFilterRegion() {
        HashMap<String, String> filterSettings = retrieveFilterSettings();
        String s = filterSettings.get("Region");
        if (textFilterRegion == null) {
            textFilterRegion = new MutableLiveData<String>();
        }
        if (s != null) {
            textFilterRegion.setValue(s);
        }
        return textFilterRegion;
    }

    public MutableLiveData<String> getTextFilterSchoolType() {
        HashMap<String, String> filterSettings = retrieveFilterSettings();
        String s = filterSettings.get("School Type");
        if (textFilterSchoolType == null) {
            textFilterSchoolType = new MutableLiveData<String>();
        }
        if (s != null) {
            textFilterSchoolType.setValue(s);
        }
        return textFilterSchoolType;
    }

    public MutableLiveData<String> getTextFilterSpecialNeeds() {
        HashMap<String, String> filterSettings = retrieveFilterSettings();
        String s = filterSettings.get("Special Needs");
        if (textFilterSpecialNeeds == null) {
            textFilterSpecialNeeds = new MutableLiveData<String>();
        }
        if (s != null) {
            textFilterSpecialNeeds.setValue(s);
        }
        return textFilterSpecialNeeds;
    }
    public MutableLiveData<ArrayList<String>> getTextFilterCCAs() {
        HashMap<String, Object> advfilterSettings = retrieveAdvancedFilterSettings();
        ArrayList<String> ccas = (ArrayList) advfilterSettings.get("CCAs");
        if (textFilterCCAs == null) {
            textFilterCCAs = new MutableLiveData<>();
        }
        if (ccas.size() != 0) {
            textFilterCCAs.setValue(ccas);
        }
        return textFilterCCAs;
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
    public void setTextFilterRegion(String s) {
        MutableLiveData<String> liveData = this.getTextFilterRegion();
        liveData.postValue(s);
    }
    public void setTextFilterSchoolType(String s) {
        MutableLiveData<String> liveData = this.getTextFilterSchoolType();
        liveData.postValue(s);
    }
    public void setTextFilterSpecialNeeds(String s) {
        MutableLiveData<String> liveData = this.getTextFilterSpecialNeeds();
        liveData.setValue(s);
    }
    public void setTextFilterCCAs(ArrayList<String> list) {
        MutableLiveData<ArrayList<String>> liveData = this.getTextFilterCCAs();
        liveData.postValue(list);
    }
}

