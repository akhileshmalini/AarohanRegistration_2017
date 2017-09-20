package com.amrita.aarohanregistration.Statistics_Aarohan.FeedbackStats;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.Statistics_Aarohan.Statistic;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class StatisticFeedbackAdapter extends RecyclerView.Adapter<StatisticFeedbackAdapter.MyViewHolder> {

    private List<Statistic> statList;
    Context mContext;
    Context ActivityContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public  BarChart chart;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.evName);
             chart = (BarChart) view.findViewById(R.id.chart);
        }
    }


    public StatisticFeedbackAdapter(List<Statistic> statList, Context mContext,Context ActivityContext) {
        this.statList = statList;

        this.mContext=mContext;


this.ActivityContext=ActivityContext;


    }

    @Override
    public StatisticFeedbackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_stat_listitem, parent, false);

        return new StatisticFeedbackAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StatisticFeedbackAdapter.MyViewHolder holder, final int position) {
        final Statistic stat = statList.get(position);
        List<String> items = Arrays.asList(stat.getStat().split("\\s*,\\s*"));
        int a5,a4,a3,a2,a1;
        a5=Integer.parseInt(items.get(0));
        a4=Integer.parseInt(items.get(1));
        a3=Integer.parseInt(items.get(2));
        a2=Integer.parseInt(items.get(3));
        a1=Integer.parseInt(items.get(4));


        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(5, a5));
        entries.add(new BarEntry(4, a4));
        entries.add(new BarEntry(3, a3));
        entries.add(new BarEntry(2, a2));
        entries.add(new BarEntry(1, a1));
        final int tot, avg;
        tot= a5 + a4 + a3 + a2 + a1;

        if(tot!=0) {
            avg = ((5 * a5) + (4 * a4) + (3 * a3) + (2 * a2) + (1 * a1)) / tot;
        }else {
            avg=0;
        }



        BarDataSet set = new BarDataSet(entries, "Stars");
        holder.title.setText(stat.getDescription());

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        holder.chart.setData(data);
        holder.chart.setFitBars(true); // make the x-axis fit exactly all bars
        holder.chart.invalidate(); // refresh



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final Dialog rankDialog = new Dialog(ActivityContext, R.style.MyAlertDialogStyle);
                rankDialog.setContentView(R.layout.rankview_dialog);
                rankDialog.setCancelable(true);
                final RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                ratingBar.setRating(avg);

                TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                TextView sub = (TextView) rankDialog.findViewById(R.id.textView37);
                ratingBar.setEnabled(false);
                text.setText(stat.getDescription());

                if(avg==0){
                    sub.setText("No Ratings Available ");
                }
                    else{
                        sub.setText("Average Rating of " + avg + " with " + tot + " responses ");
                    }
                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int rating= (int) ratingBar.getRating();


                        rankDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rankDialog.show();
                Window window = rankDialog.getWindow();
                window.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);


                return true;
            }
        });





      
        

    }

    @Override
    public int getItemCount() {
        return statList.size();
    }






}