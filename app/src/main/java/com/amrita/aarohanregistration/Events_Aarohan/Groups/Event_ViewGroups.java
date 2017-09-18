package com.amrita.aarohanregistration.Events_Aarohan.Groups;

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

import com.amrita.aarohanregistration.Events_Aarohan.Event_Student_Group_Count;
import com.amrita.aarohanregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/12/2017.
 */
/**
 * This Activity shows All the Groups present in an Event as well as the status of Filling 

 */

public class Event_ViewGroups extends AppCompatActivity {
    FirebaseDatabase database;
    ProgressBar progressBar;
    TextView title;
    RecyclerView recyclerView;
    Event_ShowGroupsAdapter mAdapter;
    int grpSize;

    ArrayList<Event_Student_Group_Count> evList;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Getting Intent Extras
        String evName =getIntent().getExtras().getString("Event");
        String grpCount =getIntent().getExtras().getString("GroupSize");

        //Get Int value of grpCount
        grpSize=Integer.parseInt(grpCount);
        
        //View Bindings
        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        title= (TextView) findViewById(R.id.txtTitle);
        //Database Instance
        database= FirebaseDatabase.getInstance();

        //Set Page Title
        title.setText("Groups");

        //ArrayList Initialization
        evList =new ArrayList<>();

        DatabaseReference EventsRef = database.getReference("Events").child(evName).child("Groups");

        //Constantly Monitor the List of Students in Firebase.
            ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getChildrenCount() == 0) {
                    //Basically Means There are no Groups in the Event
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"No Groups added | List Empty",Toast.LENGTH_LONG).show();

                } else {
                    evList.clear();
                    for (DataSnapshot event : snapshot.getChildren()) {
                        //currentCount
                        int curCount= (int) event.getChildrenCount();
                        evList.add(new Event_Student_Group_Count(event.getKey().toString(),curCount));
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        EventsRef.addValueEventListener(postListener);

        //Populate Adapter
        mAdapter = new Event_ShowGroupsAdapter(evList,getApplicationContext(),evName,database,grpSize);

        //Set Adapter to RecyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }
}
