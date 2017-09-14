package com.amrita.aarohanregistration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Student_Statistics extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseDatabase database;
    StatisticAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final ArrayList<Statistic> arrayList =new ArrayList<>();

        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        database= FirebaseDatabase.getInstance();
        DatabaseReference ref =database.getReference("Students");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long total=dataSnapshot.getChildrenCount();
                arrayList.add(new Statistic("Student Count Overall",""+total));
                int countMale=0,countFemale=0,countSenior=0,countJunior=0,countSeniorMale=0,countSeniorFemale=0,countJuniorMale=0,countJuniorFemale=0;
                for(DataSnapshot student : dataSnapshot.getChildren()){
                    if(student.child("category").getValue().equals("Senior") && student.child("gender").getValue().equals("Male")){
                        countSeniorMale+=1;
                    }
                    if(student.child("category").getValue().equals("Junior") && student.child("gender").getValue().equals("Male")){
                        countJuniorMale+=1;
                    }

                    if(student.child("category").getValue().equals("Senior")){
                        countSenior+=1;
                    }

                    if(student.child("gender").getValue().equals("Male")){
                        countMale+=1;
                    }
                }
                countFemale=(int)dataSnapshot.getChildrenCount()-countMale;
                countJunior=(int)dataSnapshot.getChildrenCount()-countSenior;

                countSeniorFemale=countSenior-countSeniorMale;
                countJuniorFemale=countJunior-countJuniorMale;




                arrayList.add(new Statistic("Senior Students",""+countSenior));
                arrayList.add(new Statistic("Junior Students",""+countJunior));
                arrayList.add(new Statistic("Male Students",""+countMale));
                arrayList.add(new Statistic("Female Students",""+countFemale));
                arrayList.add(new Statistic("Senior Male Students",""+countSeniorMale));
                arrayList.add(new Statistic("Senior Female Students",""+countSeniorFemale));
                arrayList.add(new Statistic("Junior Male Students",""+countJuniorMale));
                arrayList.add(new Statistic("Junior Female Students",""+countJuniorFemale));


                progressBar.setVisibility(View.INVISIBLE);
                mAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new StatisticAdapter(arrayList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
}
