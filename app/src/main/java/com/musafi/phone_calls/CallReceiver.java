package com.musafi.phone_calls;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CallReceiver extends PhonecallReceiver {

    private String callId;
    private long start;
    private long end;
    private long callDuration;
    private String date;
    private String otherPhoneNumber;
    private String otherName;
    private String callStatus;

    @Override
    protected void onIncomingCallReceived(Context ctx, String number, Date start)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(start);
        this.date = strDate;

        this.callId = UUID.randomUUID().toString();

        this.callStatus = CallStatus.INCOMING_CALL.toString();

        if(number != null){
            this.otherPhoneNumber = number;
        }else{
            this.otherPhoneNumber = "none";
        }

        CallInfo callInfo = new CallInfo()
                .setCallId(this.callId).setCallStatus(this.callStatus)
                .setDate(this.date)
                .setOtherPhoneNumber(this.otherPhoneNumber)
                .setOtherName("none");

        CurrentLocation.startRecording(ctx, callInfo);

        Log.d("pttt", "onIncomingCallReceived ");
        Log.d("pttt", "number = " + number);

//        TelephonyManager telephony = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
//        telephony.listen(new PhoneStateListener(){
//            @Override
//            public void onCallStateChanged(int state, String incomingNumber) {
//                super.onCallStateChanged(state, incomingNumber);
//                //System.out.println("incomingNumber : "+incomingNumber);
//                Log.d("pttt", "numberrrrrrr = " + incomingNumber);
//            }
//        },PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start)
    {
        this.start = start.getTime();
        Log.d("pttt", "onIncomingCallAnswered ");
        Log.d("pttt", "number = " + number);
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end)
    {
        Log.d("pttt", "onIncomingCallEnded");
        this.end = end.getTime();
        this.callDuration = this.end - this.start;

        CurrentLocation.stopRecording(ctx, callDuration / (1000 * 60 * 60 * 24));
        Log.d("pttt", "number = " + number);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start)
    {
        Log.d("pttt", "onOutgoingCallStarted ");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(start);
        this.date = strDate;

        this.callId = UUID.randomUUID().toString();

        this.callStatus = CallStatus.OUTGOING_CALL.toString();

        if(number != null){
            this.otherPhoneNumber = number;
        }else{
            this.otherPhoneNumber = "none";
        }

        CallInfo callInfo = new CallInfo()
                .setCallId(this.callId).setCallStatus(this.callStatus)
                .setDate(this.date)
                .setOtherPhoneNumber(this.otherPhoneNumber)
                .setOtherName("none");

        CurrentLocation.startRecording(ctx, callInfo);


        Log.d("pttt", "number = " + number);
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end)
    {
        Log.d("pttt", "onOutgoingCallEnded ");
        this.end = end.getTime();
        this.callDuration = this.end - this.start;

        CurrentLocation.stopRecording(ctx, callDuration / (1000 * 60 * 60 * 24));
        Log.d("pttt", "number = " + number);
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start)
    {

        CurrentLocation.stopRecording(ctx, 0);
        Log.d("pttt", "onMissedCall ");
        Log.d("pttt", "number = " + number);
    }





}