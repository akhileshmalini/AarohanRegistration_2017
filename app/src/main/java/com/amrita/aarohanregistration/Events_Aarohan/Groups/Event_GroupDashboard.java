package com.amrita.aarohanregistration.Events_Aarohan.Groups;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.Events_Aarohan.Event_Student_Group;
import com.amrita.aarohanregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/11/2017.
 */

/**
 * The Group dashboard allows an Event Coordinator to add students Group by Group to the Event.
 */

public class Event_GroupDashboard extends AppCompatActivity {

    Button btn_addStd;
    TextView txt_grpName, txt_Status;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference EventsRef,GroupRef;

    Event_ShowStudentAdapter mAdapter;
    ArrayList<Event_Student_Group> stdList;

    String EventName, groupName, grpCount;
    int grpSize;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_dash);
        database = FirebaseDatabase.getInstance();

        //Getting Intent Extras
        EventName = getIntent().getExtras().getString("EventName");
        groupName = getIntent().getExtras().getString("groupName");

        //Retrieve No. of People allowed in a group
        //Note, Although the same value can be passed via Intent, there are a few Activities
        //that do not have the Count available to pass as Extra
        //Therefore this is the best method
        GroupRef=database.getReference("Events").child(EventName).child("grpCount");
        GroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                grpCount=dataSnapshot.getValue().toString();
                grpSize=Integer.parseInt(grpCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        

        //View Bindings
        txt_grpName = (TextView) findViewById(R.id.txtTitle);
        txt_Status = (TextView) findViewById(R.id.txtStat);
        progressBar = (ProgressBar) findViewById(R.id.progressBar5);
        btn_addStd = (Button) findViewById(R.id.btnAddStudent);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);

        //Set txt_grpName as the Group Name
        txt_grpName.setText(groupName);

        //Initialize Array List
        //Event_Student_Group Constructor (String stdName)
        stdList = new ArrayList<>();

        //Initializing the Reference that wil be used.
        EventsRef = database.getReference("Events").child(EventName).child("Groups").child(groupName);


        //Find Students Added to Group
        refreshList();

        //Populate Adapter
        mAdapter = new Event_ShowStudentAdapter(stdList, getApplicationContext(), EventName, Event_GroupDashboard.this);

        //Set RecyclerView Elements
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //Button Add Student will check if the group limit allows to add more students and then redirect to Add Student
        btn_addStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //-----------------------------------------------------------------------------------------------
                EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() < grpSize) {
                            //This Condition means the Number of Students present in the group is Less Than the permissible Limit
                             /*Required Extras for Group Dasboard
                                *GroupName
                                *EventName
                             */
                            Intent intent = new Intent(getApplicationContext(), Event_StudentScanActivity_GroupEvent.class);
                            intent.putExtra("groupName", groupName);
                            intent.putExtra("EventName", EventName);
                            startActivity(intent);
                        } else {
                            //This Condition means the Number of Students present in the group exceeds the permissible Limit
                            Toast.makeText(getApplicationContext(), "Group Full", Toast.LENGTH_SHORT).show();
                            txt_Status.setText("Group Full, Go to Event Dashboard to Create new Group");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                //-----------------------------------------------------------------------------------------------


            }
        });
    }


    void refreshList() {

        //Constantly Monitor the List of Students in Firebase.
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Calculate Number of Students Currently in Group
                int numStd= (int) dataSnapshot.getChildrenCount();

                if (numStd == 0) {
                    //There are no Students in this Group
                    progressBar.setVisibility(View.INVISIBLE);
                    txt_Status.setText("No Students Added to Group | Group Empty ");
                } else {
                    //There are Some People in the Group Already

                    //Calculate the Number of people left to be Added
                    int stdLeft=grpSize-numStd;
                    stdList.clear();
                    for (DataSnapshot event : dataSnapshot.getChildren()) {
                        //GET the ID of Every Student and Update arrayList
                        stdList.add(new Event_Student_Group(event.getKey().toString()));
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                        //Set Status According to Number of People Left to be added
                        if(stdLeft!=0) {
                            txt_Status.setText(numStd + " Student Added, "+stdLeft+ " Left to be added");
                        }else{
                            txt_Status.setText("Group Full, Go to Event Dashboard to Create new Group");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        EventsRef.addValueEventListener(postListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }
}
