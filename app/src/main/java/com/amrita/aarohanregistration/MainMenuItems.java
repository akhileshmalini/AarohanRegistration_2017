package com.amrita.aarohanregistration;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class MainMenuItems {

    String title;
    int img;


    public MainMenuItems(String title, int img) {


        this.title = title;
        this.img = img;
    }


    public MainMenuItems() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
