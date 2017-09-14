package com.amrita.aarohanregistration;

/**
 * Created by Akhilesh on 9/7/2017.
 */

public class Student {


    String stdName,category,gender,school;

    public Student() {
        this.stdName = "";
        this.category = "";
        this.gender = "";
        this.school = "";
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Student(String stdName, String category, String gender, String school) {

        this.stdName = stdName;
        this.category = category;
        this.gender = gender;
        this.school = school;
    }
}
