package com.amrita.aarohanregistration.Statistics_Aarohan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amrita.aarohanregistration.MainMenuItems;
import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.Statistics_Aarohan.EventStats.Statistics_Event;
import com.amrita.aarohanregistration.Statistics_Aarohan.EventStats.Statistics_EventStatus;
import com.amrita.aarohanregistration.Statistics_Aarohan.FeedbackStats.Statistics_Feedback;
import com.amrita.aarohanregistration.Statistics_Aarohan.SchoolStats.Statistics_School;
import com.amrita.aarohanregistration.Statistics_Aarohan.StudentStats.Statistics_Students;
import com.amrita.aarohanregistration.Statistics_Aarohan.WinnerStats.Statistics_WinnerSchools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class Statistics_MenuAdapter extends RecyclerView.Adapter<Statistics_MenuAdapter.MyViewHolder> {

    private List<MainMenuItems> menuList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imgs;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.eventName);
            imgs = (ImageView) view.findViewById(R.id.imageView2);

        }
    }


    public Statistics_MenuAdapter(List<MainMenuItems> menuList, Context mContext) {
        this.menuList = menuList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_menu_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final MainMenuItems event = menuList.get(position);
        holder.title.setText(event.getTitle());
        Glide.with(mContext).load(event.getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.imgs);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (position == 0) {

                    Intent intent = new Intent(mContext, Statistics_Students.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    mContext.startActivity(intent);

                } else if (position == 1) {
                    Intent intent = new Intent(mContext, Statistics_Event.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    mContext.startActivity(intent);

                } else if (position == 2) {

                    Intent intent = new Intent(mContext, Statistics_School.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    mContext.startActivity(intent);

                } else if (position == 3) {

                    Intent intent = new Intent(mContext, Statistics_Feedback.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    mContext.startActivity(intent);

                } else if (position == 4) {

                    Intent intent = new Intent(mContext, Statistics_WinnerSchools.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    mContext.startActivity(intent);

                } else if (position == 5) {

                    Intent intent = new Intent(mContext, Statistics_EventStatus.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    mContext.startActivity(intent);

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


}