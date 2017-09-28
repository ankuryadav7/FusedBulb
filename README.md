# FusedBulb
This is the library which uses google's FusedLocationApi for fetching the current location of the user.

![FusedBulb](https://s20.postimg.org/555wczj4d/fused_bulb.png)

Gradle
------
```
dependencies {
    ...
    compile 'com.fusedbulb.lib:fusedbulblib:1.0.0'
}
```

Below are the three steps by which you can integrate FusedBulb libaray in your project.

* **Step 1**
    * Add "GpsOnListener" in your main class. By implementing this you will get below three methods with different user action.
   ```java
    public class Activity_AddComplaint extends AppCompatActivity implements GpsOnListner{
    
     @Override
    public void gpsStatus(boolean _status) {
        if (_status==true){
            new GetCurrentLocation(this).getCurrentLocation();
        }else {
            //GPS is off on user's device.
        }
    }

    @Override
    public void gpsPermissionDenied(int deviceGpsStatus) {
        if (deviceGpsStatus==1){
            //user does not want to switch on GPS of his/her device.
        }

    }

    @Override
    public void gpsLocationFetched(Location location, String placeName) {
        if (location!=null){
           // you will get the users current location
        }else {
            Toast.makeText(this,"Unable to find location",Toast.LENGTH_SHORT).show();
        }
    }
    
    }
    ```
    
* **Step 2**
    * Call "GetCurrentLocation" class on button click.
   ```java
     new GetCurrentLocation(context).getCurrentLocation();
     ```
     
* **Step 3**
    * Call "onRequestPermissionsResult" method on your main class. This will return user's choice about location permission. This can be executed only in marshmallow or above. 
   ```java
     @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //handle permission allow
                } else {
                    //handle permission denied
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                return;
            }}
    }
     ```

Note
------
You need to make your app Multidex.

