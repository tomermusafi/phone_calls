package com.musafi.phone_calls;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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

import java.util.Date;

public class CallReceiver extends PhonecallReceiver {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback = new LocationCallback() {

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

    @Override
    protected void onIncomingCallReceived(Context ctx, String number, Date start)
    {
        Log.d("pttt", "onIncomingCallReceived ");
        startRecording(ctx);
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start)
    {

    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end)
    {
        Log.d("pttt", "onIncomingCallEnded");
        stopRecording();
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start)
    {

    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end)
    {

    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start)
    {

    }

    private void newLocation(Location lastLocation) {
        Log.d("pttt", "lat = "+lastLocation.getLatitude() +" lng = "+ lastLocation.getLongitude());
    }

    private void stopRecording() {
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
    }

    private void startRecording(Context context) {
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

}