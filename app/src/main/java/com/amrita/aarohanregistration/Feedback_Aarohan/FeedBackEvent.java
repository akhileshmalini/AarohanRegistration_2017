package com.amrita.aarohanregistration.Feedback_Aarohan;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class FeedBackEvent {

    String evName;
    float rating;
    String evDescription;


    public FeedBackEvent() {
        evName="";
        rating=0;
        evDescription="";
    }

    public FeedBackEvent(String evName, float rating) {
        this.evName = evName;
        this.rating = rating;
    }

    public FeedBackEvent(String evName, float rating,String evDescription) {
        this.evName = evName;
        this.rating = rating;
        this.evDescription = evDescription;

    }


    public String getEvDescription() {
        return evDescription;
    }

    public void setEvDescription(String evDescription) {
        this.evDescription = evDescription;
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
