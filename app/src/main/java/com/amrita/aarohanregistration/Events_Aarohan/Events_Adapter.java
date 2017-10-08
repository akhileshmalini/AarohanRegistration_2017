package com.amrita.aarohanregistration.Events_Aarohan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.Events_Aarohan.Groups.Event_Dashboard_GroupEvent;
import com.amrita.aarohanregistration.Events_Aarohan.Induvidual.Event_Dashboard_Induvidual;
import com.amrita.aarohanregistration.R;

import java.util.List;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class Events_Adapter extends RecyclerView.Adapter<Events_Adapter.MyViewHolder> {

    private List<Events> eventsList;
    Context mContext;
    Context activityContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, category;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView);
            category = (TextView) view.findViewById(R.id.textView2);

        }
    }


    public Events_Adapter(List<Events> eventsList, Context mContext, Context activityContext) {
        this.eventsList = eventsList;
        this.mContext = mContext;
        this.activityContext = activityContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Events event = eventsList.get(position);
        holder.title.setText(event.getEventName());
        holder.category.setText(event.getCategory());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog passDialog = new Dialog(activityContext, R.style.MyAlertDialogStyle);
                passDialog.setContentView(R.layout.dialog_password);
                passDialog.setCancelable(true);
                final EditText editText = (EditText) passDialog.findViewById(R.id.editText);
                TextView text = (TextView) passDialog.findViewById(R.id.rank_dialog_text1);
                text.setText(event.getEventName());
                final String pwd = event.getEventName().toLowerCase();
                Button updateButton = (Button) passDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String password = editText.getText().toString().toLowerCase();
                        if (password.equals(pwd)) {
                            if (event.getGrpCount() == 1) {
                                Intent intent = new Intent(mContext, Event_Dashboard_Induvidual.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("EventName", event.getEventName());
                                intent.putExtra("Category", event.getCategory());
                                mContext.startActivity(intent);
                            } else {
                                Intent intent = new Intent(mContext, Event_Dashboard_GroupEvent.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("EventName", event.getEventName());
                                intent.putExtra("Category", event.getCategory());
                                mContext.startActivity(intent);
                            }
                        } else {
                            Toast.makeText(mContext, "Invalid Passkey", Toast.LENGTH_SHORT).show();
                        }

                        passDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                passDialog.show();
                Window window = passDialog.getWindow();
                window.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);

//                if (event.getGrpCount() == 1) {
//                    Intent intent = new Intent(mContext, Event_Dashboard_Induvidual.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("EventName", event.getEventName());
//                    intent.putExtra("Category", event.getCategory());
//                    mContext.startActivity(intent);
//                } else {
//                    Intent intent = new Intent(mContext, Event_Dashboard_GroupEvent.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("EventName", event.getEventName());
//                    intent.putExtra("Category", event.getCategory());
//                    mContext.startActivity(intent);
//                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }


}