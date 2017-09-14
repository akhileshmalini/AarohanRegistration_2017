package com.amrita.aarohanregistration;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Event_Student_Group_Count {

    String stdName;
    int count;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Event_Student_Group_Count() {
        stdName="";

    }

    public Event_Student_Group_Count(String stdName, int count) {
        this.stdName = stdName;
        this.count = count;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }


}
