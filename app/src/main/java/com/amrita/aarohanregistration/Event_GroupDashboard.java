package com.amrita.aarohanregistration;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/11/2017.
 */

public class Event_GroupDashboard extends AppCompatActivity {

    Button Add;
    String EventName,groupName,grpCount;
    FirebaseDatabase database;
    ProgressBar progressBar;
    Event_ShowStudentAdapter mAdapter;
    TextView textView,txtStat;
    int grpsize;
    ArrayList<Event_Student_Group> evList;
    DatabaseReference EventsRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_dash);
        database= FirebaseDatabase.getInstance();

        EventName =getIntent().getExtras().getString("EventName");
        groupName=getIntent().getExtras().getString("groupName");

        textView= (TextView) findViewById(R.id.txtTitle);
        textView.setText(groupName);

        txtStat= (TextView) findViewById(R.id.txtStat);
        txtStat.setVisibility(View.INVISIBLE);

        progressBar= (ProgressBar) findViewById(R.id.progressBar5);


        evList  =new ArrayList<>();

        DatabaseReference evRef = database.getReference("Events").child(EventName);
        EventsRef= database.getReference("Events").child(EventName).child("Groups").child(groupName);


        evRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                grpCount=dataSnapshot.child("grpCount").getValue().toString();
                grpsize=Integer.parseInt(grpCount);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        refreshList();





        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        mAdapter = new Event_ShowStudentAdapter(evList,getApplicationContext(),EventName,Event_GroupDashboard.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




        Add = (Button) findViewById(R.id.btnAddStudent);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(),""+dataSnapshot.getChildrenCount(),Toast.LENGTH_SHORT).show();

                        if(dataSnapshot.getChildrenCount()<grpsize){
                            Intent intent =new Intent(getApplicationContext(),Event_StudentScanActivity_GroupEvent.class);
                            intent.putExtra("groupName",groupName);
                            intent.putExtra("EventName",EventName);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Group Full", Toast.LENGTH_SHORT).show();
                            txtStat.setVisibility(View.VISIBLE);
                        txtStat.setText("Go to Event Dashboard to Create new Group");

                    }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
    }




    void refreshList(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    progressBar.setVisibility(View.INVISIBLE);
                    txtStat.setVisibility(View.VISIBLE);
                    txtStat.setText("No Students Added to Group");
                } else {
                    evList.clear();
                    for (DataSnapshot event : dataSnapshot.getChildren()) {
                        evList.add(new Event_Student_Group(event.getKey().toString()));
                        mAdapter.notifyDataSetChanged();
                        txtStat.setVisibility(View.INVISIBLE);
                        txtStat.setText("");
                        progressBar.setVisibility(View.INVISIBLE);
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
