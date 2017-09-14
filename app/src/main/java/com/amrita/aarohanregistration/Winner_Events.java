package com.amrita.aarohanregistration;

/**
 * Created by Akhilesh on 9/14/2017.
 */

public class Winner_Events {

    String EventName,Place,grpOrid;


    public Winner_Events(String eventName, String place, String grpOrid) {
        EventName = eventName;
        Place = place;
        this.grpOrid = grpOrid;
    }

    public Winner_Events() {
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getGrpOrid() {
        return grpOrid;
    }

    public void setGrpOrid(String grpOrid) {
        this.grpOrid = grpOrid;
    }
}
