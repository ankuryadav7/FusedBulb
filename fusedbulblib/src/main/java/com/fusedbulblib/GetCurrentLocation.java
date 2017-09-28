package com.fusedbulblib;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.fusedbulblib.interfaces.GpsOnListner;
import com.fusedbulblib.permission.PermissionControl;
import com.fusedbulblib.playservices.PlayServiceAvailability;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.LocationListener;

/**
 * Created by AnkurYadav on 23-09-2017.
 */

public class GetCurrentLocation implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Activity activity;
    GpsOnListner gpsOnListner;
    public GetCurrentLocation(Activity context) {
        this.activity=context;
        this.gpsOnListner=(GpsOnListner)activity;
    }
    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        if (location.getLatitude()!=0.0){
            gpsOnListner.gpsLocationFetched(location);
            mGoogleApiClient.disconnect();

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void getCurrentLocation() {
        if (!PlayServiceAvailability.isAvailable(activity)) {
            activity.finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        PermissionControl permissionControl=new PermissionControl(activity);
        if (permissionControl.getOnlyLocationPermission(activity)==true){
            getLocation();
        }
    }

    private static final String TAG = "GetCurrentLocation";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    private void getLocation() {
        isGPSEnabled = GPSCheckPoint.gpsProviderEnable(activity);
        isNetworkEnabled = GPSCheckPoint.networkProviderEnable(activity);

        if (!isGPSEnabled && !isNetworkEnabled) {
            gpsOnListner.gpsStatus(false);
        } else {
            mGoogleApiClient.connect();
        }

    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

}
