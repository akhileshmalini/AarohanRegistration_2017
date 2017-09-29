package com.amrita.aarohanregistration;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Main_Menu extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.mainmenu);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view2);

        int imga[] = {R.drawable.lsevents,R.drawable.lsfeedback,R.drawable.lsstatistics,R.drawable.lsstddetails};
        String names[] ={"", "","",""};
         ArrayList<MainMenuItems> items =new ArrayList<>();

        for(int i=0;i<imga.length;i++){
            items.add(new MainMenuItems(names[i],imga[i]));
        }

        MainMenuAdapter mAdapter = new MainMenuAdapter(items,getApplicationContext(),Main_Menu.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);





    }




}


