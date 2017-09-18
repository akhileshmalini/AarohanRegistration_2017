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
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.Faculty;
import com.amrita.aarohanregistration.FacultyProfile;
import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Event_ShowStudent_GroupEvent extends AppCompatActivity {
    TextView txt_arhnID, txt_stdName, txt_stdSchool, txt_stdGender, txt_stdGroup, txt_stdCategory, a, b, c, d, e, f;
    Button btn_removeStd, btn_showGrp, btn_getFacDetails;
    ProgressBar progressBar;
    String arhnID, EventName, sch = "Apple", GroupsName;

    DatabaseReference fstudentsRef, studentsRef;
    FirebaseDatabase database;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_studentprofile);
        database = FirebaseDatabase.getInstance();

        //Get Intent Extras
        arhnID = getIntent().getExtras().getString("ArhnId");
        EventName = getIntent().getExtras().getString("EventName");


        //View Bindings
        txt_arhnID = (TextView) findViewById(R.id.textView27);
        txt_stdName = (TextView) findViewById(R.id.textView22);
        txt_stdSchool = (TextView) findViewById(R.id.textView31);
        txt_stdGender = (TextView) findViewById(R.id.textView35);
        txt_stdCategory = (TextView) findViewById(R.id.textView33);
        txt_stdGroup = (TextView) findViewById(R.id.textView29);
        btn_showGrp = (Button) findViewById(R.id.button14);
        btn_getFacDetails = (Button) findViewById(R.id.button5);
        btn_removeStd = (Button) findViewById(R.id.btnRemove);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        //Unused
        a = (TextView) findViewById(R.id.textView21);
        b = (TextView) findViewById(R.id.textView26);
        c = (TextView) findViewById(R.id.textView28);
        d = (TextView) findViewById(R.id.textView30);
        e = (TextView) findViewById(R.id.textView32);
        f = (TextView) findViewById(R.id.textView34);


        hideContent();
        studentIsValid();

        //Button To Fetch Faculty Details
        btn_getFacDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sch.equals("Apple")) {
                    //Faculty Details Not Available
                    Toast.makeText(getApplicationContext(), "No Faculty Information Available", Toast.LENGTH_LONG).show();
                } else {
                    //Fetch Faculty Details from Available School Details
                    fstudentsRef = database.getReference("Schools").child(sch).child("faculty");
                    fstudentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Faculty f = dataSnapshot.getValue(Faculty.class);
                            //Get Faculty Details from School and Pass as Indent Data to FacultyProfile Class
                            String Email = f.getEmail();
                            String Phone = "" + f.getPhoneno();
                            String Name = f.getName();

                            /*
                            * Extars Required
                            * Name
                            * Email
                            * Phone Number
                            * */
                            Intent intent = new Intent(Event_ShowStudent_GroupEvent.this, FacultyProfile.class);
                            intent.putExtra("Name", Name);
                            intent.putExtra("Phone", Phone);
                            intent.putExtra("Email", Email);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


        //Button When Student is Removed
        btn_removeStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize Dialog and Request Passkey
                final Dialog passDialog = new Dialog(Event_ShowStudent_GroupEvent.this, R.style.MyAlertDialogStyle);
                passDialog.setContentView(R.layout.dialog_password);
                passDialog.setCancelable(true);
                final EditText editText = (EditText) passDialog.findViewById(R.id.editText);
                TextView text = (TextView) passDialog.findViewById(R.id.rank_dialog_text1);
                text.setText("Enter Passkey For Removal");
                final String pwd = "amma";
                Button updateButton = (Button) passDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = editText.getText().toString();
                        if (password.equals(pwd)) {

                            //Passkey is Valid Perform Remove Operation
                            DatabaseReference eventStdRef = database.getReference("Events").child(EventName).child("Students").child(arhnID);
                            eventStdRef.removeValue();
                            studentsRef.child("EventsAttended").child(EventName).removeValue();
                            DatabaseReference grpRef = database.getReference("Events").child(EventName).child("Groups").child(GroupsName).child(arhnID);
                            grpRef.removeValue();
                            Toast.makeText(getApplicationContext(), "Student Successfully Removed", Toast.LENGTH_SHORT).show();

                            /*
                            * Redirect to Group Daskboard
                            * Extras Required
                            * EventName
                            * GroupName
                            * */
                            Intent intent = new Intent(Event_ShowStudent_GroupEvent.this, Event_GroupDashboard.class);
                            intent.putExtra("EventName", EventName);
                            intent.putExtra("groupName", GroupsName);
                            startActivity(intent);
                            finish();

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

        //Button for When User Wishes to see the Group to which the student Belongs to
        btn_showGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*
                            * Redirect to Group Daskboard
                            * Extras Required
                            * EventName
                            * GroupName
                            * */
                Intent intent = new Intent(getApplicationContext(), Event_GroupDashboard.class);
                intent.putExtra("groupName", GroupsName);
                intent.putExtra("EventName", EventName);
                startActivity(intent);
                finish();

            }
        });


    }

    //Show All Content that may have been Hidden
    void showContent() {
        txt_arhnID.setVisibility(View.VISIBLE);
        txt_stdName.setVisibility(View.VISIBLE);
        txt_stdSchool.setVisibility(View.VISIBLE);
        txt_stdGender.setVisibility(View.VISIBLE);
        txt_stdCategory.setVisibility(View.VISIBLE);
        txt_stdGroup.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);
        btn_getFacDetails.setVisibility(View.VISIBLE);
        btn_removeStd.setVisibility(View.VISIBLE);
        btn_showGrp.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


    }

    //Hide All Content (Used mainly to hide Content until Data is retrieved)
    void hideContent() {
        progressBar.setVisibility(View.VISIBLE);
        txt_arhnID.setVisibility(View.INVISIBLE);
        txt_stdName.setVisibility(View.INVISIBLE);
        txt_stdSchool.setVisibility(View.INVISIBLE);
        txt_stdGender.setVisibility(View.INVISIBLE);
        txt_stdCategory.setVisibility(View.INVISIBLE);
        txt_stdGroup.setVisibility(View.INVISIBLE);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        e.setVisibility(View.INVISIBLE);
        f.setVisibility(View.INVISIBLE);
        btn_removeStd.setVisibility(View.INVISIBLE);
        btn_getFacDetails.setVisibility(View.INVISIBLE);
        btn_showGrp.setVisibility(View.INVISIBLE);
    }



    //Test to see if Incoming ID is Valid
    void studentIsValid() {
        DatabaseReference evRef = database.getReference("Events").child(EventName).child("Groups");
        evRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int st = 0;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    //Test to see if ARHN ID Exists in Any of the Groups
                    if (d.hasChild(arhnID)) {
                        GroupsName = d.getKey();
                        txt_stdGroup.setText(GroupsName);
                        st = 1;
                        getDetails();
                    }
                }

                if (st == 0) {
                    //Could Not Find the Relevant ID
                    Toast.makeText(getApplicationContext(), "Student Hasn't Participated", Toast.LENGTH_SHORT).show();
                    /*
                    *Redirect to Event_ShowStudentScanActivity_Group
                    * Extras Required
                    * EventName
                     */
                    Intent intent = new Intent(Event_ShowStudent_GroupEvent.this, Event_ShowStudentScanActivity_Group.class);
                    intent.putExtra("EventName", EventName);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    //Get the Relevant Details of the Student once Student Existance is confirmed
    void getDetails() {
        studentsRef = database.getReference("Students").child(arhnID);
        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //If Student Exists
                if (snapshot.hasChild("category")) {
                    Student student = snapshot.getValue(Student.class);
                    String nm = student.getStdName(), sc = student.getSchool(), gen = student.getGender(), ca = student.getCategory();
                    txt_arhnID.setText(arhnID);
                    txt_stdName.setText(nm);
                    txt_stdSchool.setText(sc);
                    txt_stdGender.setText(gen);
                    txt_stdCategory.setText(ca);
                    sch = sc;
                    showContent();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid ID", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Event_ShowStudent_GroupEvent.this, Event_StudentList_GroupEvent.class);
                    intent.putExtra("EventName", EventName);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

