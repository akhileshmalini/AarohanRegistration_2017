package com.amrita.aarohanregistration.Events_Aarohan.Induvidual;

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
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.Events_Aarohan.Event_Status;
import com.amrita.aarohanregistration.Events_Aarohan.Groups.Event_Dashboard_GroupEvent;
import com.amrita.aarohanregistration.Events_Aarohan.Groups.Event_ShowStudentScanActivity_Group;
import com.amrita.aarohanregistration.Events_Aarohan.Groups.Event_Winnner_GroupEvent;
import com.amrita.aarohanregistration.R;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Akhilesh on 9/11/2017.
 */

public class Event_Dashboard_Induvidual extends AppCompatActivity {

    String EventName,cate,grpCount,groupno,groupName;
    Button btn_addStd,btn_viewGrps,btn_studList,btn_scanView,btn_evWinners,btn_setStatus;
    FirebaseDatabase database;
    TextView txt_evName,txt_Category;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_dashboard_inuvidual);
        database= FirebaseDatabase.getInstance();
        
        //Get intent Extras
        EventName =getIntent().getExtras().getString("EventName");
        cate=getIntent().getExtras().getString("Category");


        //View Bindings
        txt_evName= (TextView) findViewById(R.id.dashTitle);
        txt_Category= (TextView) findViewById(R.id.dashCat);
        btn_addStd= (Button) findViewById(R.id.button9);
        btn_studList= (Button) findViewById(R.id.button11);
        btn_scanView= (Button) findViewById(R.id.button13);
        btn_setStatus= (Button) findViewById(R.id.button17);
        btn_evWinners= (Button) findViewById(R.id.button12);


        //Set Event Title and Category
        txt_evName.setText(EventName);
        txt_Category.setText(cate+" Category");



        //Button To Add Student to Event
        btn_addStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * Intent Extras rquired
                * EventName
                * */
                Intent intent =new Intent(getApplicationContext(),Event_StudentScanActivity_Induvidual.class);
                intent.putExtra("EventName",EventName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent);
            }
        });



        //Button to Display List of Student
        btn_studList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * Intent Extras rquired
                * EventName
                * */
                Intent intent =new Intent(Event_Dashboard_Induvidual.this,Event_StudentList_Induvidual.class);
                intent.putExtra("Event",EventName);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent);

            }
        });

        //Button to Scan and View a Student
        btn_scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*
                * Intent Extras rquired
                * EventName
                * */
                Intent intent=new Intent(Event_Dashboard_Induvidual.this,Event_ShowStudentScanActivity_Group.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });

        //Button to Set Status of Event
        btn_setStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*
                * Intent Extras rquired
                * EventName
                * */
                Intent intent =new Intent(Event_Dashboard_Induvidual.this,Event_Status.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("EventName",EventName);
                startActivity(intent);
            }
        });


        //Button to set Winners of Event
        btn_evWinners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * Intent Extras rquired
                * EventName
                * */

                final Dialog passDialog = new Dialog(Event_Dashboard_Induvidual.this, R.style.MyAlertDialogStyle);
                passDialog.setContentView(R.layout.dialog_password);
                passDialog.setCancelable(true);
                final EditText editText = (EditText) passDialog.findViewById(R.id.editText);
                TextView text = (TextView) passDialog.findViewById(R.id.rank_dialog_text1);
                text.setText("Enter Passkey To Access Winner");
                Button updateButton = (Button) passDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setText("Enter");
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = editText.getText().toString();
                        if (password.equals("winner")) {

                            Intent intent = new Intent(Event_Dashboard_Induvidual.this, Event_Winnner_GroupEvent.class);
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






}
