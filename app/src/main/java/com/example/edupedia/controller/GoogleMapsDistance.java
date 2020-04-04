package com.example.edupedia.controller;

import android.content.Context;
import android.location.Address;

import com.example.edupedia.model.School;
import com.example.edupedia.ui.AdapterClass;
import com.example.edupedia.ui.SchoolItem;
import com.example.edupedia.ui.home.HomeFragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


public class GoogleMapsDistance extends AsyncTask<Void, Object, ArrayList<School>> implements Serializable {

    private static final String TAG = "GoogleMapsDistance";
    private static final String KEY = "AIzaSyCUaalvzVnKZLKDtGCAp1hmU9pIrov4EMM";

    private AdapterClass adapterClass;
    private static GoogleMapsController googleMaps;
    private Context mContext;
    private HashMap<String, School> schoolHashMap;
    private ArrayList<School> schoolList;
   // private ArrayList<SchoolItem> schoolItemList;
    private RecyclerView recyclerView;
    private String userLocation;
    private HomeFragment homeFragment;
    private HashMap<String, School> schoolDB;
    private String userLat;
    private String userLng;


    public GoogleMapsDistance(HomeFragment homeFragment, Context context, ArrayList<School> schoolList,
                              String userLat, String userLng, AdapterClass adapterClass) {
        this.mContext = context;
        this.schoolList = schoolList;
       // this.schoolItemList = schoolItemList;
       // this.userLocation = userLocation;
        this.adapterClass= adapterClass;
        this.homeFragment = homeFragment;
        this.userLat = userLat;
        this.userLng = userLng;
    }
    public GoogleMapsDistance() {

    }

        @RequiresApi(api = Build.VERSION_CODES.N)
        /*
        ** method for retrieving distance using google api distance matrix asynchronously
         */
        protected ArrayList<School> doInBackground(Void...values) {
            //adapterClass = new AdapterClass(schoolItemList);
           // Log.d("UIStuff", "Size of school item list" + schoolItemList.size());
            //this.schoolHashMap = (HashMap<String, School>)objects[0];
            ArrayList<School> retSchools = new ArrayList<>();
            this.schoolDB = this.homeFragment.getSchoolDB();
            if (schoolDB == null) {
                Log.d(TAG, "School DB is null");
            }
            int i = 0;
            for (School school : schoolList) {
                Address schAddress = googleMaps.geoLocate("Singapore " + school.getPostalCode());
                String schLat = String.valueOf(schAddress.getLatitude());
                String schLong = String.valueOf(schAddress.getLongitude());
                ArrayList<String> modeList = new ArrayList<>();
                modeList.add("driving");
                modeList.add("transit");
                ArrayList<Double> distList = new ArrayList<>();
                ArrayList<Double> durList = new ArrayList<>();
                Log.d(TAG, userLat);
                Log.d(TAG, userLng);
                for (String mode : modeList) {
                    String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + schLat + "," + schLong +
                            "&destinations=" + this.userLat + "%2C" + this.userLng +
                            "&mode=" + mode +
                            "&key=" + KEY;
                    Log.e(TAG, requestURL);
                    String jsonStr = null;
                    try {
                        URL url = new URL(requestURL);
                        Log.d("URL", requestURL);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            InputStream in = new BufferedInputStream(conn.getInputStream());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line).append('\n');
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    in.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            jsonStr = sb.toString();
                        }
                    } catch (MalformedURLException e) {
                        Log.e(TAG, "MalformedURLException: " + e.getMessage());
                    } catch (ProtocolException e) {
                        Log.e(TAG, "ProtocolException: " + e.getMessage());
                    } catch (IOException e) {
                        Log.e(TAG, "IOException: " + e.getMessage());
                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e.getMessage());
                    }
                    Log.e(TAG, "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            JSONArray rows = jsonObj.getJSONArray("rows");
                            JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
                            double distance = Double.valueOf(elements.getJSONObject(0).getJSONObject("distance").getString("value"));
                            distance = distance / 1000;
                            distance = Math.round(distance * 100.0) / 100.0;
                            double duration = Double.valueOf(elements.getJSONObject(0).getJSONObject("duration").getString("value"));
                            duration = duration / 60;
                            duration = Math.round(duration * 100.0) / 100.0;
                            distList.add(distance);
                            Log.d(TAG, mode + distance);
                            durList.add(duration);
                        } catch (final JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }
                    }
                    try {
                        Log.e(TAG, "Driving time:" + durList.get(0));
                        school.setDrivingTime(durList.get(0));
                    } catch (IndexOutOfBoundsException e) {
                        Log.e(TAG, "Driving time error: " + school.getSchoolName());
                    }
                    try {
                        Log.e(TAG, "Transport time:" + durList.get(1));
                        school.setPublicTime(durList.get(1));
                    } catch (IndexOutOfBoundsException e) {
                        Log.e(TAG, "Transport time error: " + school.getSchoolName());
                    }
                    try {
                        Log.e(TAG, "Distance" + distList.get(0));
                        school.setDistance(distList.get(0));
                    } catch (IndexOutOfBoundsException e) {
                        Log.e(TAG, "Distance error: " + school.getSchoolName());
                    }
                }
                publishProgress(school, distList.get(0), i);
                retSchools.add(school);
                i++;
            }
           return retSchools;
        }

/*        protected void onPostExecute(ArrayList<School> schools) {
            Log.d(TAG, "Post Execute");
        }
*/
        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Object... values) {
            if (schoolDB == null) {
                schoolDB = homeFragment.getSchoolDB();
            }
            School school = (School) values[0];
            //Log.d("SchDB", String.valueOf(school.getDistance()));
            String schoolName = school.getSchoolName();
            schoolDB.replace(schoolName, school);
            homeFragment.setSchoolDB(schoolDB);
            //Log.d("SchDB", "Set");
            double distance = (double)values[1];
            int i = (int)values[2];
            //Toast.makeText(mContext, schoolName+ ":" + distance, Toast.LENGTH_SHORT).show();
            /*if (i == 0) { //the first update
                //this.adapterClass = this.homeFragment.getAdapter();
                if (adapterClass != null) {
                    Log.d(TAG, "Not NULL");
                }
                //recyclerView.setAdapter(adapterClass);
            }*/
            Log.d("UIStuff", "Updating distance" + schoolName);
            adapterClass.updateDistance(schoolName, distance);
        }

        public static void setGoogleMapsController(GoogleMapsController googleMapsController) {
            googleMaps = googleMapsController;
    }
}

