package com.amrita.aarohanregistration.Events_Aarohan;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class Events {

    String eventName, category;
    int grpCount;

    public Events(String eventName, String category, int grpCount) {
        this.eventName = eventName;
        this.category = category;
        this.grpCount = grpCount;
    }

    public Events() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getGrpCount() {
        return grpCount;
    }

    public void setGrpCount(int grpCount) {
        this.grpCount = grpCount;
    }
}
