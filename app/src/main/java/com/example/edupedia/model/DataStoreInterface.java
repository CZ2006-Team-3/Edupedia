package com.example.edupedia.model;

import org.json.JSONArray;
import org.json.JSONObject;

public interface DataStoreInterface {

    public Object retrieveData();

    public void storeToMap(Object obj);

}
