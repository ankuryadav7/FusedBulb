# FusedBulb
This is the library which uses google's FusedLocationApi for fetching the current location of the user.


![FusedBulb](https://s20.postimg.org/ub9azembx/fusedbulb_banner.jpg)

Gradle
------
```
dependencies {
    ...
    compile 'com.fusedbulb.lib:fusedbulblib:1.0.0'
    ...
    New Version Now Available. 1.0.3. Kindly go down for more info
}
```

Below are the three steps by which you can integrate FusedBulb library in your project.

* **Step 1**
    * Add "GpsOnListener" in your main class. By implementing this you will get below three methods with different user action.
   ```java
    public class ActivityExample extends AppCompatActivity implements GpsOnListner{
    
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
    public void gpsLocationFetched(Location location) {
        if (location!=null){
           // you will get user's current location
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
     
Key Feature of 1.0.3-->>Continuous location update
------

If you want to fetch continuous location of the user then use same object of **GetCurrentLocation** class like below-: Just copy paste the below code and you will get the current loaction of user. 

Add activity in Menifest File
------
```java

<activity android:name="com.fusedbulblib.permission.PermissionResult"></activity>

```

```java

public class ActivityDemo extends AppCompatActivity implements GpsOnListener{

    GetCurrentLocation getCurrentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    getCurrentLocation=new GetCurrentLocation(this);
    
    getCurrentLocation.getContinuousLocation(true);
    getCurrentLocation.getCurrentLocation();
   }

    @Override
    public void gpsStatus(boolean _status) {
        Log.w("FusedBuld GpsStatus",_status+"");
        if (_status==true){
            getCurrentLocation.getContinuousLocation(true);
            getCurrentLocation.getCurrentLocation();
        }
    }

    @Override
    public void gpsPermissionDenied(int deviceGpsPermission) {
        Log.w("FusedBuld GpsPermission",deviceGpsPermission+"");
    }

    @Override
    public void gpsLocationFetched(Location location) {
        Log.w("FusedBuld location",location.getLatitude()+"--"+location.getLongitude());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getCurrentLocation.stopLocationUpdate();
    }
}




```


 Developed By
 ------
 Ankur Yadav- ankuryadavaj729@gmail.com

Note
------
You need to make your app Multidex.

