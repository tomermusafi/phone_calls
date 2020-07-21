package com.musafi.phone_calls.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.musafi.phone_calls.Entity.CallInfo;
import com.musafi.phone_calls.Entity.UserInfo;

public class MyFirebase {

    static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("usersInfo");

    public static void saveDataInFirebase(UserInfo userInfo){
//        User temp = new User();
//        User user = new User("Gadi", 111111666, false);
        //Gson gson = new Gson();
        //gson.toJson(userInfo);
        myRef.child("Users").child(userInfo.getUserId()).setValue(userInfo);
    }

    public static void saveCallInFirebase(Context context, CallInfo callInfo){
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //String uuid = UUID.randomUUID().toString();
        myRef.child("Users").child(android_id).child("userCallsInfo").child(callInfo.getCallId()).setValue(callInfo);
        //myRef.child("Users").child(android_id).child("userCallsInfo").child(uuid).child("call_info").child("myLoc").setValue(callInfo.getCallLocation());
    }
}
