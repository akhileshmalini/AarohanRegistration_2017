package com.amrita.aarohanregistration.Events_Aarohan.Induvidual;

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

public class Event_StudentProfile_Induvidual extends AppCompatActivity {
    ImageView img_status;
    TextView txt_status,txt_arhnIdtxt,txt_stdName,txt_schoolName,txt_stdGender,txt_stdCategory,a,b,c,d,e;
    ProgressBar progressBar;
    String cate,arhnID,groupName;
    FirebaseDatabase database;
    String EventName;
    Button btn_addNew, btn_remove;
    //References
    DatabaseReference categoryRef,eventStdRef,studentsRef;

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_studentprofile_induvidua);
        database= FirebaseDatabase.getInstance();

        //Get Intent Extras
        arhnID=getIntent().getExtras().getString("ArhnId");
        EventName =getIntent().getExtras().getString("EventName");

        //View Bindings
        img_status= (ImageView) findViewById(R.id.imageView);
        txt_status= (TextView) findViewById(R.id.textView3);
        btn_addNew= (Button) findViewById(R.id.button3);
        btn_remove= (Button) findViewById(R.id.button2);
        txt_arhnIdtxt = (TextView) findViewById(R.id.txt_id);
        txt_stdName = (TextView) findViewById(R.id.txt_name);
        txt_schoolName = (TextView) findViewById(R.id.txt_school);
        txt_stdGender = (TextView) findViewById(R.id.txt_gender);
        txt_stdCategory = (TextView) findViewById(R.id.txt_category);
        progressBar= (ProgressBar) findViewById(R.id.progressBar2);
        a= (TextView) findViewById(R.id.textView4);
        b= (TextView) findViewById(R.id.textView6);
        c= (TextView) findViewById(R.id.textView8);
        d= (TextView) findViewById(R.id.textView10);
        e= (TextView) findViewById(R.id.textView12);

        
        
        //Find Category to Which Student Belongs
        categoryRef = database.getReference("Events").child(EventName).child("Category");
        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cate=dataSnapshot.getValue().toString();
                //Find Out If Student is Valid
                studentIsValid();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });






        hideContent();

        //Remove Student From Event
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog to Get Passkey before Removal
                final Dialog passDialog = new Dialog(Event_StudentProfile_Induvidual.this, R.style.MyAlertDialogStyle);
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
                            studentsRef.child("EventsAttended").child(EventName).removeValue();
                            Toast.makeText(getApplicationContext(),"Student Successfully Removed",Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(Event_StudentProfile_Induvidual.this,Event_StudentScanActivity_Induvidual.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            intent.putExtra("EventName",EventName);
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

        //Add New Student to List
        btn_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Event_StudentScanActivity_Induvidual.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                intent.putExtra("EventName",EventName);
                startActivity(intent);
                finish();

            }
        });




    }

    void showContent(){
        progressBar.setVisibility(View.INVISIBLE);
        txt_arhnIdtxt.setVisibility(View.VISIBLE);
        txt_stdName.setVisibility(View.VISIBLE);
        txt_schoolName.setVisibility(View.VISIBLE);
        txt_stdGender.setVisibility(View.VISIBLE);
        txt_stdCategory.setVisibility(View.VISIBLE);
        a.setVisibility(View.VISIBLE);
        b.setVisibility(View.VISIBLE);
        c.setVisibility(View.VISIBLE);
        d.setVisibility(View.VISIBLE);
        e.setVisibility(View.VISIBLE);
        btn_addNew.setVisibility(View.VISIBLE);
        btn_remove.setVisibility(View.VISIBLE);
        img_status.setVisibility(View.VISIBLE);

    }

    void hideContent(){
        txt_status.setText("Retrieving Student Information...");
        txt_arhnIdtxt.setVisibility(View.INVISIBLE);
        txt_stdName.setVisibility(View.INVISIBLE);
        txt_schoolName.setVisibility(View.INVISIBLE);
        txt_stdGender.setVisibility(View.INVISIBLE);
        txt_stdCategory.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        a.setVisibility(View.INVISIBLE);
        b.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);
        d.setVisibility(View.INVISIBLE);
        btn_addNew.setVisibility(View.INVISIBLE);
        e.setVisibility(View.INVISIBLE);
        btn_remove.setVisibility(View.INVISIBLE);
        img_status.setVisibility(View.INVISIBLE);

    }

    void studentIsValid(){
         studentsRef = database.getReference("Students").child(arhnID);

        studentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild("category")) {

                    studentHasParicipated();
                }else{
                    img_status.setVisibility(View.VISIBLE);
                    txt_status.setVisibility(View.VISIBLE);
                    txt_status.setText("Invalid ID! Student does Not Exist");
                    Glide.with(getApplicationContext()).load(R.drawable.nonots)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(img_status);
                    progressBar.setVisibility(View.INVISIBLE);
                    btn_addNew.setVisibility(View.VISIBLE);
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
                    txt_status.setText("Student has Already been Added in this Event!");
                    Glide.with(getApplicationContext()).load(R.drawable.nonots)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(img_status);
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
                txt_arhnIdtxt.setText(arhnID);
                txt_stdName.setText(nm);
                txt_schoolName.setText(sc);
                txt_stdGender.setText(gen);
                txt_stdCategory.setText(ca[0]);
                if(a==1996){
                    if(ca[0].equals(cate)){
                        Glide.with(getApplicationContext()).load(R.drawable.yesokay)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(img_status);
                        txt_status.setText("Student Allowed to Enter Event");
                        DatabaseReference eventStdRef = database.getReference("Events").child(EventName).child("Students");
                            eventStdRef.child(arhnID).setValue(0);
                        studentsRef.child("EventsAttended").child(EventName).setValue(0);
                        showContent();
                    }    else{
                        Glide.with(getApplicationContext()).load(R.drawable.nonots)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .into(img_status);
                        txt_status.setText("Student Doesn't Belong to this Category");
                        txt_stdCategory.setTypeface(null, Typeface.BOLD);
                        showContent();
                        btn_remove.setVisibility(View.INVISIBLE);

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
