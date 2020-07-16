package com.musafi.phone_calls;



class CallInfo {
    private String callId;
    private long callDuration;
    private String date;
    private String otherPhoneNumber;
    private String otherName;
    private String callStatus;
    private MyLoc callLocation;

    public CallInfo(long callDuration, String date, String otherPhoneNumber, String otherName, String callStatus, MyLoc callLocation, String callId) {
        this.callDuration = callDuration;
        this.date = date;
        this.otherPhoneNumber = otherPhoneNumber;
        this.otherName = otherName;
        this.callStatus = callStatus;
        this.callLocation = callLocation;
        this.callId = callId;
    }

    public CallInfo() {
    }

    public long getCallDuration() {
        return callDuration;
    }

    public CallInfo setCallDuration(long callDuration) {
        this.callDuration = callDuration;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CallInfo setDate(String date) {
        this.date = date;
        return this;
    }

    public String getOtherPhoneNumber() {
        return otherPhoneNumber;
    }

    public CallInfo setOtherPhoneNumber(String otherPhoneNumber) {
        this.otherPhoneNumber = otherPhoneNumber;
        return this;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public CallInfo setCallStatus(String callStatus) {
        this.callStatus = callStatus;
        return this;
    }

    public MyLoc getCallLocation() {
        return callLocation;
    }

    public CallInfo setCallLocation(MyLoc callLocation) {
        this.callLocation = callLocation;
        return this;
    }

    public String getOtherName() {
        return otherName;
    }

    public CallInfo setOtherName(String otherName) {
        this.otherName = otherName;
        return this;
    }

    public String getCallId() {
        return callId;
    }

    public CallInfo setCallId(String callId) {
        this.callId = callId;
        return this;
    }
}
