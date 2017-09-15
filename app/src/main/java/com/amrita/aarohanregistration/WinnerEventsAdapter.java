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

public class WinnerEventsAdapter extends RecyclerView.Adapter<WinnerEventsAdapter.MyViewHolder> {

    private List<Winner_Events> statList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,fedstat;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.evName);
            fedstat = (TextView) view.findViewById(R.id.evStat);

        }
    }


    public WinnerEventsAdapter(List<Winner_Events> statList, Context mContext) {
        this.statList = statList;
        this.mContext=mContext;

    }

    @Override
    public WinnerEventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_listitem, parent, false);

        return new WinnerEventsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WinnerEventsAdapter.MyViewHolder holder, final int position) {
        final Winner_Events stat = statList.get(position);
        holder.title.setText(stat.getEventName());
        holder.fedstat.setText(stat.getPlace());

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(mContext,Winner_GroupList.class);
              intent.putExtra("EventName",stat.getEventName());
              intent.putExtra("Groups",stat.getGrpOrid());
              intent.putExtra("Place",stat.getPlace());
              mContext.startActivity(intent);

          }
      });
        

    }

    @Override
    public int getItemCount() {
        return statList.size();
    }






}