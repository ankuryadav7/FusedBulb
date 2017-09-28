package com.fusedbulblib;

import android.app.Activity;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by AnkurYadav on 23-09-2017.
 */

public class GPSCheckPoint {

    public static LocationManager locationManager;
    public static boolean gpsProviderEnable(Activity context) {

        locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;
    }

    public static boolean networkProviderEnable(Activity context) {
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return isNetworkEnabled;
    }
}
