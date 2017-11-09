package com.fusedbulb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.fusedbulb.dialogs.CheckGPSDialog;
import com.fusedbulb.dialogs.PermissionDeniedDialog;
import com.fusedbulb.fonts.FontTypeFace;
import com.fusedbulblib.GetCurrentLocation;
import com.fusedbulblib.interfaces.DialogClickListener;
import com.fusedbulblib.interfaces.GpsOnListner;

/**
 * Created by AnkurYadav on 23-09-2017.
 */

public class MainActivity extends AppCompatActivity implements GpsOnListner{

    TextView currentLocationTxt;
    GetCurrentLocation getCurrentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView getLocationTxt=(TextView)findViewById(R.id.getLocationTxt);
        TextView stopTxt=(TextView)findViewById(R.id.stopTxt);

        currentLocationTxt=(TextView)findViewById(R.id.currentLocationTxt);
        getLocationTxt.setTypeface(new FontTypeFace(this).MontserratRegular());
        currentLocationTxt.setTypeface(new FontTypeFace(this).MontserratRegular());
        getCurrentLocation=new GetCurrentLocation(MainActivity.this);
        getLocationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation.getContinuousLocation(true).getCurrentLocation();
            }
        });

        stopTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Location update stop",Toast.LENGTH_SHORT).show();
                getCurrentLocation.stopLocationUpdate();
            }
        });
    }

    @Override
    public void gpsStatus(boolean _status) {
          if (_status==false){
              new CheckGPSDialog(this).showDialog();
          }else {
              getCurrentLocation.getCurrentLocation();
          }
    }

    @Override
    public void gpsPermissionDenied(int deviceGpsStatus) {
            if (deviceGpsStatus==1){
                permissionDeniedByUser();
            }else {
                getCurrentLocation.getCurrentLocation();
            }
    }

    @Override
    public void gpsLocationFetched(Location location) {
        if (location != null) {
            currentLocationTxt.setText(location.getLatitude()+", "+location.getLongitude());
            Log.w("locationUpdate",currentLocationTxt.getText().toString());
           // currentLocationTxt.setText(new GetAddress(this).fetchCurrentAddress(location));
        } else {
            Toast.makeText(this, getResources().getString(R.string.unable_find_location), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation.getContinuousLocation(true).getCurrentLocation();
                } else {
                    permissionDeniedByUser();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                return;
            }}
    }

    private final int REQ_LOCATION_ON = 10;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case REQ_LOCATION_ON:
                    switch (resultCode) {
                        case Activity.RESULT_OK:
                            break;
                        case Activity.RESULT_CANCELED:
                            break;
                        default:
                            break;
                    }
                    break;
            }

    }

    private void permissionDeniedByUser() {

        new PermissionDeniedDialog(this).showDialog(new DialogClickListener() {
            @Override
            public void positiveListener(Activity context, Dialog dialog) {
                new GetCurrentLocation(MainActivity.this).getCurrentLocation();
            }

            @Override
            public void negativeListener(Activity context, Dialog dialog) {
                dialog.dismiss();
            }
        });

    }

}
