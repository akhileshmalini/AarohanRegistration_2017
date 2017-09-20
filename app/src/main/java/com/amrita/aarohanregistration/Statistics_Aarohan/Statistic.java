package com.amrita.aarohanregistration.Statistics_Aarohan;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Statistic {
    String Description, stat;

    public Statistic() {
    }

    public Statistic(String description, String stat) {

        Description = description;
        this.stat = stat;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
