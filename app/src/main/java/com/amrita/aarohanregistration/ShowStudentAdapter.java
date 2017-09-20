package com.amrita.aarohanregistration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amrita.aarohanregistration.Feedback_Aarohan.FeedBackEvent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class ShowStudentAdapter extends RecyclerView.Adapter<ShowStudentAdapter.MyViewHolder> {

    private List<FeedBackEvent> studentList;
    Context mContext;
    DatabaseReference ref;
    Context activityContext;
    FirebaseDatabase database;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.evName);

        }
    }


    public ShowStudentAdapter(List<FeedBackEvent> studentList, Context mContext) {
        this.studentList = studentList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showstudent_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final FeedBackEvent event = studentList.get(position);
        holder.title.setText(event.getEvName());
        

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }






}