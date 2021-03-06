package com.amrita.aarohanregistration.Events_Aarohan.Induvidual;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.Events_Aarohan.Event_Winner_Model;
import com.amrita.aarohanregistration.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class Event_ShowWinnerInduvidualsAdapter extends RecyclerView.Adapter<Event_ShowWinnerInduvidualsAdapter.MyViewHolder> {

    private List<Event_Winner_Model> grpsList;
    Context mContext;
    String Eventname;
    FirebaseDatabase database;
    Context activityContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView place, arhnid;

        public MyViewHolder(View view) {
            super(view);
            place = (TextView) view.findViewById(R.id.evName);
            arhnid = (TextView) view.findViewById(R.id.evStat);
        }
    }


    public Event_ShowWinnerInduvidualsAdapter(List<Event_Winner_Model> grpsList, Context mContext, String Eventname, FirebaseDatabase database, Context context) {
        this.grpsList = grpsList;
        this.mContext = mContext;
        this.Eventname = Eventname;
        this.database = database;
        this.activityContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_winner_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Event_Winner_Model group = grpsList.get(position);
        holder.place.setText(group.getPlace());
        holder.arhnid.setText(group.getGrp());

        //Upon Click Redirect to Winner Details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Event_ShowStudent_Induviudal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("EventName", Eventname);
                intent.putExtra("ArhnId", group.getGrp());
                mContext.startActivity(intent);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(activityContext).create();
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Are you sure you want to remove " + group.getGrp() + " from " + group.getPlace() + "?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final DatabaseReference WinnerRef = database.getReference("Events").child(Eventname).child("Winners").child(group.getPlace());
                                WinnerRef.removeValue();
                                DatabaseReference StdRef = database.getReference("Winners").child(group.getSchool()).child(Eventname).child(group.getPlace());
                                StdRef.removeValue();
                                grpsList.remove(position);
                                Toast.makeText(mContext, "Removed Student", Toast.LENGTH_SHORT).show();
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return grpsList.size();
    }


}