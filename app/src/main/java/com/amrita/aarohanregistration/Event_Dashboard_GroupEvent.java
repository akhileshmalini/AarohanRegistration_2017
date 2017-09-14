package com.amrita.aarohanregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akhilesh on 9/11/2017.
 */

public class Event_Dashboard_GroupEvent extends AppCompatActivity {

    String EventName,cate,grpCount,groupno,groupName;
    Button addGrp,viewGrps,studList,scanView,winners;
    FirebaseDatabase database;
    TextView e,c,gr;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_dashboard);
        database= FirebaseDatabase.getInstance();

        e= (TextView) findViewById(R.id.dashTitle);
        c= (TextView) findViewById(R.id.dashCat);
        gr= (TextView) findViewById(R.id.dashGrp);


        EventName =getIntent().getExtras().getString("EventName");
        cate=getIntent().getExtras().getString("Category");

        e.setText(EventName);
        c.setText(cate+" Category");

        DatabaseReference EventsRef = database.getReference("Events").child(EventName);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                grpCount=dataSnapshot.child("grpCount").getValue().toString();
                groupno=""+(dataSnapshot.child("Groups").getChildrenCount()+1);
                 groupName="GRP"+groupno;
                gr.setText("Students/Group : "+grpCount);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        EventsRef.addValueEventListener(postListener);


        addGrp= (Button) findViewById(R.id.button9);
        addGrp.setText("Create New Group");
        addGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Event_GroupDashboard.class);
                intent.putExtra("groupName",groupName);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });


        viewGrps= (Button) findViewById(R.id.button10);
        viewGrps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Event_ViewGroups.class);
                intent.putExtra("Event",EventName);
                intent.putExtra("GroupSize",""+grpCount);
                startActivity(intent);
            }
        });

        studList= (Button) findViewById(R.id.button11);
        studList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(Event_Dashboard_GroupEvent.this,Event_StudentList_GroupEvent.class);
                intent.putExtra("Event",EventName);
                startActivity(intent);

            }
        });


        scanView= (Button) findViewById(R.id.button13);
        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(Event_Dashboard_GroupEvent.this,Event_ShowStudentScanActivity_Group.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });

        winners= (Button) findViewById(R.id.button12);

        winners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Event_Dashboard_GroupEvent.this,Event_Winnner_GroupEvent.class);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });





    }






}
