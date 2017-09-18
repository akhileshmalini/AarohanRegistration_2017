package com.amrita.aarohanregistration.Events_Aarohan.Induvidual;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amrita.aarohanregistration.Events_Aarohan.Event_Student_Group;
import com.amrita.aarohanregistration.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class Event_ShowStudentAdapter_Induvidual extends RecyclerView.Adapter<Event_ShowStudentAdapter_Induvidual.MyViewHolder> {

    private List<Event_Student_Group> studList;
    Context mContext;
    DatabaseReference ref;
    Context activityContext;
    FirebaseDatabase database;
    String EventName;
    Context ActivityContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.evName);
        }
    }


    public Event_ShowStudentAdapter_Induvidual(List<Event_Student_Group> studList, Context mContext, String EventName,Context ActivityContext) {
        this.studList = studList;
        this.mContext = mContext;
        this.EventName=EventName;
        this.ActivityContext=ActivityContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showstudent_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Event_Student_Group event = studList.get(position);
        holder.title.setText(event.getStdName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext,Event_ShowStudent_Induviudal.class);
                intent.putExtra("ArhnId",event.getStdName());
                intent.putExtra("EventName",EventName);

                mContext.startActivity(intent);
                ((Activity)ActivityContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studList.size();
    }






}