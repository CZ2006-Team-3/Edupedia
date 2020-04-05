package com.example.edupedia.model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Strategy Pattern to store search settings and results to application background files
 */
public interface DataStoreInterface {

    public Object retrieveData();

    public void storeToMap(Object obj);

}
