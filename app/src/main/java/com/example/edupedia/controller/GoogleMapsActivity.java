package com.example.edupedia.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.example.edupedia.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class GoogleMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final int DEFAULT_ZOOM = 15;
    public static final int PERMISSION_REQUEST_CODE = 9001;
    private static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final int GPS_REQUEST_CODE = 9003;
    public static final String TAG = "MapDebug";
    private boolean mLocationPermissionGranted;

    private ImageButton mBtnLocate;
    private GoogleMap mGoogleMap;
    private EditText mSearchAddress;
    private Button confirmButton;
    private Address address = null;
    private Location curLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Marker curLocationMarker;
    private GoogleMapsController googleMapsController;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_maps_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        googleMapsController = new GoogleMapsController(this);
        mSearchAddress = findViewById(R.id.et_address);
        mBtnLocate = findViewById(R.id.btn_locate);
        confirmButton = findViewById(R.id.confirmButton);
        initGoogleMap();
        mBtnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationName = mSearchAddress.getText().toString();
                hideSoftKeyboard(view);
                address = googleMapsController.geoLocate(locationName);
                gotoLocation(mGoogleMap, address.getLatitude(), address.getLongitude());
                showMarker(address.getLatitude(), address.getLongitude());
                //Toast.makeText(this, address.getLocality(), Toast.LENGTH_SHORT).show();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (address != null | curLocation != null) {
                    if (address != null) {
                        String addressStr = null;
                        addressStr = googleMapsController.reverseGeolocate(address.getLatitude(), address.getLongitude());
                        if (addressStr != null) {
                            intent.putExtra("Address", addressStr);
                            intent.putExtra("userLat", String.valueOf(address.getLatitude()));
                            Log.d("GoogleMapsHere", String.valueOf(address.getLatitude()));
                            intent.putExtra("userLng", String.valueOf(address.getLongitude()));
                            Log.d("GoogleMapsHere", String.valueOf(address.getLongitude()));
                            //Log.d("Address:", addressStr);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    } else if (curLocation != null) {
                        String addressStr = null;
                        addressStr = googleMapsController.reverseGeolocate(curLocation.getLatitude(), curLocation.getLongitude());
                        if (addressStr != null) {
                            intent.putExtra("Address", addressStr);
                            intent.putExtra("userLat", String.valueOf(curLocation.getLatitude()));
                            Log.d("GoogleMapsHere", String.valueOf(curLocation.getLatitude()));
                            intent.putExtra("userLng", String.valueOf(curLocation.getLongitude()));
                            Log.d("GoogleMapsHere", String.valueOf(curLocation.getLongitude()));
                            //Log.d("Address:", addressStr);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                    /*if (addressStr != null) {
                        intent.putExtra("Address", addressStr);
                        Log.d("Address:", addressStr);
                        setResult(RESULT_OK, intent);
                        finish();
                    }*/
                }
            }
        });
        GoogleMapsDistance.setGoogleMapsController(this.googleMapsController);
    }

    private void setLocation(Location currentLocation) {
        this.curLocation = currentLocation;
    }

    private void showMarker(double lat, double lng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, lng));
        if (this.curLocationMarker != null) {
            curLocationMarker.remove();
        }
        this.curLocationMarker = mGoogleMap.addMarker(markerOptions);
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initGoogleMap() {
        if (isServicesOk()) {
            if (isGPSEnabled()) {
                checkLocationPermission();
                if (!mLocationPermissionGranted) {
                    requestLocationPermission();
                    if (!mLocationPermissionGranted) return;
                }
                Toast.makeText(this, "Ready to Map", Toast.LENGTH_SHORT).show();
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_fragment_container);

                supportMapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is showing on the screen");

        mGoogleMap=googleMap;
        googleMapsController.getCurrentLocation(new Callback() {
            @Override
            public void firebaseResponseCallback(Location location) {
                curLocation = location;
                //Log.d(TAG, curLocation.toString());
                double lat = curLocation.getLatitude();
                double lng = curLocation.getLongitude();
                gotoLocation(mGoogleMap, lat, lng);
                setLocation(curLocation);
                showMarker(lat, lng);
                mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
            }
        });
    }

    private void gotoLocation(GoogleMap mGoogleMap, double lat,double lng){

        LatLng latLng=new LatLng(lat,lng);

        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM);

        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    private boolean isGPSEnabled() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnabled) {
            return true;
        } else {

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permissions")
                    .setMessage("GPS is required for this app to work. Please enable GPS.")
                    .setPositiveButton("Yes", (new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            GoogleMapsActivity.this.startActivityForResult(intent, GPS_REQUEST_CODE);
                        }
                    }))
                    .setCancelable(false)
                    .show();
        }
        return false;
    }

    private void checkLocationPermission() {
        this.mLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isServicesOk() {

        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();

        int result = googleApi.isGooglePlayServicesAvailable(this);

        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApi.isUserResolvableError(result)) {
            Dialog dialog = googleApi.getErrorDialog(this, result, PLAY_SERVICES_ERROR_CODE, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface task) {
                    Toast.makeText(GoogleMapsActivity.this, "Dialog is cancelled by User", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        } else {
            Toast.makeText(this, "Play services are required by this application", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_google_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){

            case R.id.maptype_none:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            }
            case R.id.maptype_normal:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            }
            case R.id.maptype_satellite:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            }
            case R.id.maptype_terrain:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            }
            case R.id.maptype_hybrid:{
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_REQUEST_CODE) {

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerEnabled) {
                Toast.makeText(this, "GPS is enabled.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS not enabled. Unable to show user location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public GoogleMapsActivity getGoogleMapsActivity() { return this; }
}

/*interface Callback{
    void firebaseResponseCallback(Location result);
}*/
