package com.amrita.aarohanregistration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class StatisticEventAdapter extends RecyclerView.Adapter<StatisticEventAdapter.MyViewHolder> {

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


    public StatisticEventAdapter(List<Statistic> statList, Context mContext) {
        this.statList = statList;
        this.mContext=mContext;

    }

    @Override
    public StatisticEventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_listitem, parent, false);

        return new StatisticEventAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StatisticEventAdapter.MyViewHolder holder, final int position) {
        final Statistic stat = statList.get(position);
        holder.title.setText(stat.getDescription());
        holder.fedstat.setText(stat.getStat());
        final String Ev=stat.getDescription().replace(" (Student Count)","");
    }

    @Override
    public int getItemCount() {
        return statList.size();
    }






}