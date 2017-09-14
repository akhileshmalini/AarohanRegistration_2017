package com.amrita.aarohanregistration;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class Event_ShowStudent_GroupEvent extends AppCompatActivity {
    TextView arhnIdtxt,name,school,gender,group,category,a,b,c,d,e,f;
    FirebaseDatabase database;
    String arhnID,EventName;
    ProgressBar progressBar;
    Button fac;
    String sch="apple";
    String GroupsName;
    Button Remove,showGrp;
    DatabaseReference fstudentsRef,studentsRef;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_studentprofile);


        arhnID=getIntent().getExtras().getString("ArhnId");
        EventName=getIntent().getExtras().getString("EventName");

        database= FirebaseDatabase.getInstance();



        arhnIdtxt = (TextView) findViewById(R.id.textView27);
        name = (TextView) findViewById(R.id.textView22);
        school = (TextView) findViewById(R.id.textView31);
        gender = (TextView) findViewById(R.id.textView35);
        category = (TextView) findViewById(R.id.textView33);
        group= (TextView) findViewById(R.id.textView29);
        showGrp= (Button) findViewById(R.id.button14);
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);

        a= (TextView) findViewById(R.id.textView21);
        b= (TextView) findViewById(R.id.textView26);
        c= (TextView) findViewById(R.id.textView28);
        d= (TextView) findViewById(R.id.textView30);
        e= (TextView) findViewById(R.id.textView32);
        f= (TextView) findViewById(R.id.textView34);


        fac= (Button) findViewById(R.id.button5);
        Remove= (Button) findViewById(R.id.btnRemove);

        Remove.setVisibility(View.INVISIBLE);
        fac.setVisibility(View.INVISIBLE);
        showGrp.setVisibility(View.INVISIBLE);
        hideContent();

        studentIsValid();

        fac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sch.equals("Apple")){
                    Toast.makeText(getApplicationContext(),"No Faculty Information Available", Toast.LENGTH_LONG).show();
                }else{
                    fstudentsRef = database.getReference("Schools").child(sch).child("faculty");
                    fstudentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Faculty f = dataSnapshot.getValue(Faculty.class);
                            String Email =f.getEmail();
                            String Phone= ""+f.getPhoneno();
                            String Name =f.getName();
                            Intent intent =new Intent(Event_ShowStudent_GroupEvent.this,FacultyProfile.class);
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


        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog passDialog = new Dialog(Event_ShowStudent_GroupEvent.this, R.style.MyAlertDialogStyle);
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
                            DatabaseReference eventStdRef = database.getReference("Events").child(EventName).child("Students").child(arhnID);
                            eventStdRef.removeValue();

                            studentsRef.child("EventsAttended").child(EventName).removeValue();
                            DatabaseReference grpRef = database.getReference("Events").child(EventName).child("Groups").child(GroupsName).child(arhnID);
                            grpRef.removeValue();

                            Toast.makeText(getApplicationContext(),"Student Successfully Removed",Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(Event_ShowStudent_GroupEvent.this,Event_GroupDashboard.class);
                            intent.putExtra("EventName",EventName);
                            intent.putExtra("groupName",GroupsName);
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


            showGrp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(getApplicationContext(),Event_GroupDashboard.class);
                    intent.putExtra("groupName",GroupsName);
                    intent.putExtra("EventName",EventName);
                    startActivity(intent);
                    finish();

                }
            });







    }


    void showContent(){
        arhnIdtxt.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        school.setVisibility(View.VISIBLE);
        gender.setVisibility(View.VISIBLE);
        category.setVisibility(View.VISIBLE);
        group.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        f.setVisibility(View.VISIBLE);


    }

    void hideContent(){

        arhnIdtxt.setVisibility(View.INVISIBLE);
        name.setVisibility(View.INVISIBLE);
        school.setVisibility(View.INVISIBLE);
        gender.setVisibility(View.INVISIBLE);
        category.setVisibility(View.INVISIBLE);
        group.setVisibility(View.INVISIBLE);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        e.setVisibility(View.INVISIBLE);
        f.setVisibility(View.INVISIBLE);

    }

    void studentIsValid(){
        DatabaseReference evRef =database.getReference("Events").child(EventName).child("Groups");
        evRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int st=0;
                for(DataSnapshot d :dataSnapshot.getChildren()){
                    if(d.hasChild(arhnID)){

                        GroupsName=d.getKey();
                        group.setText(GroupsName);
                        st=1;
                        getDetails();

                    }

                }

                if(st==0){
                    Toast.makeText(getApplicationContext(),"Student Hasn't Participated",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(Event_ShowStudent_GroupEvent.this,Event_ShowStudentScanActivity_Group.class);
                    intent.putExtra("EventName",EventName);
                    startActivity(intent);
                    finish();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


    void getDetails(){
         studentsRef = database.getReference("Students").child(arhnID);
        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("category")) {
                    Student student = snapshot.getValue(Student.class);
                    String nm = student.getStdName(), sc = student.getSchool(), gen = student.getGender(),ca=student.getCategory();

                    arhnIdtxt.setText(arhnID);
                    name.setText(nm);
                    school.setText(sc);
                    gender.setText(gen);
                    category.setText(ca);
                    sch=sc;
                    showContent();
                    fac.setVisibility(View.VISIBLE);
                    Remove.setVisibility(View.VISIBLE);
                    showGrp.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.INVISIBLE);



                }else{

                    Toast.makeText(getApplicationContext(),"Invalid ID", Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(Event_ShowStudent_GroupEvent.this,RandomStudentScanActivity.class);
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

