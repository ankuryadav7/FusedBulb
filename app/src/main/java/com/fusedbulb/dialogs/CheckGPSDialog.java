package com.fusedbulb.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fusedbulb.R;
import com.fusedbulb.fonts.FontTypeFace;
import com.fusedbulblib.GPSCheckPoint;
import com.fusedbulblib.interfaces.DialogClickListener;
import com.fusedbulblib.interfaces.GpsOnListner;


/**
 * Created by AnkurYadav on 23-09-2017.
 */

public class CheckGPSDialog{

    Activity activity;
    GpsOnListner gpsOnListner;
    public CheckGPSDialog(Activity context) {
        this.activity=context;
        this.gpsOnListner=(GpsOnListner)activity;
    }

    AlertDialog.Builder alertDialog;
    Dialog dialog;
    DialogClickListener dialogClickListner;
    public void showDialog(DialogClickListener listner) {
        try {
            dialogClickListner = listner;
            alertDialog = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View v = inflater.inflate(R.layout.use_location_dialog, null);

            alertDialog.setView(v);
            if (dialog == null) {
                dialog = alertDialog.create();
            }

            LinearLayout positive_button = (LinearLayout) v.findViewById(R.id.positive_button);
            LinearLayout negative_button = (LinearLayout) v.findViewById(R.id.negative_button);

            TextView title = (TextView) v.findViewById(R.id.title);
            TextView message = (TextView) v.findViewById(R.id.message);
            TextView suggetion = (TextView) v.findViewById(R.id.suggetion);

            TextView negative_txt = (TextView) v.findViewById(R.id.negative_txt);
            TextView positive_txt = (TextView) v.findViewById(R.id.positive_txt);

            title.setTypeface(new FontTypeFace(activity).MontserratRegular());
            title.setTypeface(new FontTypeFace(activity).MontserratRegular());
            message.setTypeface(new FontTypeFace(activity).MontserratRegular());
            suggetion.setTypeface(new FontTypeFace(activity).MontserratRegular());

            negative_txt.setTypeface(new FontTypeFace(activity).MontserratRegular());
            positive_txt.setTypeface(new FontTypeFace(activity).MontserratRegular());


            positive_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogClickListner.positiveListener(activity,dialog);

                    /*Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    activity.startActivity(intent);
                    gpsOnHandler();*/


                }
            });

            negative_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogClickListner.negativeListener(activity,dialog);
                   // gpsOnListner.gpsPermissionDenied(1);
                }
            });

            dialog.show();


        } catch (Exception e) {

        }

    }

    int count=0;
    private void gpsOnHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isGPSEnabled = GPSCheckPoint.gpsProviderEnable(activity);
                boolean isNetworkEnabled = GPSCheckPoint.networkProviderEnable(activity);
                if (!isGPSEnabled && !isNetworkEnabled) {
                    if (count<3){
                        gpsOnHandler();
                        count++;
                    }else {gpsOnListner.gpsStatus(true);count=3;}

                } else {gpsOnListner.gpsStatus(true);
                }
            }},1000);
    }
}
