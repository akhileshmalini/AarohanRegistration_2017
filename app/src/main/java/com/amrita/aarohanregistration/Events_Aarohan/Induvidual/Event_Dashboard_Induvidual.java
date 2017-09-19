package com.amrita.aarohanregistration.Events_Aarohan.Induvidual;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amrita.aarohanregistration.Events_Aarohan.Event_Status;
import com.amrita.aarohanregistration.Events_Aarohan.Groups.Event_ShowStudentScanActivity_Group;
import com.amrita.aarohanregistration.R;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Akhilesh on 9/11/2017.
 */

public class Event_Dashboard_Induvidual extends AppCompatActivity {

    String EventName,cate,grpCount,groupno,groupName;
    Button btn_addStd,btn_viewGrps,btn_studList,btn_scanView,btn_evWinners,btn_setStatus;
    FirebaseDatabase database;
    TextView txt_evName,txt_Category;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_dashboard_inuvidual);
        database= FirebaseDatabase.getInstance();
        
        //Get intent Extras
        EventName =getIntent().getExtras().getString("EventName");
        cate=getIntent().getExtras().getString("Category");


        //View Bindings
        txt_evName= (TextView) findViewById(R.id.dashTitle);
        txt_Category= (TextView) findViewById(R.id.dashCat);
        btn_addStd= (Button) findViewById(R.id.button9);
        btn_studList= (Button) findViewById(R.id.button11);
        btn_scanView= (Button) findViewById(R.id.button13);
        btn_setStatus= (Button) findViewById(R.id.button17);
        btn_evWinners= (Button) findViewById(R.id.button12);


        //Set Event Title and Category
        txt_evName.setText(EventName);
        txt_Category.setText(cate+" Category");



        //Button To Add Student to Event
        btn_addStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * Intent Extras rquired
                * EventName
                * */
                Intent intent =new Intent(getApplicationContext(),Event_StudentScanActivity_Induvidual.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });



        //Button to Display List of Student
        btn_studList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * Intent Extras rquired
                * EventName
                * */
                Intent intent =new Intent(Event_Dashboard_Induvidual.this,Event_StudentList_Induvidual.class);
                intent.putExtra("Event",EventName);
                startActivity(intent);

            }
        });

        //Button to Scan and View a Student
        btn_scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*
                * Intent Extras rquired
                * EventName
                * */
                Intent intent=new Intent(Event_Dashboard_Induvidual.this,Event_ShowStudentScanActivity_Group.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });

        //Button to Set Status of Event
        btn_setStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*
                * Intent Extras rquired
                * EventName
                * */
                Intent intent =new Intent(Event_Dashboard_Induvidual.this,Event_Status.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });


        //Button to set Winners of Event
        btn_evWinners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * Intent Extras rquired
                * EventName
                * */
                Intent intent=new Intent(Event_Dashboard_Induvidual.this,Event_Winnner_Induvidual.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });





    }






}
