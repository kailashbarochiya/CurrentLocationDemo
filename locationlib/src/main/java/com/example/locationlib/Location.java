package com.example.locationlib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by kailash on 12/12/2017.
 */

public class Location extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private LocationRequest mLocationRequest;
    private static GoogleApiClient mGoogleApiClient;
    private static android.location.Location mCurrentLocation;
    private Activity activity;

    public Location(Activity applicationContext) {
        activity = applicationContext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void checkPermission() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            if (checkLocationPermission())
            {
                createLocationRequest();
                mGoogleApiClient = new GoogleApiClient.Builder(activity)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this).build();
                mGoogleApiClient.connect();
            }

        } else {
            createLocationRequest();
            mGoogleApiClient = new GoogleApiClient.Builder(activity)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            mGoogleApiClient.connect();
        }


    }


    public boolean  checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (mGoogleApiClient == null) {
                        createLocationRequest();
                        mGoogleApiClient = new GoogleApiClient.Builder(activity).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
                        mGoogleApiClient.connect();
                    }

                } else {

                    Toast.makeText(Location.this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        // mLocationRequest.setNumUpdates(2);
        // mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }


    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {


        @SuppressLint("MissingPermission") PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, Location.this);

        @SuppressLint("MissingPermission") android.location.Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if (mLastLocation != null) {
            Log.d("CurrentLatlong==", "Lat:" + mLastLocation.getLatitude() + " Lng:" + mLastLocation.getLongitude());
            setmCurrentLocation(mLastLocation);
        }

    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

        mCurrentLocation = location;

        if (null != mCurrentLocation) {
            setmCurrentLocation(mCurrentLocation);
        }
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) Location.this);
    }


    public static android.location.Location getmCurrentLocation() {
        return mCurrentLocation;
    }

    public void setmCurrentLocation(android.location.Location mCurrentLocation) {
        this.mCurrentLocation = mCurrentLocation;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                startLocationUpdates();
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onPause() {
        super.onPause();

        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                stopLocationUpdates();
            }
        }


    }


}
