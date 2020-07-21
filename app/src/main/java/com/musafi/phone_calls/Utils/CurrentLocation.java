package com.musafi.phone_calls.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.musafi.phone_calls.Entity.CallInfo;

public class CurrentLocation {
    private static CallInfo callInfo;
    private static FusedLocationProviderClient fusedLocationProviderClient;
    private static LocationRequest locationRequest;
    private static LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            if (locationResult.getLastLocation() != null) {
                newLocation(locationResult.getLastLocation());
            } else {
                Log.d("pttt", "Location information isn't available.");
            }
        }
        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
            //Toast.makeText(this, "Loc= " + locationAvailability.isLocationAvailable(), Toast.LENGTH_SHORT).show();
            Log.d("pttt", "Loc = "+locationAvailability.isLocationAvailable());
            locationAvailability.isLocationAvailable();
        }
    };

    public static void stopRecording(Context context, long callDuration) {
        if (fusedLocationProviderClient != null) {
            Task<Void> task = fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            task.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("pttt", "stop Location Callback removed.");
                        //stopSelf();
                    } else {
                        Log.d("pttt", "stop Failed to remove Location Callback.");
                    }
                }
            });
        }
        MyFirebase.saveCallInFirebase(context, callInfo.setCallDuration(callDuration));
    }

    public static void startRecording(Context context, CallInfo newCallInfo) {
        callInfo = newCallInfo;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationRequest = new LocationRequest();
            locationRequest.setSmallestDisplacement(1.0f);
            locationRequest.setInterval(5000);
            locationRequest.setFastestInterval(5000);
            //locationRequest.setMaxWaitTime(TimeUnit.MINUTES.toMillis(2));
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private static void newLocation(Location lastLocation) {
        callInfo.setmLatitude(lastLocation.getLatitude()).setmLongitude(lastLocation.getLongitude());

//        callInfo.setCallLocation(new MyLoc()
//                .setLatitude(lastLocation.getLatitude())
//                .setLongitude(lastLocation.getLongitude())
//                .setSpeed(lastLocation.getSpeed()));
        Log.d("pttt", "lat = "+lastLocation.getLatitude() +" lng = "+ lastLocation.getLongitude());

    }
}
