package com.amrita.aarohanregistration.Events_Aarohan;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Event_Student_Group {

    String stdName;


    public Event_Student_Group() {
        stdName="";
    }

    public Event_Student_Group(String stdName) {
        this.stdName = stdName;
    }


    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }


}
