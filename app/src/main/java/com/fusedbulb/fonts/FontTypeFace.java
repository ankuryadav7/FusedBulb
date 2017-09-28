package com.fusedbulb.fonts;

import android.app.Activity;
import android.graphics.Typeface;


/**
 * Created by AnkurYadav on 23-09-2017.
 */

public class FontTypeFace {

    Activity activity;
    public FontTypeFace(Activity context) {
     this.activity=context;
    }


    public Typeface MontserratRegular() {
        Typeface montserratRegular = Typeface.createFromAsset(activity.getAssets(), "Montserrat-Regular.ttf");
        return montserratRegular;
    }

}
