package com.fusedbulb;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import java.util.List;
import java.util.Locale;

/**
 * Created by AnkurYadav on 9/27/2017.
 */

public class GetAddress {

    Activity activity;
    public GetAddress(Activity context) {
        this.activity=context;
    }


    String city,state;
    String address;
    String country;
    String complete_add;
    public String fetchCurrentAddress(Location location) {
        try {

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(activity, Locale.getDefault());
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            address= addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            if (city==null){
                city=address;
                state=country;
                complete_add=city+", "+state;
            }else{
                complete_add=address+", "+city;
            }
        }catch (Exception e){

        }

        return complete_add;
    }
}
