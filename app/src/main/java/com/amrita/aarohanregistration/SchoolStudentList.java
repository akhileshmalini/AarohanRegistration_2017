package com.amrita.aarohanregistration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/10/2017.
 */

public class SchoolStudentList extends AppCompatActivity {
    FirebaseDatabase database;
    ProgressBar progressBar;
    Random_ShowStudentAdapter mAdapter;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_heading);
        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        database= FirebaseDatabase.getInstance();

        String schName =getIntent().getExtras().getString("School");
        final ArrayList<Event_Student_Group> schList =new ArrayList<>();
        textView= (TextView) findViewById(R.id.textView20);
        textView.setText(schName);

        DatabaseReference EventsRef = database.getReference("Schools").child(schName).child("Students");
        EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() == 0) {
                    progressBar.setVisibility(View.INVISIBLE);

                } else {
                    for (DataSnapshot event : snapshot.getChildren()) {
                        schList.add(new Event_Student_Group(event.getKey()));
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

        mAdapter = new Random_ShowStudentAdapter(schList,getApplicationContext(),SchoolStudentList.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
}
