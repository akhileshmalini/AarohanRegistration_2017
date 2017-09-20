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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.Faculty;
import com.amrita.aarohanregistration.FacultyProfile;
import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.RandomStudentScanActivity;
import com.amrita.aarohanregistration.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akhilesh on 9/9/2017.
 */

/**
 * Activity to Show Details of a Single Student
 */


public class Event_ShowStudent_Induviudal extends AppCompatActivity {

    TextView txt_arhnID, txt_stdName, txt_stdSchool, txt_stdGender, txt_stdCategory, a, b, c, d, e;
    Button btn_remove, btn_facultyInfo;
    ProgressBar progressBar;

    String arhnID, EventName;
    String sch = "Apple";

    FirebaseDatabase database;
    DatabaseReference fstudentsRef, studentsRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_studentprofile_induvidual);
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
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        btn_facultyInfo = (Button) findViewById(R.id.button5);
        btn_remove = (Button) findViewById(R.id.btnRemove);

        //Unused
        a = (TextView) findViewById(R.id.textView21);
        b = (TextView) findViewById(R.id.textView26);
        c = (TextView) findViewById(R.id.textView34);
        d = (TextView) findViewById(R.id.textView30);
        e = (TextView) findViewById(R.id.textView32);


        hideContent();
        studentIsValid();

        //Button to request Faculty Information
        btn_facultyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sch.equals("Apple")) {
                    //No Faculty information available
                    Toast.makeText(getApplicationContext(), "No Faculty Information Available", Toast.LENGTH_LONG).show();
                } else {
                    //Get Faculty Information
                    fstudentsRef = database.getReference("Schools").child(sch).child("faculty");
                    fstudentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Faculty f = dataSnapshot.getValue(Faculty.class);
                            String Email = f.getEmail();
                            String Phone = "" + f.getPhoneno();
                            String Name = f.getName();
                            /*
                            * Redirect to Faculty Profile
                            * Intent Extras
                            * Name
                            * Phone
                            * Email
                            * */
                            Intent intent = new Intent(Event_ShowStudent_Induviudal.this, FacultyProfile.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


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

        //Button to remove Student from Event
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize Dialog to Request Passkey before removal
                final Dialog passDialog = new Dialog(Event_ShowStudent_Induviudal.this, R.style.MyAlertDialogStyle);
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
                            //Passkey Match, Remove Student
                            DatabaseReference eventStdRef = database.getReference("Events").child(EventName).child("Students").child(arhnID);
                            eventStdRef.removeValue();
                            //Student Removed from Event
                            studentsRef.child("EventsAttended").child(EventName).removeValue();
                            //Remmoved from Student's Events Attended
                            /*
                            * Redirect to Student List
                            * Extras
                                *Eventname
                            * */
                            Toast.makeText(getApplicationContext(), "Student Successfully Removed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Event_ShowStudent_Induviudal.this, Event_StudentList_Induvidual.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            intent.putExtra("Event", EventName);
                            startActivity(intent);
                            finish();

                        } else {
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


    void showContent() {
        txt_arhnID.setVisibility(View.VISIBLE);
        txt_stdName.setVisibility(View.VISIBLE);
        txt_stdSchool.setVisibility(View.VISIBLE);
        txt_stdGender.setVisibility(View.VISIBLE);
        txt_stdCategory.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        btn_remove.setVisibility(View.VISIBLE);
        btn_facultyInfo.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    void hideContent() {
        txt_arhnID.setVisibility(View.INVISIBLE);
        txt_stdName.setVisibility(View.INVISIBLE);
        txt_stdSchool.setVisibility(View.INVISIBLE);
        txt_stdGender.setVisibility(View.INVISIBLE);
        txt_stdCategory.setVisibility(View.INVISIBLE);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        e.setVisibility(View.INVISIBLE);
        btn_remove.setVisibility(View.INVISIBLE);
        btn_facultyInfo.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

    }

    //Test to see if Student Actually exists
    void studentIsValid() {
        studentsRef = database.getReference("Students").child(arhnID);
        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("category")) {
//                    Student Exists, Get Details
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
                    //No Such Student exists
                    Toast.makeText(getApplicationContext(), "Invalid ID", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Event_ShowStudent_Induviudal.this, RandomStudentScanActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


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

