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
import android.widget.Toast;

import com.amrita.aarohanregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/14/2017.
 */

public class Winner_GroupList extends AppCompatActivity {
    FirebaseDatabase database;
     DatabaseReference WinnerRef;
    ArrayList<String> winners;
    Winner_ShowStudentAdapter mAdapter;
    ProgressBar progressBar;
     String EventName,Place,Groups;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= FirebaseDatabase.getInstance();
        progressBar= (ProgressBar) findViewById(R.id.progressBar3);

        TextView title= (TextView) findViewById(R.id.txtTitle);
        title.setText("Students");

        EventName=getIntent().getExtras().getString("EventName");
        Groups=getIntent().getExtras().getString("Groups");
       Place=getIntent().getExtras().getString("Place");

        winners=new ArrayList<>();
        WinnerRef = database.getReference("Events").child(EventName).child("Winners").child(Place);

            WinnerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getValue().toString().contains("GRP")){

                            getGroupInfo();
                        }else if(dataSnapshot.getValue().toString().contains("ARHN")){
                            winners.add(dataSnapshot.getValue().toString());
                            mAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);

                        }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new Winner_ShowStudentAdapter(winners,getApplicationContext(),EventName,Winner_GroupList.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }


    void getGroupInfo(){
        DatabaseReference StdRef = database.getReference("Events").child(EventName).child("Groups").child(Groups);
        StdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ar:dataSnapshot.getChildren()){
                    winners.add(ar.getKey().toString());
                    mAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
