package com.amrita.aarohanregistration.Feedback_Aarohan;

import android.app.Dialog;
import android.content.Intent;
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

public class FeedbackStudentProfile extends AppCompatActivity {

    Dialog rankDialog;

    FirebaseDatabase database;
    FeedbackEventsAdapter mAdapter;
    FeedbackAarohanAdapter mAdaptera;
    TextView a,b;

    ProgressBar progressBar,progressBar2;
    String arhnID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_studentprofile);

         arhnID=getIntent().getExtras().getString("arhnid");

        progressBar= (ProgressBar) findViewById(R.id.progressBar4);
        progressBar2= (ProgressBar) findViewById(R.id.progressBar5);

        a= (TextView) findViewById(R.id.textView9);
        b= (TextView) findViewById(R.id.textView11);
        database= FirebaseDatabase.getInstance();
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        studentIsValid();

    }


    void studentIsValid(){
        DatabaseReference studentsRef = database.getReference("Students");

        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(arhnID)) {
                    populateFileds();
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid Code "+arhnID+ " Please Scan again if nescessary",Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(FeedbackStudentProfile.this,FeedbackScanActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



    void populateFileds(){
        final ArrayList<FeedBackEvent> evList =new ArrayList<>();
        final ArrayList<FeedBackEvent> aarohanFeedback =new ArrayList<>();
        DatabaseReference fedRef = database.getReference("Students").child(arhnID).child("Feedback");

        fedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()<4){
                    aarohanFeedback.add(new FeedBackEvent("Question"+1,0,"How was Aarohan'17 ?"));
                    aarohanFeedback.add(new FeedBackEvent("Question"+2,0,"How was your experience interacting with the Team ?"));
                    aarohanFeedback.add(new FeedBackEvent("Question"+3,0,"How was the food and Hospitality ?"));
                    aarohanFeedback.add(new FeedBackEvent("Question"+4,0,"How was the Registration System ?"));


                }else{
                    int i=1;
                    for(DataSnapshot event : dataSnapshot.getChildren()) {

                    try {
                        float q1 = Float.valueOf(event.getValue().toString());
                        if(i==1){
                            aarohanFeedback.add(new FeedBackEvent("Question" + i, q1,"How was Aarohan'17 ?"));
                        }else if(i==2){
                            aarohanFeedback.add(new FeedBackEvent("Question" + i, q1,"How was your experience interacting with the Team ?"));
                        }else if(i==3){
                            aarohanFeedback.add(new FeedBackEvent("Question" + i, q1,"How was the food and Hospitality ?"));
                        }else if(i==4){
                            aarohanFeedback.add(new FeedBackEvent("Question" + i, q1,"How was the Registration System ?"));
                        }

                        i += 1;


                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();


                    }

                }
                }

                mAdaptera.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
                b.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference EventsRef = database.getReference("Students").child(arhnID).child("EventsAttended");
        EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot event : snapshot.getChildren()){
                    evList.add(new FeedBackEvent(event.getKey().toString(), Float.valueOf(event.getValue().toString())));
                    mAdapter.notifyDataSetChanged();
                    progressBar2.setVisibility(View.INVISIBLE);
                    a.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view5);
        RecyclerView aaroFeed = (RecyclerView) findViewById(R.id.recycleraarohanfeed);

        mAdaptera = new FeedbackAarohanAdapter(aarohanFeedback,getApplicationContext(),FeedbackStudentProfile.this,arhnID,database);
        RecyclerView.LayoutManager mLayoutManagera = new LinearLayoutManager(getApplicationContext());
        aaroFeed.setLayoutManager(mLayoutManagera);
        aaroFeed.setItemAnimator(new DefaultItemAnimator());
        aaroFeed.setAdapter(mAdaptera);

        mAdapter = new FeedbackEventsAdapter(evList,getApplicationContext(),FeedbackStudentProfile.this,EventsRef,arhnID,database);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }



}