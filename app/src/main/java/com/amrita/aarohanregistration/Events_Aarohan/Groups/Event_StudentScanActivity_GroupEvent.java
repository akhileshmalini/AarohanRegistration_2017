package com.amrita.aarohanregistration.Events_Aarohan.Groups;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.ScanActivity;
import com.google.android.gms.vision.barcode.Barcode;



/*
* This Activity Allows the User to Choose between Scanning a student QRCode or Manually Entering a user's ID.
*
* */

public class Event_StudentScanActivity_GroupEvent extends AppCompatActivity {

    Button btn_scanQR, btn_manualEntry, btn_stdList;
    EditText edit_manualCode;
    TextView result, txt_Title;

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    String EventName, cate, groupName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentscan);

        //Get Intent Extras
        EventName = getIntent().getExtras().getString("EventName");
        groupName = getIntent().getExtras().getString("groupName");

        //View Bindings
        btn_scanQR = (Button) findViewById(R.id.scanbtn);
        btn_manualEntry = (Button) findViewById(R.id.button4);
        btn_stdList = (Button) findViewById(R.id.button8);
        txt_Title = (TextView) findViewById(R.id.textTitle);
        edit_manualCode = (EditText) findViewById(R.id.editArhnID);
        //Unused
        result = (TextView) findViewById(R.id.result);


        //Set Page Title
        txt_Title.setText(EventName);

        //Test to see if Camera Permissions exist
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }


        //Button When QR Code needs to be Scanned
        btn_scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Event_StudentScanActivity_GroupEvent.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //Button When Manual Entry is Done
        btn_manualEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Test to see if Input String matches Format using regEx
                String entry = edit_manualCode.getText().toString();
                if (entry.matches("^ARHN\\d{4}")) {
                    //Entry Matches therefore Intent to Student Profile for Group Event
                    /*
                    * Required Extras
                    * EventName
                    * GroupName
                    * ID(Manual Entry Text)
                    * */
                    Intent intent = new Intent(Event_StudentScanActivity_GroupEvent.this, Event_StudentProfile_GroupEvent.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    intent.putExtra("EventName", EventName);
                    intent.putExtra("groupName", groupName);
                    intent.putExtra("ArhnId", entry);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid ID | Does not Match Pattern", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Button to View List of Students already Registered
        btn_stdList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    /*
                    * Required Extras
                    * EventName
                    * */
                Intent intent = new Intent(Event_StudentScanActivity_GroupEvent.this, Event_StudentList_GroupEvent.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("Event", EventName);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                //Barcode Returns a Value Sucessfully
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                            /*
                                * Required Extras
                                * EventName
                                * GroupName
                                * ID(QR Code Result Text)
                             * */
                        Intent intent = new Intent(Event_StudentScanActivity_GroupEvent.this, Event_StudentProfile_GroupEvent.class);
                        intent.putExtra("EventName", EventName);
                        intent.putExtra("groupName", groupName);
                        intent.putExtra("ArhnId", barcode.displayValue);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}