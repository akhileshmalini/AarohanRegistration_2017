package com.amrita.aarohanregistration.Statistics_Aarohan.EventStats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.Statistics_Aarohan.Statistic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/7/2017.
 */

public class Statistics_EventStatus extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseDatabase database;
    StatisticEventAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Statistic> arrayList =new ArrayList<>();

        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        database= FirebaseDatabase.getInstance();
        DatabaseReference ref =database.getReference("Events");
        TextView title= (TextView) findViewById(R.id.txtTitle);
        title.setText("Event Status");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long total=dataSnapshot.getChildrenCount();

                for(DataSnapshot Event : dataSnapshot.getChildren()){
                    if(!Event.hasChild("EventStatus")) {
                        arrayList.add(new Statistic(Event.getKey(), "No Status Available"));
                    }else{

                        arrayList.add(new Statistic(Event.getKey(),Event.child("EventStatus").getValue().toString()));

                    }

                }

                progressBar.setVisibility(View.INVISIBLE);
                mAdapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new StatisticEventAdapter(arrayList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }



}