package com.amrita.aarohanregistration.Events_Aarohan.Induvidual;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
 * Created by Akhilesh on 9/10/2017.
 */

public class Event_StudentList_Induvidual extends AppCompatActivity {
    TextView evTtitle;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    Event_ShowStudentAdapter_Induvidual mAdapter;
    FirebaseDatabase database;
    ArrayList<Event_Student_Group> stdList;
    String evName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();

        //Get Intent Extras
        evName = getIntent().getExtras().getString("Event");

        //View Bindings
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        evTtitle = (TextView) findViewById(R.id.txtTitle);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //Set Title as Event
        evTtitle.setText(evName);

        //Initialize ArrayList
        stdList = new ArrayList<>();

        refreshList();
    }


    void refreshList() {


        DatabaseReference EventsRef = database.getReference("Events").child(evName).child("Students");
        EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() == 0) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "No Students Found", Toast.LENGTH_SHORT).show();

                } else {
                    stdList.clear();
                    for (DataSnapshot event : snapshot.getChildren()) {
                        stdList.add(new Event_Student_Group(event.getKey().toString()));
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mAdapter = new Event_ShowStudentAdapter_Induvidual(stdList, getApplicationContext(), evName, Event_StudentList_Induvidual.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
        mAdapter.notifyDataSetChanged();

    }

}
