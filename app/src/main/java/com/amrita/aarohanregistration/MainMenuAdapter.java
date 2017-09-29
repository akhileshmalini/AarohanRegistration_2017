package com.amrita.aarohanregistration;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.Events_Aarohan.Event_JuniorSeniorSelect;
import com.amrita.aarohanregistration.Events_Aarohan.Groups.Event_GroupDashboard;
import com.amrita.aarohanregistration.Events_Aarohan.Groups.Event_ShowStudent_GroupEvent;
import com.amrita.aarohanregistration.Feedback_Aarohan.FeedbackScanActivity;
import com.amrita.aarohanregistration.Statistics_Aarohan.Statistics_Home;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MyViewHolder> {

    private List<MainMenuItems> menuList;
    Context mContext,activityContext;
    String passEvents,passStats;
 

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imgs;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.eventName);
            imgs = (ImageView) view.findViewById(R.id.imageView2);
            }
    }


    public MainMenuAdapter(List<MainMenuItems> menuList, Context mContext, Context activityContext) {
        this.menuList = menuList;
        this.mContext=mContext;
        this.activityContext=activityContext;


        //Paswords
        passEvents="events";
        passStats="stats";

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


            if(position==0){

//Initialize Dialog and Request Passkey
                final Dialog passDialog = new Dialog(activityContext, R.style.MyAlertDialogStyle);
                passDialog.setContentView(R.layout.dialog_password);
                passDialog.setCancelable(true);
                final EditText editText = (EditText) passDialog.findViewById(R.id.editText);
                TextView text = (TextView) passDialog.findViewById(R.id.rank_dialog_text1);
                text.setText("Enter Passkey To Access Events");
                Button updateButton = (Button) passDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setText("Enter");
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = editText.getText().toString();

                        if (password.equals(passEvents)) {


                            Intent intent =new Intent( mContext,Event_JuniorSeniorSelect.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        } else {
                            //Invalid Passkey
                            Toast.makeText(mContext, "Invalid Passkey", Toast.LENGTH_SHORT).show();
                        }
                        passDialog.dismiss();
                    }
                });
                passDialog.show();
                Window window = passDialog.getWindow();
                window.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            }else if (position==1){
                Intent intent =new Intent( mContext,FeedbackScanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                mContext.startActivity(intent);

            }else if (position==2){

                final Dialog passDialog = new Dialog(activityContext, R.style.MyAlertDialogStyle);
                passDialog.setContentView(R.layout.dialog_password);
                passDialog.setCancelable(true);
                final EditText editText = (EditText) passDialog.findViewById(R.id.editText);
                TextView text = (TextView) passDialog.findViewById(R.id.rank_dialog_text1);
                text.setText("Enter Passkey To Access Statistics");
                Button updateButton = (Button) passDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setText("Enter");
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = editText.getText().toString();
                        if (password.equals(passStats)) {
                            Intent intent =new Intent( mContext,Statistics_Home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        } else {
                            //Invalid Passkey
                            Toast.makeText(mContext, "Invalid Passkey", Toast.LENGTH_SHORT).show();
                        }
                        passDialog.dismiss();
                    }
                });
                passDialog.show();
                Window window = passDialog.getWindow();
                window.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);




            }else if (position==3){

                Intent intent =new Intent( mContext,RandomStudentScanActivity.class);
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