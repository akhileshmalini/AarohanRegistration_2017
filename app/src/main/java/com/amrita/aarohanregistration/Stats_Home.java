package com.amrita.aarohanregistration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Stats_Home extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view2);

        int imga[] = {R.drawable.students,R.drawable.events, R.drawable.school,R.drawable.bgfeedback,R.drawable.school,R.drawable.events};
        String names[] ={"Student Statistics", "Event Statistics","Schoolwise Statistics","Feedback Statistics","Winners Statistics","Event Status"};
        ArrayList<MainMenuItems> items =new ArrayList<>();

        for(int i=0;i<imga.length;i++){
            items.add(new MainMenuItems(names[i],imga[i]));
        }

        StatsMenuAdapter mAdapter = new StatsMenuAdapter(items,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
}
