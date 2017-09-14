package com.amrita.aarohanregistration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akhilesh on 9/12/2017.
 */

public class Event_Winnner_GroupEvent extends AppCompatActivity {



    EditText groupentry;
    Spinner spinner;
    RecyclerView recyclerView;
    Button button;
    FirebaseDatabase database;
    String sampleArhnID;
    Event_ShowWinnerGroupsAdapter mAdapter;
    String EventName;
    String place;
    String SchoolName;
     String grp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_winner);
        database= FirebaseDatabase.getInstance();
        EventName=getIntent().getExtras().getString("EventName");
        groupentry= (EditText) findViewById(R.id.editText2);
        spinner = (Spinner) findViewById(R.id.spinner);
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        button= (Button) findViewById(R.id.button15);
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("1st Place");
        spinnerArray.add("2nd Place");
        spinnerArray.add("3rd Place");
        spinnerArray.add("Consolation");
        spinnerArray.add("Other");



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        final ArrayList<Event_Winner_Model> list = new ArrayList<>();

        final DatabaseReference WinnerRef = database.getReference("Events").child(EventName).child("Winners");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot event : dataSnapshot.getChildren()) {
                    list.add(new Event_Winner_Model(event.getValue().toString(),event.getKey().toString()));
                    mAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        WinnerRef.addValueEventListener(postListener);

        mAdapter = new Event_ShowWinnerGroupsAdapter(list,getApplicationContext(),EventName,SchoolName,database,Event_Winnner_GroupEvent.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);







        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference EventsRef = database.getReference("Events").child(EventName).child("Groups");

                    grp =groupentry.getText().toString();

                     place =spinner.getSelectedItem().toString();

                    EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(grp)){
                                WinnerRef.child(place).setValue(grp);
                                ArrayList<String> arhnIds =new ArrayList<String>();

                                for(DataSnapshot std :dataSnapshot.child(grp).getChildren()){
                                    arhnIds.add(std.getKey());
                                }

                                 sampleArhnID=arhnIds.get(0);
                                getSchool();

                                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();

                                //Get School Name
                                Toast.makeText(getApplicationContext(),"Group "+grp+" set to "+place,Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(getApplicationContext(),"Invalid Group ID",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

            }
        });







    }


    void getSchool(){
        DatabaseReference StdRef = database.getReference("Students").child(sampleArhnID).child("school");
        StdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SchoolName= dataSnapshot.getValue().toString();
                setWinner();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    void setWinner(){
        DatabaseReference StdRef = database.getReference("Winners").child(SchoolName).child(EventName).child(place);
        StdRef.setValue(grp);

    }

}
