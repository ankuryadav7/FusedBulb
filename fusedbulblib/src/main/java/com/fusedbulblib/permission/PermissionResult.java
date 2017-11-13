package com.fusedbulblib.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import com.fusedbulblib.GPSCheckPoint;
import com.fusedbulblib.GetCurrentLocation;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by AnkurPC_Webdior on 11/13/2017.
 */

public class PermissionResult extends AppCompatActivity {

    public boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    String PERMISSION_GRANTED="GRANTED";
    String PERMISSION_DENIED="DENIED";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionControl permissionControl=new PermissionControl(this);
        if (permissionControl.getOnlyLocationPermission(this)==true){
            isGPSEnabled = GPSCheckPoint.gpsProviderEnable(this);
            isNetworkEnabled = GPSCheckPoint.networkProviderEnable(this);
            if (!isGPSEnabled && !isNetworkEnabled) {
                LocationSettingsRequest.Builder locationSettingsRequestBuilder = new LocationSettingsRequest.Builder().addLocationRequest(GetCurrentLocation.mLocationRequest);
                com.google.android.gms.common.api.PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(GetCurrentLocation.mGoogleApiClient, locationSettingsRequestBuilder.build());
                result.setResultCallback(mResultCallbackFromSettings);
            }else {
                LocationSettingsRequest.Builder locationSettingsRequestBuilder = new LocationSettingsRequest.Builder().addLocationRequest(GetCurrentLocation.mLocationRequest);
                com.google.android.gms.common.api.PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(GetCurrentLocation.mGoogleApiClient, locationSettingsRequestBuilder.build());
                result.setResultCallback(mResultCallbackFromSettings);
                handlerCall();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent permissionIntent = new Intent("com.fusedbulblib");
                    permissionIntent.putExtra("PERMISSION",PERMISSION_GRANTED);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(permissionIntent);
                    onBackPressed();

                } else {
                    Intent permissionIntent = new Intent("com.fusedbulblib");
                    permissionIntent.putExtra("PERMISSION",PERMISSION_DENIED);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(permissionIntent);
                    onBackPressed();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                return;
            }
        }
    }

    private ResultCallback<LocationSettingsResult> mResultCallbackFromSettings = new ResultCallback<LocationSettingsResult>() {
        @Override
        public void onResult(LocationSettingsResult result) {
            final Status status = result.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        status.startResolutionForResult(PermissionResult.this, 10);
                    } catch (IntentSender.SendIntentException e) {
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    break;
            }
        }
    };

    private final int REQ_LOCATION_ON = 10;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_LOCATION_ON:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Intent permissionIntent = new Intent("com.fusedbulblib");
                        permissionIntent.putExtra("GPS",PERMISSION_GRANTED);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(permissionIntent);
                        handlerCall();
                        break;
                    case Activity.RESULT_CANCELED:
                        Intent permissionIntent2 = new Intent("com.fusedbulblib");
                        permissionIntent2.putExtra("GPS",PERMISSION_DENIED);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(permissionIntent2);
                        handlerCall();
                        break;
                    default:
                        break;
                }
                break;
        }}


    private void handlerCall() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        },500);
    }

}