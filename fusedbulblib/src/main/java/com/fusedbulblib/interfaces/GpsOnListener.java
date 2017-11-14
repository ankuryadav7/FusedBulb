package com.fusedbulblib.interfaces;

import android.location.Location;

/**
 * Created by AnkurYadav on 23-09-2017.
 */

public interface GpsOnListener {

    public void gpsStatus(boolean _status);
    public void gpsPermissionDenied(int deviceGpsStatus);
    public void gpsLocationFetched(Location location);
}
