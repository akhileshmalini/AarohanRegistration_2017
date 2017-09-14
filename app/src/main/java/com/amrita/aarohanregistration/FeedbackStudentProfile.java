package com.amrita.aarohanregistration;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.name;

/**
 * Created by Akhilesh on 9/7/2017.
 */

public class FeedbackStudentProfile extends AppCompatActivity {

    Dialog rankDialog;

    FirebaseDatabase database;
    FeedbackEventsAdapter mAdapter;
    FeedbackAarohanAdapter mAdaptera;
    ProgressBar progressBar;
    String arhnID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_studentprofile);

         arhnID=getIntent().getExtras().getString("arhnid");

        progressBar= (ProgressBar) findViewById(R.id.progressBar4);
        database= FirebaseDatabase.getInstance();

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
                    for(int i=1;i<=4;i++)
                    aarohanFeedback.add(new FeedBackEvent("Question"+i,0));

                }else{
                    int i=1;
                    for(DataSnapshot event : dataSnapshot.getChildren()) {

                    try {
                        float q1 = Float.valueOf(event.getValue().toString());
                        aarohanFeedback.add(new FeedBackEvent("Question" + i, q1));
                        i += 1;


                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();


                    }

                }
                }

                mAdaptera.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

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
                    progressBar.setVisibility(View.INVISIBLE);
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