package com.amrita.aarohanregistration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/14/2017.
 */

public class Winner_EventsList extends AppCompatActivity {

    FirebaseDatabase database;
    WinnerEventsAdapter mAdapter;
    ProgressBar progressBar;

    ArrayList<Winner_Events> eventList;
    String SchoolName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= FirebaseDatabase.getInstance();

        eventList=new ArrayList<>();
       SchoolName=getIntent().getExtras().getString("School");

        progressBar= (ProgressBar) findViewById(R.id.progressBar3);

        DatabaseReference StdRef = database.getReference("Winners").child(SchoolName);
        StdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot Events : dataSnapshot.getChildren()){
                            String place="";
                            for(DataSnapshot p : Events.getChildren()){
                                place=p.getKey().toString();
                            }

                        eventList.add(new Winner_Events(Events.getKey(),place));
                    mAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);





                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new WinnerEventsAdapter(eventList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




    }
    
    
}
