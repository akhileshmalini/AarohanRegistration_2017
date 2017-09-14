package com.amrita.aarohanregistration;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class School_Statistics extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ProgressBar progressBar;
    FirebaseDatabase database;
    StatisticSchoolAdapter mAdapter;
    ArrayList<Statistic> arrayList;
    ArrayList<Statistic> arrayListCopy;

    RecyclerView recyclerView;

    ArrayList<Statistic> original,temp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          arrayList =new ArrayList<>();
        progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        database= FirebaseDatabase.getInstance();
        DatabaseReference ref =database.getReference("Schools");

        Query sort = ref.orderByKey();

         original=new ArrayList<>();
        temp =new ArrayList<>();

        sort.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long total=dataSnapshot.getChildrenCount();
                arrayList.add(new Statistic("Total Number of Schools Registered",""+total));

                for(DataSnapshot School : dataSnapshot.getChildren()){


                    arrayList.add(new Statistic(School.getKey()+" (Student Count)",""+School.child("Students").getChildrenCount()));

                }
                original.addAll(arrayList);


                progressBar.setVisibility(View.INVISIBLE);
                mAdapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


         recyclerView = (RecyclerView) findViewById(R.id.recycler_view);




        mAdapter = new StatisticSchoolAdapter(arrayList,getApplicationContext());
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
            for (Statistic statistic : arrayList) {
                if (statistic.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    temp.add(statistic);
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

            for (Statistic statistic : arrayList) {
                if (statistic.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    temp.add(statistic);
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
