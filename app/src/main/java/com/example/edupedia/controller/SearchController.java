package com.example.edupedia.controller;

import com.example.edupedia.model.DataStoreInterface;
import com.example.edupedia.model.School;
import com.example.edupedia.ui.SchoolItem;

import android.os.Build;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SearchController extends ViewModel {

    private MutableLiveData<String> textFilterEdLevel, textFilterGradeCutOff, textFilterNature,
            textFilterLocation;
    private MutableLiveData<ArrayList<String>> textFilterRegion, textFilterSchoolType, textFilterIP;
    private EditText editLocation;
    private SortController sortController = SortController.getInstance();

    public void storeFilterSettings() {
        //1. Save Filter Settings to Internal Storage
        JSONObject jsonFilter = new JSONObject();
        try {
            jsonFilter.put("EdLevel", textFilterEdLevel.getValue());
            jsonFilter.put("GradeCutOff", textFilterGradeCutOff.getValue());
            jsonFilter.put("Nature", textFilterNature.getValue());
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
            if (applyFilter(school, textFilterEdLevel.getValue(),
                    textFilterGradeCutOff.getValue(),
                    textFilterNature.getValue(),
                    null,
                    null,
                    null
                    ))
                results.add(school.getSchoolName());
        }
        //somehow when location is clicked
        return results;
    }

    private boolean applyFilter(School school,
                                String edLevel,
                                String gradeCutOff,
                                String nature,
                                ArrayList<String> region,
                                ArrayList<String> type,
                                ArrayList<String> ip) {
        HashMap<String, Object> filters = new HashMap<>();
        if (edLevel != null && !edLevel.isEmpty()) filters.put("EdLevel", edLevel);
        if (gradeCutOff != null && !gradeCutOff.isEmpty()) filters.put("GradeCutOff", gradeCutOff);
        if (nature != null && !nature.isEmpty()) filters.put("Nature", nature);
        if (region != null && !region.isEmpty()) filters.put("Region", region);
        if (type != null && !type.isEmpty()) filters.put("Type", type);
        if (ip != null && !ip.isEmpty()) filters.put("IntegratedProgramme", ip);
        Iterator iterator = filters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry filterEntry = (Map.Entry) iterator.next();
            Object filter =  filterEntry.getValue();
            switch ((String) filterEntry.getKey()) {
                case "EdLevel":
                    String maincode = school.getMainCode();
                    String filterLevel = ((String) filter).substring(0, ((String) filter).length()-6).toUpperCase();
                    if (!maincode.equals(filterLevel)) {
                        if ((maincode.equals("JUNIOR COLLEGE") |maincode.equals("MIXED LEVEL") )
                                && ((String) filter).equals("Tertiary Level"))
                            break;
                        return false;
                    }
                    break;
                case "GradeCutOff" :
                    if (edLevel.equals("Secondary Level")){

                        if (school.getGradePSLE()<Integer.parseInt((String) filter))
                            return false;
                    }
                    if (edLevel.equals("Tertiary Level")){
                        if (school.getGradeO()>Integer.parseInt((String) filter))
                            return false;
                    }
                    break;
                case "Nature":
                    String natureCode = school.getNatureCode();
                    if (!natureCode.equals(((String) filter)))
                        return false;
                    break;
                case "Region":
                    if (((ArrayList<String>) filter).contains(school.getZoneCode()))
                        return false;
                    break;
                case "Type":
                    if (((ArrayList<String>) filter).contains(school.getTypeCode()))
                        return false;
                    break;
                case "IntegratedProgramme":
                    if (school.isIp() && !((ArrayList<String>) filter).contains("Yes"))
                        return false;

                    else if (!school.isIp() && !((ArrayList<String>) filter).contains("No"))
                        return false;
                    break;
            }


        }
        return true;
    }


    private ArrayList<School> onAdvancedSearch(HashMap<String, School> basicResults) {
        ArrayList<School> advancedResults = new ArrayList<School>();
        Iterator dbIterator = basicResults.entrySet().iterator();
        while (dbIterator.hasNext()) {
            Map.Entry schoolEntry = (Map.Entry) dbIterator.next();
            School school = (School) schoolEntry.getValue();
            if (applyFilter(school,
                    null,
                    null,
                    null,
                    textFilterRegion.getValue(),
                    textFilterSchoolType.getValue(),
                    textFilterIP.getValue())){
                advancedResults.add(school);
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
    public ArrayList<School> getDistances(ArrayList<School> schools) {
        for (School school: schools) {
            String loc = getTextFilterLocation().getValue();
            school = GoogleMapsDistance.googleMapsDistance(school, loc);
            Log.e("School location:", school.getAddress());
        }
        return schools;
    }

    public ArrayList<String> retrieveResults(HashMap<String, School> db) {
        DataStoreInterface dataStore = DataStoreFactory.getDatastore("Results");
        ArrayList<String> results = (ArrayList<String>) dataStore.retrieveData();
//        Log.d("PRINT ARRAYLIST RESULTS", results.get(0));
        if (results == null) {
            results = new ArrayList<>(db.keySet());
            storeResults(results);
        }
        if (results.isEmpty()){
            return null;
        }
        return results;
    }

    public void storeResults(ArrayList<String> results) {
        DataStoreInterface dataStore = DataStoreFactory.getDatastore("Results");
        dataStore.storeToMap(results);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<School> generateSchools(HashMap<String, School> db, ArrayList<String> results) {
       if(results==null) return new ArrayList<>(db.values());
        ArrayList<School> schoolList = new ArrayList<>();
        for (String name : results){
            schoolList.add(db.get(name));
        }
        schoolList = sortController.sortBy(SortController.NAME,true, schoolList);
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

    public MutableLiveData<String> getTextFilterNature() {
        HashMap<String, String> filterSettings = retrieveFilterSettings();
        String s = filterSettings.get("Nature");
        if (textFilterNature == null) {
            textFilterNature = new MutableLiveData<String>();
        }
        if (s != null) {
            textFilterNature.setValue(s);
        }
        return textFilterNature;
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

    public MutableLiveData<ArrayList<String>> getTextFilterRegion() {
        if (textFilterRegion == null) {
            textFilterRegion = new MutableLiveData<ArrayList<String>>();
        }
        return textFilterRegion;
    }

    public MutableLiveData<ArrayList<String>> getTextFilterSchoolType() {
        if (textFilterSchoolType == null) {
            textFilterSchoolType = new MutableLiveData<ArrayList<String>>();
        }
        return textFilterSchoolType;
    }

    public MutableLiveData<ArrayList<String>> getTextFilterIP() {
        if (textFilterIP == null) {
            textFilterIP = new MutableLiveData<ArrayList<String>>();
        }
        return textFilterIP;
    }



    public void setTextFilterEdLevel(String s) {
        MutableLiveData<String> liveData = this.getTextFilterEdLevel();
        liveData.setValue(s);
    }

    public void setTextFilterNature(String s) {
        MutableLiveData<String> liveData = this.getTextFilterNature();
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
    public void setTextFilterRegion(ArrayList<String> s) {
        MutableLiveData<ArrayList<String>> liveData = this.getTextFilterRegion();
        liveData.setValue(s);
    }
    public void setTextFilterSchoolType(ArrayList<String> s) {
        MutableLiveData<ArrayList<String>> liveData = this.getTextFilterSchoolType();
        liveData.setValue(s);
    }
    public void setTextFilterIP(ArrayList<String> s) {
        MutableLiveData<ArrayList<String>> liveData = this.getTextFilterIP();
        liveData.setValue(s);
    }
}

