package com.amrita.aarohanregistration;

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
    ImageView statusImg;
    TextView status,arhnIdtxt,name,school,gender,category,grp;
    TextView a,b,c,d,e,f;
    ProgressBar progressBar;
    String cate,arhnID,groupName;
    FirebaseDatabase database;
    String EventName;
    Button addNew, remove;
    DatabaseReference categoryRef,studentsRef,eventStdRef,grpRef;

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_studentprofile);
        database= FirebaseDatabase.getInstance();

        arhnID=getIntent().getExtras().getString("ArhnId");
        EventName =getIntent().getExtras().getString("EventName");
        groupName=getIntent().getExtras().getString("groupName");

        categoryRef = database.getReference("Events").child(EventName).child("Category");
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cate=dataSnapshot.getValue().toString();
                hideContent();
                studentIsValid();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        statusImg= (ImageView) findViewById(R.id.imageView);
        status= (TextView) findViewById(R.id.textView3);
        grp= (TextView) findViewById(R.id.textView25);

        addNew= (Button) findViewById(R.id.button3);
        remove= (Button) findViewById(R.id.button2);

        arhnIdtxt = (TextView) findViewById(R.id.txt_id);
        name = (TextView) findViewById(R.id.txt_name);
        school = (TextView) findViewById(R.id.txt_school);
        gender = (TextView) findViewById(R.id.txt_gender);
        category = (TextView) findViewById(R.id.txt_category);
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        a= (TextView) findViewById(R.id.textView4);
        b= (TextView) findViewById(R.id.textView6);
        c= (TextView) findViewById(R.id.textView8);
        d= (TextView) findViewById(R.id.textView10);
        e= (TextView) findViewById(R.id.textView12);
        f= (TextView) findViewById(R.id.textView23);




        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog passDialog = new Dialog(Event_StudentProfile_GroupEvent.this, R.style.MyAlertDialogStyle);
                passDialog.setContentView(R.layout.dialog_password);
                passDialog.setCancelable(true);
                final EditText editText = (EditText) passDialog.findViewById(R.id.editText);

                TextView text = (TextView) passDialog.findViewById(R.id.rank_dialog_text1);
                text.setText("Enter Passkey For Removal");
                final String pwd ="amma";
                Button updateButton = (Button) passDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = editText.getText().toString();
                        if (password.equals(pwd)){


                            eventStdRef.removeValue();
                             studentsRef = database.getReference("Students").child(arhnID);
                            studentsRef.child("EventsAttended").child(EventName).removeValue();
                             grpRef = database.getReference("Events").child(EventName).child("Groups").child(groupName).child(arhnID);
                            grpRef.removeValue();




                            Toast.makeText(getApplicationContext(),"Student Successfully Removed",Toast.LENGTH_SHORT).show();

                            Intent intent =new Intent(Event_StudentProfile_GroupEvent.this,Event_GroupDashboard.class);
                            intent.putExtra("EventName",EventName);
                            intent.putExtra("groupName",groupName);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(getApplicationContext(),"Invalid Passkey",Toast.LENGTH_SHORT).show();
                        }
                        passDialog.dismiss();
                    }
                });
                passDialog.show();
                Window window = passDialog.getWindow();
                window.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);


            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Event_GroupDashboard.class);
                intent.putExtra("groupName",groupName);
                intent.putExtra("EventName",EventName);
                intent.putExtra("Category",cate);
                startActivity(intent);
                finish();

            }
        });




    }

    void showContent(){
        progressBar.setVisibility(View.INVISIBLE);
        arhnIdtxt.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        grp.setVisibility(View.VISIBLE);
        school.setVisibility(View.VISIBLE);
        gender.setVisibility(View.VISIBLE);
        category.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);

        addNew.setVisibility(View.VISIBLE);
        remove.setVisibility(View.VISIBLE);
        statusImg.setVisibility(View.VISIBLE);

    }

    void hideContent(){
        status.setText("Retrieving Student Information...");

        arhnIdtxt.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        school.setVisibility(View.INVISIBLE);
        grp.setVisibility(View.INVISIBLE);
        gender.setVisibility(View.INVISIBLE);
        category.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        addNew.setVisibility(View.INVISIBLE);
        e.setVisibility(View.INVISIBLE);
        f.setVisibility(View.INVISIBLE);
        remove.setVisibility(View.INVISIBLE);
        statusImg.setVisibility(View.INVISIBLE);

    }

    void studentIsValid(){
        studentsRef = database.getReference("Students").child(arhnID);
        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("category")) {
                    studentHasParicipated();
                }else{
                    statusImg.setVisibility(View.VISIBLE);
                    status.setVisibility(View.VISIBLE);
                    status.setText("Invalid ID! Student does Not Exist");
                    Glide.with(getApplicationContext()).load(R.drawable.nonots)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(statusImg);
                    progressBar.setVisibility(View.INVISIBLE);
                    addNew.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    void studentHasParicipated(){

        eventStdRef = database.getReference("Events").child(EventName).child("Students").child(arhnID);
        eventStdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue()!=null) {
                    Toast.makeText(getApplicationContext(),""+snapshot.getValue(),Toast.LENGTH_SHORT).show();
                    getStudentDetails(21);
                    status.setText("Student has Already been Added in this Event!");
                    Glide.with(getApplicationContext()).load(R.drawable.nonots)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(statusImg);
                    showContent();
                }else{
                    getStudentDetails(1996);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    String getStudentDetails(final int a){

        final String[] ca = new String[1];
        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Student student =dataSnapshot.getValue(Student.class);
                String nm=student.getStdName(),sc=student.getSchool(),gen=student.getGender();
                ca[0] =student.getCategory();
                arhnIdtxt.setText(arhnID);
                name.setText(nm);
                school.setText(sc);
                gender.setText(gen);
                category.setText(ca[0]);
                grp.setText(groupName);
                if(a==1996){

                    if(ca[0].equals(cate)){
                        Glide.with(getApplicationContext()).load(R.drawable.yesokay)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(statusImg);
                        status.setText("Student Allowed to Enter Event");
                        DatabaseReference eventStdRef = database.getReference("Events").child(EventName).child("Students");
                            eventStdRef.child(arhnID).setValue(0);
                        studentsRef.child("EventsAttended").child(EventName).setValue(0);
                        DatabaseReference grpRef = database.getReference("Events").child(EventName).child("Groups").child(groupName);
                        grpRef.child(arhnID).setValue(0);


                        showContent();
                    }    else{
                        Glide.with(getApplicationContext()).load(R.drawable.nonots)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(statusImg);

                        status.setText("Student Doesn't Belong to this Category");

                        category.setTypeface(null, Typeface.BOLD);
                        showContent();
                        f.setVisibility(View.INVISIBLE);
                        grp.setVisibility(View.INVISIBLE);
                        remove.setVisibility(View.INVISIBLE);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return ca[0];

    }







}
