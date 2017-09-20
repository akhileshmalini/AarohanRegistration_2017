package com.amrita.aarohanregistration.Events_Aarohan;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/7/2017.
 */

public class Event_List_Senior extends AppCompatActivity {

    FirebaseDatabase database;
    Events_Adapter mAdapter;
    ProgressBar progressBar;
    TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title= (TextView) findViewById(R.id.txtTitle);
        title.setText("Senior Category");


        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        database= FirebaseDatabase.getInstance();
        final ArrayList<Events> evList =new ArrayList<>();
        DatabaseReference EventsRef = database.getReference("Events");
        EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
               for(DataSnapshot event : snapshot.getChildren()){
                    if(event.child("Category").getValue().toString().equals("Senior")) {
                        evList.add(new Events(event.getKey().toString(), event.child("Category").getValue().toString(), Integer.parseInt(event.child("grpCount").getValue().toString())));
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

         mAdapter = new Events_Adapter(evList,getApplicationContext(),Event_List_Senior.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




    }



}
