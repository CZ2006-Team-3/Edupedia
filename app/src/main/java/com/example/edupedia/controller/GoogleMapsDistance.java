package com.example.edupedia.controller;

import android.content.Context;
import android.location.Address;

import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


public class GoogleMapsDistance //extends AppCompatActivity
        extends AsyncTask<Void, Object, ArrayList<School>> implements Serializable {
    //AsyncTask<Object, String, ArrayList<School>>

//    private ProgressDialog dialog;
    //private static GoogleMapsActivity googleMaps;

    /*protected void onPreExecute() {
        this.googleMaps = new GoogleMapsActivity();
        adapterClass = new AdapterClass();
       // dialog = ProgressDialog.show(getContext(), null, "Loading schools...", false);
    }
*/  private static final String TAG = "GoogleMapsDistance";
    private static final String KEY = "AIzaSyCUaalvzVnKZLKDtGCAp1hmU9pIrov4EMM";
    private AdapterClass mAdapter;

    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainNavigationUI mainNavigationUI = new MainNavigationUI();
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, homeFragment, "Home Fragment").commit();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<School> schools = (ArrayList) extras.get("School List");
            Log.d(TAG, "Goittee");
            String userLocation = extras.getString("User Location");
            RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
            mRecyclerView = findViewById(R.id.recycler_view);
            mAdapter = new AdapterClass(schools);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            new distanceTask().execute(schools, userLocation);
            //googleMaps = new GoogleMapsActivity();
            //adapterClass = new AdapterClass();
        }
        //new AsyncCaller().execute();

    }*/

    //protected class distanceTask extends AsyncTask<Object, Void, ArrayList<School>> {
        private AdapterClass adapterClass;
        private GoogleMapsActivity googleMaps;
        private Context mContext;
        private HashMap<String, School> schoolHashMap;
        private ArrayList<School> schoolList;
        private ArrayList<SchoolItem> schoolItemList;
        private RecyclerView recyclerView;
        private String userLocation;
        private HomeFragment homeFragment;
        private HashMap<String, School> schoolDB;

        public GoogleMapsDistance(HomeFragment homeFragment, Context context, ArrayList<SchoolItem> schoolItemList, ArrayList<School> schoolList,
                                  String userLocation) {
            this.mContext = context;
            this.schoolList = schoolList;
            this.schoolItemList = schoolItemList;
            //this.recyclerView = recyclerView;
            this.userLocation = userLocation;
            this.homeFragment = homeFragment;
        }

        protected void onPreExecute() {
            googleMaps = new GoogleMapsActivity();
            //recyclerView.setAdapter(adapterClass);

            //adapterClass = new AdapterClass(schools);
            //adapterClass = new AdapterClass(schoolItemList);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected ArrayList<School> doInBackground(Void...values) {
            //adapterClass = new AdapterClass(schoolItemList);
            Log.d("UIStuff", "Size of school item list" + schoolItemList.size());
            //this.schoolHashMap = (HashMap<String, School>)objects[0];
            ArrayList<School> retSchools = new ArrayList<>();
            this.schoolDB = this.homeFragment.getSchoolDB();
            if (schoolDB == null) {
                Log.d(TAG, "School DB is null");
            }
            int i = 0;
            for (School school : schoolList) {
                Address schAddress = googleMaps.geoLocate2("Singapore " + school.getPostalCode());
                //Log.e(TAG, school.getPostalCode());
                //Log.e(TAG, school.getSchoolName());
                String schLat = String.valueOf(schAddress.getLatitude());
                //Log.e(TAG, "Sch Lat+" + schLat);
                String schLong = String.valueOf(schAddress.getLongitude());
                //Log.e(TAG, "Sch Long:" + schLong);
                Address userAddress = googleMaps.geoLocate2(userLocation);
                String userLat = String.valueOf(userAddress.getLatitude());
                //Log.e(TAG, "User Lat:" + userLat);
                String userLong = String.valueOf(userAddress.getLongitude());
                //Log.e(TAG, "User Long" + userLong);
                ArrayList<String> modeList = new ArrayList<>();
                modeList.add("driving");
                modeList.add("transit");
                ArrayList<Double> distList = new ArrayList<>();
                ArrayList<Double> durList = new ArrayList<>();
                for (String mode : modeList) {
                    //Log.e(TAG, mode);
                    String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + schLat + "," + schLong +
                            "&destinations=" + userLat + "%2C" + userLong +
                            "&mode=" + mode +
                            "&key=" + KEY;
                    Log.e(TAG, requestURL);
                    //HttpHandler sh = new HttpHandler();
                    //String jsonStr = sh.makeServiceCall(userLocation);
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
                            //response = convertStreamToString(in);
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
                            double duration = Double.valueOf(elements.getJSONObject(0).getJSONObject("duration").getString("value"));
                            duration = duration / 60;
                            distList.add(distance);
                            Log.d(TAG, mode + distance);
                            durList.add(duration);
                        } catch (final JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }
                    }
                    try {
                        Log.e(TAG, "Driving time:" + durList.get(0));
                        school.setDistance(durList.get(0));
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
                        Log.e(TAG, "Distance" + distList.get(1));
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

        protected void onPostExecute(ArrayList<School> schools) {
            Log.d(TAG, "Post Execute");
            //Toast.makeText(getContext(), "HI", Toast.LENGTH_SHORT).show();
            adapterClass.notifyDataSetChanged();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected void onProgressUpdate(Object... values) {
            if (schoolDB == null) {
                schoolDB = this.homeFragment.getSchoolDB();
                Log.d("SchDB", "here");
                if (schoolDB == null) {
                    Log.d("SchDB", "sStill here");
                }
            }
            School school = (School) values[0];
            Log.d("SchDB", String.valueOf(school.getDistance()));
            String schoolName = school.getSchoolName();
            schoolDB.replace(schoolName, school);
            this.homeFragment.setSchoolDB(schoolDB);
            Log.d("SchDB", "Set");
            double distance = (double)values[1];
            int i = (int)values[2];
            Toast.makeText(mContext, schoolName+ ":" + distance, Toast.LENGTH_SHORT).show();
            //adapterClass.onBindViewHolder();
            if (i == 0) { //the first update
                //this might be a problem if the user switches tabs then switches back since the home fragment instance would be different
                //this.recyclerView = this.homeFragment.getmRecyclerView(); //recycler view not null here
                this.adapterClass = this.homeFragment.getAdapter();
                //adapterClass = new AdapterClass(SchoolItemListFull)
                if (adapterClass != null) {
                    Log.d(TAG, "Not NULL");
                }
                //recyclerView.setAdapter(adapterClass);
            }
            adapterClass.updateDistance(schoolName, distance); //include a write to csv function maybe
        }
    }


   /* public static void setGoogleMapsActivity(GoogleMapsActivity googleMapsActivity) {
        googleMaps = googleMapsActivity;
    }*/

