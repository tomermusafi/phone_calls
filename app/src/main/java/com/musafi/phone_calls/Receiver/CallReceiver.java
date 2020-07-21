package com.musafi.phone_calls.Receiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.musafi.phone_calls.Entity.CallInfo;
import com.musafi.phone_calls.Entity.CallStatus;
import com.musafi.phone_calls.Utils.CurrentLocation;

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
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd hh:mm:ss a");
        String strDate = dateFormat.format(new Date(System.currentTimeMillis()));
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

        CurrentLocation.stopRecording(ctx, (callDuration / (1000*1000*1000))/1000);
        Log.d("pttt", "number = " + number);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start)
    {
        Log.d("pttt", "onOutgoingCallStarted ");
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd hh:mm:ss a");
        String strDate = dateFormat.format(new Date(System.currentTimeMillis()));
        Log.d("pttt", "time: "  + strDate);
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

        CurrentLocation.stopRecording(ctx, (callDuration / (1000*1000*1000))/1000);
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