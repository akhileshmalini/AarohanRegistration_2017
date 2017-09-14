package com.amrita.aarohanregistration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Akhilesh on 9/14/2017.
 */

public class Winner_GroupList extends AppCompatActivity {
    FirebaseDatabase database;
     DatabaseReference WinnerRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= FirebaseDatabase.getInstance();
        String EventName=getIntent().getExtras().getString("EventName");
        String Groups=getIntent().getExtras().getString("Groups");
        WinnerRef= database.getReference("Events").child(EventName).child("Winners");



    }
}
