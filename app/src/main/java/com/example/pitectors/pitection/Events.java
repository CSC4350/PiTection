package com.example.pitectors.pitection;

/**
 * Created by rnice01 on 11/23/2015.
 */
public class Events {
    public String eventStatus;
    private String eventDevice;
    private String eventDate;
    private String user;


    Events(String event, String device,String date, String user){
        this.eventStatus = event;
        this.eventDate = date;
        this.eventDevice = device;
        this.user = user;
    }

    Events(){

    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventString) {
        this.eventStatus = eventString;
    }

    public String getEventDevice() {
        return eventDevice;
    }

    public void setEventDevice(String eventDevice) {
        this.eventDevice = eventDevice;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }
}
