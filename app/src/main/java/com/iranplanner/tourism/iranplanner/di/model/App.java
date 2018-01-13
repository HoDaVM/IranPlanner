package com.iranplanner.tourism.iranplanner.di.model;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.data.component.DaggerNetComponent;
import com.iranplanner.tourism.iranplanner.di.data.component.NetComponent;
import com.iranplanner.tourism.iranplanner.di.data.module.AppModule;
import com.iranplanner.tourism.iranplanner.di.data.module.NetModule;
import com.iranplanner.tourism.iranplanner.ui.activity.mainActivity.MainActivity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import server.Config;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by Hoda
 */
public class App extends MultiDexApplication {
    private NetComponent mNetComponent;
    private NetComponent googleNetComponent;
    public FirebaseAnalytics mFirebaseAnalytics;

    public FirebaseAnalytics getmFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }

    private static final String TAG = App.class.getSimpleName();

    private LocationManager locationManager;
    private Location location = null;
    private void overrideFont() {
        // for Override font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );}
    @Override
    public void onCreate() {
        super.onCreate();
        enableLocationCheck();
        prepareDirectories();
        overrideFont();
        instance = this;
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Config.BASEURL))
                .build();
        googleNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://maps.googleapis.com/"))
                .build();
        //

        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        // set in-app defaults
        Map<String, Object> remoteConfigDefaults = new HashMap();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, "1.0.0");
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL,
                "http://update.iranplanner.com/iranplanner.apk");

        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);
        firebaseRemoteConfig.fetch(60) // fetch every minutes
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "remote config is fetched.");
                            firebaseRemoteConfig.activateFetched();
                        }
                    }
                });

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;



    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public NetComponent getGoogleNetComponent() {
        return googleNetComponent;
    }

    public void enableLocationCheck() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        else if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public Location getLastLocation() {
        return location;
    }

    private void storeLocation(Location location) {
        this.location = location;
    }

    private LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            storeLocation(location);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    private static App instance;
    public static App getInstance() {
        return instance;
    }
    public String appBaseFolder;
    public String getImagesPath() {
        return appBaseFolder + "Images/";
    }

    public static String[] STORAGE_PERMISSIONS = {//5
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    public void prepareDirectories() {
        File baseFile;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 ||  isWriteStoragePermissionGranted() /*(checkGroupPermissions(App.STORAGE_PERMISSIONS))*/) {
            String path = Environment.getExternalStorageDirectory().toString();
            baseFile = new File(path, getResources().getString(R.string.app_name));
        } else {
            String path = getFilesDir().getPath();
            baseFile = new File(path);
        }

        baseFile.mkdir();
        if (!baseFile.exists() || !baseFile.isDirectory()) {
            throw new IllegalStateException("Unable to create IranPlanner base folder.");
        }

        String p = baseFile.getPath();
        appBaseFolder = p.endsWith("/") ? p : p + "/";
        createDirectories();
    }

    private void createDirectories() {
//        new File(getAudiosPath()).mkdir();
//        new File(getVideosPath()).mkdir();
        new File(getImagesPath()).mkdir();
//        new File(getTempPath()).mkdir();
    }

    public static boolean checkGroupPermissions(String... permissions) {
        boolean permission = true;// = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        List<Integer> permissionStates = new ArrayList<Integer>();
        for (String str : permissions) {
            if (ContextCompat.checkSelfPermission(getInstance(), str) != PackageManager.PERMISSION_GRANTED) {
                permission = false;
                break;
            }
        }
        return permission;
    }


    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }

    public static void createPermissionDialog(final Activity context, String appName, String permissionDescription) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(appName);
        builder.setMessage(permissionDescription);
        builder.setPositiveButton("permission", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + App.getInstance().getPackageName()));
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("cancel", null);
        builder.show();
    }

}
