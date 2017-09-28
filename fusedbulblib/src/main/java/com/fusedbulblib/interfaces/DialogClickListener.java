package com.fusedbulblib.interfaces;

import android.app.Activity;
import android.app.Dialog;

/**
 * Created by AnkurYadav on 23-09-2017.
 */

public interface DialogClickListener {
    
    void positiveListener(Activity context, Dialog dialog);
    void negativeListener(Activity context, Dialog dialog);
}
