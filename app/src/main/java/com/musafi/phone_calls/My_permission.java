package com.musafi.phone_calls;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;

public class My_permission {
    private static final int LOCATION_PERMISSIONS_REQUEST_CODE = 125;
    public static void askLocationPermissions(Context context) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION
                        ,Manifest.permission.ACCESS_FINE_LOCATION
                        ,Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        ,Manifest.permission.READ_PHONE_STATE
                        ,Manifest.permission.READ_CONTACTS
                },
                LOCATION_PERMISSIONS_REQUEST_CODE);
    }
}
