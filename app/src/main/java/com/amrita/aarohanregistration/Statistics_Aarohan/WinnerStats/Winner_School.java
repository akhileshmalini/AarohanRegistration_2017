package com.amrita.aarohanregistration.Statistics_Aarohan.WinnerStats;

/**
 * Created by Akhilesh on 9/14/2017.
 */

public class Winner_School {

    String SchoolName;
    int points;

    public Winner_School(String schoolName, int points) {
        SchoolName = schoolName;
        this.points = points;
    }

    public Winner_School() {
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
