package com.example.edupedia.controller;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

public class GoogleMapsController  {
    public static final int DEFAULT_ZOOM = 15;
    //private static final String TAG = "MapActivity";
    public static final int PERMISSION_REQUEST_CODE = 9001;
    private static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final int GPS_REQUEST_CODE = 9003;
    public static final String TAG = "MapDebug";

    private GoogleMapsActivity googleMapsActivity;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    public GoogleMapsController(GoogleMapsActivity googleMapsActivity) {
        this.googleMapsActivity = googleMapsActivity;
    }

    public Address geoLocate(String locationName) {
        Geocoder geocoder = new Geocoder(googleMapsActivity, Locale.getDefault());
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

    /**
    * to reverse geolocate the longitude and latitude to
    **/
    public String reverseGeolocate(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(googleMapsActivity, Locale.getDefault());
        String addressStr = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            addressStr = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        }
        catch (IOException e) {

        }
        return addressStr;
    }
    public void getCurrentLocation(Callback callback) {
        Log.d(TAG, "Getting the device's current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(googleMapsActivity.getGoogleMapsActivity());
        try {
            //if (locationPermission) {
            Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    private Location curLocation;
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Found location!");
                            curLocation = (Location) task.getResult();
                            //double lat = currentLocation.getLatitude();
                            //double lng = currentLocation.getLongitude();
                        } else {
                            Log.d(TAG, "OnComplete: Current location is null");
                            //Toast.makeText(MapActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                        callback.firebaseResponseCallback(this.curLocation);
                    }
                });

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException " + e.getMessage());
        }
    }
   /* private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
*/
   /*
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
    }*/
/*
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
    }*/
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
    }*/

}

/**
 * interface to handle the asynchronous response for current location
 */
interface Callback{
    void firebaseResponseCallback(Location result);
}
