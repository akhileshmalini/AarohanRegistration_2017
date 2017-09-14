package com.amrita.aarohanregistration;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class FeedBackEvent {

    String evName;
    float rating;


    public FeedBackEvent() {
        evName="";
        rating=0;
    }

    public FeedBackEvent(String evName, float rating) {
        this.evName = evName;
        this.rating = rating;
    }


    public String getEvName() {
        return evName;
    }

    public void setEvName(String evName) {
        this.evName = evName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
