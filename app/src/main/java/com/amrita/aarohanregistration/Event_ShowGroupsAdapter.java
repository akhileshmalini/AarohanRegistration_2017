package com.amrita.aarohanregistration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class Event_ShowGroupsAdapter extends RecyclerView.Adapter<Event_ShowGroupsAdapter.MyViewHolder> {

    private List<Event_Student_Group_Count> grpsList;
    Context mContext;
    String Eventname;
    FirebaseDatabase database;
    int count,a;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.evName);
            imageView= (ImageView) view.findViewById(R.id.fedStat);

        }
    }


    public Event_ShowGroupsAdapter(List<Event_Student_Group_Count> grpsList, Context mContext, String Eventname, FirebaseDatabase database,int count) {
        this.grpsList = grpsList;
        this.mContext = mContext;
        this.Eventname=Eventname;
        this.database=database;
        this.count=count;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Event_Student_Group_Count group = grpsList.get(position);
        holder.title.setText(group.getStdName());
        int a= group.getCount();
        Toast.makeText(mContext,""+a,Toast.LENGTH_SHORT).show();


        if(a<count){

            Glide.with(mContext).load(R.drawable.nonotsa)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);

        }else{
            Glide.with(mContext).load(R.drawable.yesokaya)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(mContext,Event_GroupDashboard.class);
                intent.putExtra("groupName",group.getStdName());
                intent.putExtra("EventName",Eventname);
                mContext.startActivity(intent);

            }
        });







    }

    @Override
    public int getItemCount() {
        return grpsList.size();
    }






}