package com.amrita.aarohanregistration;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Faculty {

    String name,email;
    String phoneno;

    public Faculty() {
    }

    public Faculty(String name, String email, String phoneno) {

        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
