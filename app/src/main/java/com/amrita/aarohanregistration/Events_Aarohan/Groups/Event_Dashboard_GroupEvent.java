package com.amrita.aarohanregistration.Events_Aarohan.Groups;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.Events_Aarohan.Event_Status;
import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.Statistics_Aarohan.Statistics_Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akhilesh on 9/11/2017.
 */

/**
 * This is the Dashboard for all group Events
 * It Consists of the Following Menus
     * Create New Group
     * View Groups
     * View Student List of Event
     * Scan to View Student
     * Set Event Status
     * Winner List
 * It Also Shows the Event name, category to which the event belongs to as well as how many people are allowed to enter per group.
 */


public class Event_Dashboard_GroupEvent extends AppCompatActivity {


    //Global Declarations
    String EventName, Category, grpCount, groupNo, groupName;
    Button btn_newGrp, btn_viewGrps, btn_viewStdnts, btn_scanStd, btn_winnersList, btn_evStatus;
    TextView txt_evName, txt_evCategory, txt_evGroupLimit;
    ProgressBar progressBar;
    ScrollView scrollView;
    FirebaseDatabase database;
    DatabaseReference EventsRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_dashboard);
        database = FirebaseDatabase.getInstance();
        //Intent Data Receive
        EventName = getIntent().getExtras().getString("EventName");
        Category = getIntent().getExtras().getString("Category");


        //View Bindings
        txt_evName = (TextView) findViewById(R.id.dashTitle);
        txt_evCategory = (TextView) findViewById(R.id.dashCat);
        txt_evGroupLimit = (TextView) findViewById(R.id.dashGrp);

        btn_newGrp = (Button) findViewById(R.id.button9);
        btn_viewGrps = (Button) findViewById(R.id.button10);
        btn_viewStdnts = (Button) findViewById(R.id.button11);
        btn_scanStd = (Button) findViewById(R.id.button13);
        btn_winnersList = (Button) findViewById(R.id.button12);
        btn_evStatus = (Button) findViewById(R.id.button16);

        scrollView= (ScrollView) findViewById(R.id.scrollView);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);


        hideContent();


        txt_evName.setText(EventName);
        txt_evCategory.setText(Category + " Category");


        //Was Here
         EventsRef = database.getReference("Events").child(EventName);

      /*This Firebase call is to Determine the no. of people allowed per group
        and also the value of the new group that needs to be created. */

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //This Gets the Groupno  of the last Group in the list.
                String frp="";
                for(DataSnapshot data : dataSnapshot.child("Groups").getChildren()){
                    frp=data.getKey();
                }
                //When it comes out of the loop it will have retrieved the last existing group number.
                frp = frp.replaceAll("\\D+","");
                if (frp.equals("")){
                    frp="0";
                }
                int valFrp=Integer.parseInt(frp)+1;

                //No. of people Allowed per group
                grpCount = dataSnapshot.child("grpCount").getValue().toString();

                //CurrentGroup number + 1
                groupNo = ""+valFrp;
                //New Group Name (Used When new group is created)
                groupName = "GRP" + groupNo;
                //Set GroupLimit
                txt_evGroupLimit.setText("Students/Group : " + grpCount);
                showContent();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do Nothing
            }
        };
        EventsRef.addValueEventListener(postListener);




        //Button to Create New Group
        btn_newGrp.setText("Create New Group");
        btn_newGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Required Extras for Group Dasboard
                        *NewGroupName
                        * EventName
                        * Number of People Allowed per Group
                 */

                Intent intent = new Intent(getApplicationContext(), Event_GroupDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("groupName",groupName);
                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });

        //Button to View Existing Groups
        btn_viewGrps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    /*Required Extras for Group Dasboard
                        *No. of People Allowed per Group
                        *EventName
                    */
                Intent intent = new Intent(getApplicationContext(), Event_ViewGroups.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("Event", EventName);
                intent.putExtra("GroupSize", "" + grpCount);
                startActivity(intent);
            }
        });


        //Button to View List of students in Attendance for the Event
        btn_viewStdnts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*Required Extras for Group Dasboard
                        *EventName
                    */
                Intent intent = new Intent(Event_Dashboard_GroupEvent.this, Event_StudentList_GroupEvent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("Event", EventName);
                startActivity(intent);

            }
        });


        //Button to set the current Status of the Event ie. If event has started, winners announced? etc.
        btn_evStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Required Extras for Group Dasboard
                        *EventName
                    */
                Intent intent = new Intent(Event_Dashboard_GroupEvent.this, Event_Status.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("EventName", EventName);
                startActivity(intent);
            }
        });

        //Button to set the current Status of the Event ie. If event has started, winners announced? etc.
        btn_scanStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    /*Required Extras for Group Dasboard
                        *EventName
                    */
                Intent intent = new Intent(Event_Dashboard_GroupEvent.this, Event_ShowStudentScanActivity_Group.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("EventName", EventName);
                startActivity(intent);
            }
        });

        //Button to set the Winners of a particular Event
        btn_winnersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*Required Extras for Group Dasboard
                        *EventName
                    */


                final Dialog passDialog = new Dialog(Event_Dashboard_GroupEvent.this, R.style.MyAlertDialogStyle);
                passDialog.setContentView(R.layout.dialog_password);
                passDialog.setCancelable(true);
                final EditText editText = (EditText) passDialog.findViewById(R.id.editText);
                TextView text = (TextView) passDialog.findViewById(R.id.rank_dialog_text1);
                text.setText("Enter Passkey To Access Winners");
                Button updateButton = (Button) passDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setText("Enter");
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = editText.getText().toString();
                        if (password.equals("winner")) {

                            Intent intent = new Intent(Event_Dashboard_GroupEvent.this, Event_Winnner_GroupEvent.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("EventName", EventName);
                            startActivity(intent);
                        } else {
                            //Invalid Passkey
                            Toast.makeText(getApplicationContext(), "Invalid Passkey", Toast.LENGTH_SHORT).show();
                        }
                        passDialog.dismiss();
                    }
                });
                passDialog.show();
                Window window = passDialog.getWindow();
                window.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);






            }
        });

    }


    //Function to make all View Elements Visible
    void showContent(){
        txt_evName.setVisibility(View.VISIBLE);
        txt_evCategory.setVisibility(View.VISIBLE);
        txt_evGroupLimit.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }

    //Function to make all View Elements Invisible
    void hideContent(){
        txt_evName.setVisibility(View.INVISIBLE);
        txt_evCategory.setVisibility(View.INVISIBLE);
        txt_evGroupLimit.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }



}
