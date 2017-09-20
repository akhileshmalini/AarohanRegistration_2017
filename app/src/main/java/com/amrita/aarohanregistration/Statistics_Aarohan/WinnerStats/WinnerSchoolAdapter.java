package com.amrita.aarohanregistration.Statistics_Aarohan.WinnerStats;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amrita.aarohanregistration.R;

import java.util.List;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class WinnerSchoolAdapter extends RecyclerView.Adapter<WinnerSchoolAdapter.MyViewHolder> {

    private List<Winner_School> statList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,fedstat;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.evName);
            fedstat = (TextView) view.findViewById(R.id.evStat);

        }
    }


    public WinnerSchoolAdapter(List<Winner_School> statList, Context mContext) {
        this.statList = statList;
        this.mContext=mContext;

    }

    @Override
    public WinnerSchoolAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_listitem, parent, false);

        return new WinnerSchoolAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WinnerSchoolAdapter.MyViewHolder holder, final int position) {
        final Winner_School stat = statList.get(position);
        holder.title.setText(stat.getSchoolName());
        holder.fedstat.setText(""+stat.getPoints()+" Points");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,Winner_EventsList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("School",stat.getSchoolName());
                mContext.startActivity(intent);
            }
        });
      
        

    }

    @Override
    public int getItemCount() {
        return statList.size();
    }






}