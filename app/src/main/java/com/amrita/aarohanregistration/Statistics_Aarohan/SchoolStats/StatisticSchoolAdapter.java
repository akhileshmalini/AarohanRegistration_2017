package com.amrita.aarohanregistration.Statistics_Aarohan.SchoolStats;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.SchoolStudentList;
import com.amrita.aarohanregistration.Statistics_Aarohan.Statistic;

import java.util.List;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class StatisticSchoolAdapter extends RecyclerView.Adapter<StatisticSchoolAdapter.MyViewHolder> {

    private List<Statistic> statList;
    Context mContext;





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,fedstat;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.evName);
            fedstat = (TextView) view.findViewById(R.id.evStat);

        }
    }


    public StatisticSchoolAdapter(List<Statistic> statList, Context mContext) {
        this.statList = statList;
        this.mContext=mContext;

    }

    @Override
    public StatisticSchoolAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_listitem, parent, false);

        return new StatisticSchoolAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StatisticSchoolAdapter.MyViewHolder holder, final int position) {
        final Statistic stat = statList.get(position);
        holder.title.setText(stat.getDescription());
        holder.fedstat.setText(stat.getStat());


        final String Schol=stat.getDescription().replace(" (Student Count)","");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!stat.getDescription().equals("Total Number of Schools Registered")) {
                    Intent intent = new Intent(mContext, SchoolStudentList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    intent.putExtra("School",Schol );
                    mContext.startActivity(intent);
                }
            }
        });


      
        

    }

    @Override
    public int getItemCount() {
        return statList.size();
    }






}