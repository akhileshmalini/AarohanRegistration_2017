package com.amrita.aarohanregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Akhilesh on 9/11/2017.
 */

public class Event_Dashboard_Induvidual extends AppCompatActivity {

    String EventName,cate,grpCount,groupno,groupName;
    Button addStd,viewGrps,studList,scanView,winners,status;
    FirebaseDatabase database;
    TextView e,c,gr;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_dashboard_inuvidual);
        database= FirebaseDatabase.getInstance();

        e= (TextView) findViewById(R.id.dashTitle);
        c= (TextView) findViewById(R.id.dashCat);
        gr= (TextView) findViewById(R.id.dashGrp);
        EventName =getIntent().getExtras().getString("EventName");
        cate=getIntent().getExtras().getString("Category");

        e.setText(EventName);
        c.setText(cate+" Category");




        addStd= (Button) findViewById(R.id.button9);
        addStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Event_StudentScanActivity_Induvidual.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });




        studList= (Button) findViewById(R.id.button11);
        studList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(Event_Dashboard_Induvidual.this,Event_StudentList_Induvidual.class);
                intent.putExtra("Event",EventName);
                startActivity(intent);

            }
        });


        scanView= (Button) findViewById(R.id.button13);
        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(Event_Dashboard_Induvidual.this,Event_ShowStudentScanActivity_Group.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });

        status= (Button) findViewById(R.id.button17);
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Event_Dashboard_Induvidual.this,Event_Status.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });

        winners= (Button) findViewById(R.id.button12);

        winners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Event_Dashboard_Induvidual.this,Event_Winnner_Induvidual.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });





    }






}
