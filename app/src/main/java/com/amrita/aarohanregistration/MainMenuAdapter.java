package com.amrita.aarohanregistration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Akhilesh on 9/8/2017.
 */

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MyViewHolder> {

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


    public MainMenuAdapter(List<MainMenuItems> menuList, Context mContext) {
        this.menuList = menuList;
        this.mContext=mContext;
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
        holder.imgs.setImageResource(event.getImg());





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            if(position==0){
                Intent intent =new Intent( mContext,Event_JuniorSeniorSelect.class);
                mContext.startActivity(intent);



            }else if (position==1){
                Intent intent =new Intent( mContext,FeedbackScanActivity.class);
                mContext.startActivity(intent);

            }else if (position==2){

                Intent intent =new Intent( mContext,Stats_Home.class);
                mContext.startActivity(intent);
            }else if (position==3){

                Intent intent =new Intent( mContext,RandomStudentScanActivity.class);
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