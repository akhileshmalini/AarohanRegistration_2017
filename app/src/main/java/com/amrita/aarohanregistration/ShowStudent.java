package com.amrita.aarohanregistration;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class ShowStudent extends AppCompatActivity {
    TextView arhnIdtxt,name,school,gender,category,p,a,b,c,d,e;
    FirebaseDatabase database;
    String arhnID;
    ShowStudentAdapter mAdapter;
    ProgressBar progressBar;
    Button fac;
    String sch="apple";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_scan_studentprofile);
        arhnID=getIntent().getExtras().getString("ArhnId");

        database= FirebaseDatabase.getInstance();
        arhnIdtxt = (TextView) findViewById(R.id.txt_id);
        name = (TextView) findViewById(R.id.txt_name);
        school = (TextView) findViewById(R.id.txt_school);
        gender = (TextView) findViewById(R.id.txt_gender);
        category = (TextView) findViewById(R.id.txt_category);
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        p= (TextView) findViewById(R.id.textView13);
        a= (TextView) findViewById(R.id.textView4);
        b= (TextView) findViewById(R.id.textView6);
        c= (TextView) findViewById(R.id.textView8);
        d= (TextView) findViewById(R.id.textView10);
        e= (TextView) findViewById(R.id.textView12);

        fac= (Button) findViewById(R.id.button5);

        fac.setVisibility(View.INVISIBLE);

        hideContent();

        studentIsValid();

        fac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sch.equals("Apple")){
                    Toast.makeText(getApplicationContext(),"No Faculty Information Available", Toast.LENGTH_LONG).show();

                }else{
                    DatabaseReference fstudentsRef = database.getReference("Schools").child(sch).child("faculty");

                    fstudentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Faculty f = dataSnapshot.getValue(Faculty.class);
                            String Email =f.getEmail();
                            String Phone= ""+f.getPhoneno();
                            String Name =f.getName();
                            Intent intent =new Intent(ShowStudent.this,FacultyProfile.class);
                            intent.putExtra("Name",Name);
                            intent.putExtra("Phone",Phone);
                            intent.putExtra("Email",Email);
                            startActivity(intent);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }
        });
    }


    void showContent(){
        arhnIdtxt.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        school.setVisibility(View.VISIBLE);
        gender.setVisibility(View.VISIBLE);
        category.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);


    }

    void hideContent(){

        arhnIdtxt.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        school.setVisibility(View.INVISIBLE);
        gender.setVisibility(View.INVISIBLE);
        category.setVisibility(View.INVISIBLE);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        e.setVisibility(View.INVISIBLE);
        p.setVisibility(View.INVISIBLE);

    }

    void studentIsValid(){
        DatabaseReference studentsRef = database.getReference("Students");

        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(arhnID)) {

                    Student student = snapshot.child(arhnID).getValue(Student.class);
                    String nm = student.getStdName(), sc = student.getSchool(), gen = student.getGender(),ca=student.getCategory();

                            arhnIdtxt.setText(arhnID);
                            name.setText(nm);
                            school.setText(sc);
                            gender.setText(gen);
                            category.setText(ca);
                            sch=sc;
                    showContent();
                    fac.setVisibility(View.VISIBLE);



                }else{

                    Toast.makeText(getApplicationContext(),"Invalid ID", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(ShowStudent.this,RandomStudentScanActivity.class);
                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        final ArrayList<FeedBackEvent> evList =new ArrayList<>();

        DatabaseReference EventsRef = database.getReference("Students").child(arhnID).child("EventsAttended");
        EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getChildrenCount() == 0) {
                    progressBar.setVisibility(View.INVISIBLE);
                    showContent();
                } else {
                    for (DataSnapshot event : snapshot.getChildren()) {
                        evList.add(new FeedBackEvent(event.getKey().toString(), Float.valueOf(event.getValue().toString())));
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                        showContent();
                        p.setVisibility(View.VISIBLE);

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mAdapter = new ShowStudentAdapter(evList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
}

