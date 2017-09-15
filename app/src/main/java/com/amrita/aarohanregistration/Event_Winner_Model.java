package com.amrita.aarohanregistration;

/**
 * Created by Akhilesh on 9/12/2017.
 */

public class Event_Winner_Model {

    String grp, place,school;


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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Event_Winner_Model() {

    }

    public Event_Winner_Model(String grp, String place, String school) {

        this.grp = grp;
        this.place = place;
        this.school = school;
    }
}
