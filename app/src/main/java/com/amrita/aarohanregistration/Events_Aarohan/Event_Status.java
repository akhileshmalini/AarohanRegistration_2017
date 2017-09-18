package com.amrita.aarohanregistration.Events_Aarohan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.amrita.aarohanregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akhilesh on 9/15/2017.
 */

public class Event_Status extends AppCompatActivity {
    FirebaseDatabase database;
    String EventName;
    DatabaseReference EventsRef,exRef;
    TextView txt_stat;
    RadioButton radio_started,radio_progress,radio_awaited,radio_announced;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_status);
        database = FirebaseDatabase.getInstance();
        //Get Intent Extras
        EventName = getIntent().getExtras().getString("EventName");

        //Initialize Firebase reference
        EventsRef = database.getReference("Events").child(EventName).child("EventStatus");
        exRef = database.getReference("Events").child(EventName);

        //View Bindings
        txt_stat= (TextView) findViewById(R.id.textView42);
        radio_started= (RadioButton) findViewById(R.id.radioButton);
        radio_progress= (RadioButton) findViewById(R.id.radioButton2);
        radio_awaited= (RadioButton) findViewById(R.id.radioButton3);
        radio_announced= (RadioButton) findViewById(R.id.radioButton4);

        //Get Current Status From Firebase
        exRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("EventStatus")){
                    String stat=dataSnapshot.child("EventStatus").getValue().toString();
                    txt_stat.setText(stat);

                    if(stat.equals("Started")){
                        //Started
                        radio_started.setChecked(true);
                        radio_progress.setChecked(false);
                        radio_awaited.setChecked(false);
                        radio_announced.setChecked(false);

                    }else if(stat.equals("In Progress")){
                        //In Progress
                        radio_started.setChecked(false);
                        radio_progress.setChecked(true);
                        radio_awaited.setChecked(false);
                        radio_announced.setChecked(false);


                    }else if(stat.equals("Results Awaited")){
                        //Results Awaited
                        radio_started.setChecked(false);
                        radio_progress.setChecked(false);
                        radio_awaited.setChecked(true);
                        radio_announced.setChecked(false);


                    }else if(stat.equals("Results Announced")){
                        //Results Announced
                        radio_started.setChecked(false);
                        radio_progress.setChecked(false);
                        radio_awaited.setChecked(false);
                        radio_announced.setChecked(true);


                    }


                }else{
                    txt_stat.setText("No Status available");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Radio Button on selected sets the Status on Firebase
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked)
                    EventsRef.setValue("Started");
                        txt_stat.setText("Started");

                break;
            case R.id.radioButton2:
                if (checked)
                    EventsRef.setValue("In Progress");
                txt_stat.setText("In Progress");

                break;
            case R.id.radioButton3:
                if (checked)
                    EventsRef.setValue("Results Awaited");
                txt_stat.setText("Results Awaited");

                break;
            case R.id.radioButton4:
                if (checked)
                    EventsRef.setValue("Results Announced");
                txt_stat.setText("Results Announced");

                break;
        }
    }
}
