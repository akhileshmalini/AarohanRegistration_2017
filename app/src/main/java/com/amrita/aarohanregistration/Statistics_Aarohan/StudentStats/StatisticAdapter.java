package com.amrita.aarohanregistration.Statistics_Aarohan.StudentStats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.Statistics_Aarohan.Statistic;

import java.util.List;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class StatisticAdapter  extends RecyclerView.Adapter<StatisticAdapter.MyViewHolder> {

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


    public StatisticAdapter(List<Statistic> statList, Context mContext) {
        this.statList = statList;
        this.mContext=mContext;
       
    }

    @Override
    public StatisticAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_listitem, parent, false);

        return new StatisticAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StatisticAdapter.MyViewHolder holder, final int position) {
        final Statistic stat = statList.get(position);
        holder.title.setText(stat.getDescription());
        holder.fedstat.setText(stat.getStat());

      
        

    }

    @Override
    public int getItemCount() {
        return statList.size();
    }






}