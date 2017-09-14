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
 * Created by Akhilesh on 9/12/2017.
 */

public class Event_ViewGroups extends AppCompatActivity {
    FirebaseDatabase database;
    ProgressBar progressBar;
    Event_ShowGroupsAdapter mAdapter;
    int grp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        database= FirebaseDatabase.getInstance();

        String evName =getIntent().getExtras().getString("Event");
        String g =getIntent().getExtras().getString("GroupSize");
        grp=Integer.parseInt(g);

        final ArrayList<Event_Student_Group_Count> evList =new ArrayList<>();

        DatabaseReference EventsRef = database.getReference("Events").child(evName).child("Groups");
        EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getChildrenCount() == 0) {
                    progressBar.setVisibility(View.INVISIBLE);

                } else {

                    for (DataSnapshot event : snapshot.getChildren()) {
                        int a= (int) event.getChildrenCount();
                        evList.add(new Event_Student_Group_Count(event.getKey().toString(),a));
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new Event_ShowGroupsAdapter(evList,getApplicationContext(),evName,database,grp);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
}
