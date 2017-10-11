package com.amrita.aarohanregistration.Events_Aarohan.Groups;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amrita.aarohanregistration.R;
import com.amrita.aarohanregistration.Student;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akhilesh on 9/7/2017.
 */

public class Event_StudentProfile_GroupEvent extends AppCompatActivity {
    ImageView img_status;
    TextView txt_status, txt_arhnID, txt_stdName, txt_schoolName, txt_stdGender, txt_stdCategory, txt_stdGroup, a, b, c, d, e, f;
    ProgressBar progressBar;
    String EventName, cate, arhnID, groupName,groupSize;
    FirebaseDatabase database;
    Button btn_addNew, btn_removeStd;
    DatabaseReference categoryRef, studentsRef, eventStdRef, grpRef;

    @Override
    public void onBackPressed() {
        //Destroy on Backpressed
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_studentprofile);
        database = FirebaseDatabase.getInstance();

        //Get Intent Extras
        arhnID = getIntent().getExtras().getString("ArhnId");
        EventName = getIntent().getExtras().getString("EventName");
        groupName = getIntent().getExtras().getString("groupName");
        groupSize=getIntent().getExtras().getString("groupSize");


        //Get the Category of the Event in Question
        categoryRef = database.getReference("Events").child(EventName).child("Category");
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cate = dataSnapshot.getValue().toString();
                //Test to see  if Student is Valid, This Function will later automatically do all the relevant work.
                studentIsValid();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //View Bindings
        img_status = (ImageView) findViewById(R.id.imageView);
        txt_status = (TextView) findViewById(R.id.textView3);
        txt_stdGroup = (TextView) findViewById(R.id.textView25);
        btn_addNew = (Button) findViewById(R.id.button3);
        btn_removeStd = (Button) findViewById(R.id.button2);
        txt_arhnID = (TextView) findViewById(R.id.txt_id);
        txt_stdName = (TextView) findViewById(R.id.txt_name);
        txt_schoolName = (TextView) findViewById(R.id.txt_school);
        txt_stdGender = (TextView) findViewById(R.id.txt_gender);
        txt_stdCategory = (TextView) findViewById(R.id.txt_category);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        //The Following are Labels and play no Significance
        a = (TextView) findViewById(R.id.textView4);
        b = (TextView) findViewById(R.id.textView6);
        c = (TextView) findViewById(R.id.textView8);
        d = (TextView) findViewById(R.id.textView10);
        e = (TextView) findViewById(R.id.textView12);
        f = (TextView) findViewById(R.id.textView23);

        //Hide All the Content
        hideContent();

        //Button When we need to Remove a Student
        btn_removeStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Because Removing is a dangerous operation, we ask request user to type a safety passkey

                //Initialize the Dialog Window
                final Dialog passDialog = new Dialog(Event_StudentProfile_GroupEvent.this, R.style.MyAlertDialogStyle);
                passDialog.setContentView(R.layout.dialog_password);
                passDialog.setCancelable(true);
                final EditText editText = (EditText) passDialog.findViewById(R.id.editText);
                TextView text = (TextView) passDialog.findViewById(R.id.rank_dialog_text1);
                text.setText("Enter Passkey For Removal");
                //Paskey for Removal here is amma
                final String pwd = "amma";
                Button updateButton = (Button) passDialog.findViewById(R.id.rank_dialog_button);

