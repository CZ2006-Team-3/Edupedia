package com.example.edupedia.model;

import org.json.JSONArray;
import org.json.JSONObject;

public interface DataStoreInterface {

    public JSONObject retrieveData();

    public void storeToMap(JSONObject obj);

}
