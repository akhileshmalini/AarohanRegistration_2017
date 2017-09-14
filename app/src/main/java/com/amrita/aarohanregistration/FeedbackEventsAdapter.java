package com.amrita.aarohanregistration;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class FeedbackEventsAdapter extends RecyclerView.Adapter<FeedbackEventsAdapter.MyViewHolder> {

    private List<FeedBackEvent> eventsList;
    Context mContext;
    DatabaseReference ref;
    Context activityContext;
    String arhnID;
    FirebaseDatabase database;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView fedstat;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.evName);
            fedstat = (ImageView) view.findViewById(R.id.fedStat);

        }
    }


    public FeedbackEventsAdapter(List<FeedBackEvent> eventsList, Context mContext,Context activityContext, DatabaseReference ref, String arhnID ,FirebaseDatabase database) {
        this.eventsList = eventsList;
        this.mContext=mContext;
        this.ref=ref;
        this.activityContext=activityContext;
        this.arhnID=arhnID;
        this.database=database;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final FeedBackEvent event = eventsList.get(position);
        holder.title.setText(event.getEvName());
        final float num=event.getRating();

        if(num==0.0){

            Glide.with(mContext).load(R.drawable.nonots)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(holder.fedstat);

        }else{
            Glide.with(mContext).load(R.drawable.yesokay)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(holder.fedstat);
        }





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final Dialog rankDialog = new Dialog(activityContext, R.style.MyAlertDialogStyle);
                rankDialog.setContentView(R.layout.rank_dialog);
                rankDialog.setCancelable(true);
                final RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                ratingBar.setRating(num);

                TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                text.setText(event.getEvName());

                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int rating= (int) ratingBar.getRating();

                        DatabaseReference eventStdRef = database.getReference("Events").child(event.getEvName()).child("Students").child(arhnID);
                        eventStdRef.setValue(ratingBar.getRating());
                        ref.child(event.getEvName()).setValue(ratingBar.getRating());
                        eventsList.get(position).setRating(ratingBar.getRating());
                        notifyItemChanged(position);
                        rankDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rankDialog.show();



            }
        });

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }






}