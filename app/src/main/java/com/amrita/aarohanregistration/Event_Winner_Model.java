package com.amrita.aarohanregistration;

/**
 * Created by Akhilesh on 9/12/2017.
 */

public class Event_Winner_Model {

    String grp, place;

    public Event_Winner_Model(String grp, String place) {
        this.grp = grp;
        this.place = place;
    }

    public Event_Winner_Model() {
    }

    public String getGrp() {
        return grp;
    }

    public void setGrp(String grp) {
        this.grp = grp;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
