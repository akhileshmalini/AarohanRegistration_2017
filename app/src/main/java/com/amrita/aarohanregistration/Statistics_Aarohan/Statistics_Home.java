package com.amrita.aarohanregistration.Statistics_Aarohan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.amrita.aarohanregistration.MainMenuItems;
import com.amrita.aarohanregistration.R;

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Statistics_Home extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view2);




        int imga[] = {R.drawable.lsstdstats,R.drawable.lsevstats, R.drawable.lsschoolstats,R.drawable.lsfeedstats,R.drawable.lswinnerstats,R.drawable.lsevstatus};
        String names[] ={"","","","","",""};
        ArrayList<MainMenuItems> items =new ArrayList<>();

        for(int i=0;i<imga.length;i++){
            items.add(new MainMenuItems(names[i],imga[i]));
        }

        Statistics_MenuAdapter mAdapter = new Statistics_MenuAdapter(items,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
}
