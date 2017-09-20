package com.amrita.aarohanregistration.Statistics_Aarohan.FeedbackStats;

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
 * Created by Akhilesh on 9/9/2017.
 */

public class Statistics_Feedback extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseDatabase database;
    StatisticFeedbackAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final ArrayList<Statistic> arrayList =new ArrayList<>();

        TextView title= (TextView) findViewById(R.id.txtTitle);
        title.setText("Feedback Statistics");


        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        database= FirebaseDatabase.getInstance();
        DatabaseReference ref =database.getReference("Events");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot event : dataSnapshot.getChildren()){
                    final int[] r5 = {0};
                    final int[] r4 = {0};
                    final int[] r3 = {0};
                    final int[] r2 = {0};
                    final int[] r1 = {0};
                    for(DataSnapshot students: event.child("Students").getChildren()){

                        if(Integer.valueOf(students.getValue().toString())==5){
                        r5[0] +=1;
                        }else if(Integer.valueOf(students.getValue().toString())==4){
                            r4[0] +=1;
                        }else if(Integer.valueOf(students.getValue().toString())==3){
                            r3[0] +=1;
                        }else if(Integer.valueOf(students.getValue().toString())==2){
                            r2[0] +=1;
                        }else if(Integer.valueOf(students.getValue().toString())==1){
                            r1[0] +=1;
                        }
                    }

                    arrayList.add(new Statistic(event.getKey(),r5[0]+","+r4[0]+","+r3[0]+","+r2[0]+","+r1[0]));

                }
                progressBar.setVisibility(View.INVISIBLE);
                mAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference ref2 =database.getReference("Feedback");

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot Questions : dataSnapshot.getChildren()) {
                    final int[] r5 = {0};
                    final int[] r4 = {0};
                    final int[] r3 = {0};
                    final int[] r2 = {0};
                    final int[] r1 = {0};
                    for(DataSnapshot rt : Questions.getChildren()) {

                        if(Integer.valueOf(rt.getValue().toString())==5){
                            r5[0] +=1;
                        }else if(Integer.valueOf(rt.getValue().toString())==4){
                            r4[0] +=1;
                        }else if(Integer.valueOf(rt.getValue().toString())==3){
                            r3[0] +=1;
                        }else if(Integer.valueOf(rt.getValue().toString())==2){
                            r2[0] +=1;
                        }else if(Integer.valueOf(rt.getValue().toString())==1){
                            r1[0] +=1;
                        }
                    }

                    arrayList.add(new Statistic(Questions.getKey(),r5[0]+","+r4[0]+","+r3[0]+","+r2[0]+","+r1[0]));


                    }

                progressBar.setVisibility(View.INVISIBLE);
                mAdapter.notifyDataSetChanged();


            }





            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new StatisticFeedbackAdapter(arrayList,getApplicationContext(),Statistics_Feedback.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
}
