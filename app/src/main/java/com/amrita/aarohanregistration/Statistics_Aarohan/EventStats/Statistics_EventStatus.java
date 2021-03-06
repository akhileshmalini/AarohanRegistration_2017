package com.amrita.aarohanregistration.Statistics_Aarohan.EventStats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.Statistics_Aarohan.Statistic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/7/2017.
 */

public class Statistics_EventStatus extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ProgressBar progressBar;
    FirebaseDatabase database;
    StatisticEventAdapter mAdapter;
    ArrayList<Statistic> arrayList;
    ArrayList<Statistic> original,temp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList =new ArrayList<>();
        original=new ArrayList<>();
        temp =new ArrayList<>();
        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        database= FirebaseDatabase.getInstance();
        DatabaseReference ref =database.getReference("Events");
        TextView title= (TextView) findViewById(R.id.txtTitle);
        title.setText("Event Status");


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long total=dataSnapshot.getChildrenCount();

                for(DataSnapshot Event : dataSnapshot.getChildren()){
                    if(!Event.hasChild("EventStatus")) {
                        arrayList.add(new Statistic(Event.getKey(), "No Status Available"));
                    }else{

                        arrayList.add(new Statistic(Event.getKey(),Event.child("EventStatus").getValue().toString()));

                    }
                    original.addAll(arrayList);

                }

                progressBar.setVisibility(View.INVISIBLE);
                mAdapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new StatisticEventAdapter(arrayList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {




        if(query.equals("")){
            arrayList.clear();
            arrayList.addAll(original);
            mAdapter.notifyDataSetChanged();

        }else {
            temp.clear();
            for (Statistic student : arrayList) {
                if (student.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    temp.add(student);
                }
            }
            if (temp.size() == 0) {
                arrayList.clear();

                arrayList.addAll(original);
                mAdapter.notifyDataSetChanged();

            } else {
                arrayList.clear();
                arrayList.addAll(temp);
                mAdapter.notifyDataSetChanged();
            }

        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if(query.equals("")){
            arrayList.clear();
            arrayList.addAll(original);
            mAdapter.notifyDataSetChanged();
        }else {
            temp.clear();

            for (Statistic student : arrayList) {
                if (student.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    temp.add(student);
                }
            }
            if (temp.size() == 0) {
                arrayList.clear();

                Toast.makeText(getApplicationContext(), "No School Found", Toast.LENGTH_SHORT).show();
                arrayList.addAll(original);
                mAdapter.notifyDataSetChanged();

            } else {
                arrayList.clear();
                arrayList.addAll(temp);
                mAdapter.notifyDataSetChanged();
            }

        }

        return true;
    }




}
