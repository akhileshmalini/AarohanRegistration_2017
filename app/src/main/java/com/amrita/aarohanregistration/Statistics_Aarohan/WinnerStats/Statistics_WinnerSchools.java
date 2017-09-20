package com.amrita.aarohanregistration.Statistics_Aarohan.WinnerStats;

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
import com.amrita.aarohanregistration.Statistics_Aarohan.WinnerStats.WinnerSchoolAdapter;
import com.amrita.aarohanregistration.Statistics_Aarohan.WinnerStats.Winner_School;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/14/2017.
 */

public class Statistics_WinnerSchools extends AppCompatActivity {
    FirebaseDatabase database;
    WinnerSchoolAdapter mAdapter;
    ProgressBar progressBar;

    ArrayList<Winner_School> schoolList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= FirebaseDatabase.getInstance();

        schoolList=new ArrayList<>();
        progressBar= (ProgressBar) findViewById(R.id.progressBar3);

        TextView title= (TextView) findViewById(R.id.txtTitle);
        title.setText("Winners");

        DatabaseReference StdRef = database.getReference("Winners");
        StdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot Schools : dataSnapshot.getChildren()){
                    int p1=0,p2=0,p3=0,pcon=0,poth=0;
                    int tot=0;


                        for(DataSnapshot Events : Schools.getChildren()){
                            String Place = "";
                            int a,b,c;
                            for(DataSnapshot p : Events.getChildren()){
                                Place=p.getKey().toString();
                                a=0;
                                b=0;
                                a=0;
                                if(Place.equals("1st Place")) {
                                    p1 += 1;

                                }else if(Place.equals("2nd Place")){
                                    p2+=1;
                                }
                                else if(Place.equals("3rd Place")){
                                    p3+=1;
                                }
                                a =p1*5;
                                b=p2*3;
                                c=p3*1;

                                tot=a+b+c;
                                a=0;
                                b=0;
                                a=0;
                            }


                    }

                    schoolList.add(new Winner_School(Schools.getKey(),tot));
                    mAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new WinnerSchoolAdapter(schoolList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




    }
}
