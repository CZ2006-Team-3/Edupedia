package com.example.edupedia.controller;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class GoogleMapsController extends AppCompatActivity implements OnMapReadyCallback {
    public static final int DEFAULT_ZOOM = 15;
    //private static final String TAG = "MapActivity";
    public static final int PERMISSION_REQUEST_CODE = 9001;
    private static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final int GPS_REQUEST_CODE = 9003;
    public static final String TAG = "MapDebug";
    private boolean mLocationPermissionGranted;

    private ImageButton mBtnLocate;
    private GoogleMap mGoogleMap;
    private EditText mSearchAddress;
    private Button confirmButton;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is showing on the screen");

        mGoogleMap=googleMap;

        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
    }

    public Address geoLocate2(String locationName) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Address address = null;
        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
            if (addressList.size() == 1) {
                address = addressList.get(0);
            }
            else {
                for (Address add : addressList) {
                    Log.d(TAG, "geoLocate: Address: " + address.getAddressLine(address.getMaxAddressLineIndex()));
                }
                address = addressList.get(0);
            }
        } catch (IOException e) {

        }
        return address;
    }
    private Location getCurrentLocation(GoogleMap mGoogleMap) {
        Log.d(TAG, "Getting the device's current location");
        Location currentLocation = null;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (checkLocationPermission()) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Found location!");
                            Location currentLocation = (Location) task.getResult();
                            //double lat = currentLocation.getLatitude();
                            //double lng = currentLocation.getLongitude();
                        } else {
                            Log.d(TAG, "OnComplete: Current location is null");
                            //Toast.makeText(MapActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException " + e.getMessage());
        }
        return currentLocation;
    }
    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
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
                    Toast.makeText(GoogleMapsController.this, "Dialog is cancelled by User", Toast.LENGTH_SHORT).show();
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
}
