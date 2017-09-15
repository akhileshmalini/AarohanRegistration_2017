package com.amrita.aarohanregistration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Akhilesh on 9/15/2017.
 */

public class Event_Status extends AppCompatActivity {
    FirebaseDatabase database;
    String EventName;
    DatabaseReference EventsRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_status);
        database= FirebaseDatabase.getInstance();

        EventName = getIntent().getExtras().getString("EventName");
        EventsRef = database.getReference("Events").child(EventName).child("EventStatus");


    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                EventsRef.setValue("Started");
                    break;
            case R.id.radioButton2:
                if (checked)
                    EventsRef.setValue("In Progress");
                    break;
            case R.id.radioButton3:
                if (checked)
                    EventsRef.setValue("Results Awaited");
                    break;
            case R.id.radioButton4:
                if (checked)
                    EventsRef.setValue("Results Announced");
                    break;
        }
    }
}