                //Button Clicked When password is Entered
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = editText.getText().toString();
                        if (password.equals(pwd)) {
                            //Remove Student From EventList
                            eventStdRef.removeValue();
                            studentsRef = database.getReference("Students").child(arhnID);
                            //Remove Student From Student's Events Attended
                            studentsRef.child("EventsAttended").child(EventName).removeValue();
                            //Remove Student From Group
                            grpRef = database.getReference("Events").child(EventName).child("Groups").child(groupName).child(arhnID);
                            grpRef.removeValue();
                            //Notify User to successful removal
                            Toast.makeText(getApplicationContext(), "Student Successfully Removed", Toast.LENGTH_SHORT).show();
                            //Go back to Dashboard
                            Intent intent = new Intent(Event_StudentProfile_GroupEvent.this, Event_GroupDashboard.class);
                            /*
                            * Required Extras
                            * EventName
                            * GroupName
                            * */
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            intent.putExtra("EventName", EventName);
                            intent.putExtra("groupName", groupName);
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


        //Button When New Student need to be Added
        btn_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    /*
                            * Required Extras
                            * EventName
                            * GroupName
                            * */
                Intent intent = new Intent(getApplicationContext(), Event_GroupDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("groupName", groupName);
                intent.putExtra("EventName", EventName);
                startActivity(intent);
                finish();
            }
        });


    }

    //Show All the Content in the View
    void showContent() {
        progressBar.setVisibility(View.INVISIBLE);
        txt_arhnID.setVisibility(View.VISIBLE);
        txt_stdName.setVisibility(View.VISIBLE);
        txt_stdGroup.setVisibility(View.VISIBLE);
        txt_schoolName.setVisibility(View.VISIBLE);
        txt_stdGender.setVisibility(View.VISIBLE);
        txt_stdCategory.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);
        btn_addNew.setVisibility(View.VISIBLE);
        btn_removeStd.setVisibility(View.VISIBLE);
        img_status.setVisibility(View.VISIBLE);

    }

    //Hide All the Content in the View
    void hideContent() {
        txt_status.setText("Retrieving Student Information...");
        progressBar.setVisibility(View.VISIBLE);
        txt_arhnID.setVisibility(View.INVISIBLE);
        txt_stdName.setVisibility(View.INVISIBLE);
        txt_schoolName.setVisibility(View.INVISIBLE);
        txt_stdGroup.setVisibility(View.INVISIBLE);
        txt_stdGender.setVisibility(View.INVISIBLE);
        txt_stdCategory.setVisibility(View.INVISIBLE);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        btn_addNew.setVisibility(View.INVISIBLE);
        e.setVisibility(View.INVISIBLE);
        f.setVisibility(View.INVISIBLE);
        btn_removeStd.setVisibility(View.INVISIBLE);
        img_status.setVisibility(View.INVISIBLE);

    }

    void studentIsValid() {

        //First and Foremost, Test to see if Student Exists
        studentsRef = database.getReference("Students").child(arhnID);
        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("category")) {
                    //Yes the Student Exists
                    studentHasParicipated();
                } else {
                    //No the Student Doesn't Exist
                    img_status.setVisibility(View.VISIBLE);
                    txt_status.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    btn_addNew.setVisibility(View.VISIBLE);
                    txt_status.setText("Invalid ID | Student does Not Exist");
                    Glide.with(getApplicationContext()).load(R.drawable.nonots)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(img_status);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    void studentHasParicipated() {
        //2nd Test To see If Student has Already Participated
        eventStdRef = database.getReference("Events").child(EventName).child("Students").child(arhnID);
        eventStdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    //Student has Already Participated in the Event
                    testSize(21);
                    txt_status.setText("Student has Already been Added in this Event");
                    Glide.with(getApplicationContext()).load(R.drawable.nonots)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(img_status);
                    showContent();
                } else {
                    //Student hasn't Already Participated in the Event
                    testSize(1996);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void testSize(final int a){

        DatabaseReference grpRef = database.getReference("Events").child(EventName).child("Groups").child(groupName);

        final int gSize=Integer.parseInt(groupSize);
        grpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int num=(int)dataSnapshot.getChildrenCount();
                if(num<gSize){
                    getStudentDetails(a);
                }else{

                    txt_status.setText("Group is Full Student Cannot be Added");
                    Glide.with(getApplicationContext()).load(R.drawable.nonots)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(img_status);
                    showContent();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    void getStudentDetails(final int a) {


        //Finally Test to see if Student Belongs to the Category of the Event




        final String[] ca = new String[1];
        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student = dataSnapshot.getValue(Student.class);
                String nm = student.getStdName(), sc = student.getSchool(), gen = student.getGender();
                ca[0] = student.getCategory();
                txt_arhnID.setText(arhnID);
                txt_stdName.setText(nm);
                txt_schoolName.setText(sc);
                txt_stdGender.setText(gen);
                txt_stdCategory.setText(ca[0]);
                txt_stdGroup.setText(groupName);

                if (a == 1996) {
                    //All is Well, Student may enter Event
                    if (ca[0].equals(cate)) {
                        Glide.with(getApplicationContext()).load(R.drawable.yesokay)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(img_status);
                        txt_status.setText("Student Allowed to Enter Event");

                        //Update Firebase at relevant Locations
                        DatabaseReference eventStdRef = database.getReference("Events").child(EventName).child("Students");
                        eventStdRef.child(arhnID).setValue(0);
                        studentsRef.child("EventsAttended").child(EventName).setValue(0);
                        DatabaseReference grpRef = database.getReference("Events").child(EventName).child("Groups").child(groupName);
                        grpRef.child(arhnID).setValue(0);
                        //Show The Hidden Content
                        showContent();
                    } else {
                        //Student Doesn't Belong to the Category
                        Glide.with(getApplicationContext()).load(R.drawable.nonots)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(img_status);
                        txt_status.setText("Student Doesn't Belong to this Category");
                        txt_stdCategory.setTypeface(null, Typeface.BOLD);
                        showContent();
                        f.setVisibility(View.INVISIBLE);
                        txt_stdGroup.setVisibility(View.INVISIBLE);
                        btn_removeStd.setVisibility(View.INVISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
