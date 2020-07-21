package com.musafi.phone_calls.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.musafi.phone_calls.Entity.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class Manage_contents {
    static final String MY_PERMISSION = Manifest.permission.READ_CONTACTS;
    static MySharedPreferences msp;

    public static void readContacts(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, MY_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, MY_PERMISSION)) {
                    My_permission.askLocationPermissions(context);
                } else {
                    new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Permission missing")
                            .setMessage("Manually grant permission")
                            .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                    intent.setData(uri);
                                    context.startActivity(intent);
                                }
                            })
                            .show();
                }
                return;
            }
        }
        Map<String,String> contact = new HashMap<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        phoneNo = phoneNo.replace("-","").trim();
                        contact.put(""+phoneNo, ""+name);

                    }

                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        initUserInfo(contact, context);
    }

    public static void initUserInfo(Map<String,String> contact, Context context){
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        msp = new MySharedPreferences(context);


        UserInfo userInfo = new UserInfo()
                .setUserName(msp.getString("name", "none"))
                .setUserId(android_id)
                .setUserContacts(contact)
                .setUserPhoneNumber(msp.getString("number", "none"))
                ;

        MyFirebase.saveDataInFirebase(userInfo);
    }

}
