package com.musafi.phone_calls.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.musafi.phone_calls.R;
import com.musafi.phone_calls.Utils.Manage_contents;
import com.musafi.phone_calls.Utils.MySharedPreferences;
import com.musafi.phone_calls.Utils.My_permission;

public class MainActivity extends AppCompatActivity {

    private final int LOCATION_PERMISSIONS_REQUEST_CODE = 125;
    private MySharedPreferences msp;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msp = new MySharedPreferences(this);
        My_permission.askLocationPermissions(this);
    }

    // // // // // // // // // // // // // // // // Permissions  // // // // // // // // // // // // // // //

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSIONS_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Singleton singleton = Singleton.getInstance(this);
                    if(msp.getInt("counter", -1) == 0) {
                        Manage_contents.readContacts(this);
                    }
                    int counter = msp.getInt("counter", -1);
                    msp.putInt("counter", counter + 1);
                    Toast.makeText(MainActivity.this, "Result code = " + grantResults[0], Toast.LENGTH_SHORT).show();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void openAppSettingsManually() {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
