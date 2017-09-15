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
    DatabaseReference WinnerRef;
    String sampleArhnID;
    Event_ShowWinnerGroupsAdapter mAdapter;
    String EventName;
    String place;
    String SchoolName;
     String grp;
    ArrayList<Event_Winner_Model> list;
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

        list = new ArrayList<>();

         WinnerRef = database.getReference("Events").child(EventName).child("Winners");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()!=0) {
                    list.clear();
                    for (DataSnapshot event : dataSnapshot.getChildren()) {

                        getSchool(event.getValue().toString(), event.getKey().toString());


                    }

                    mAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(getApplicationContext(),"No Winners Set",Toast.LENGTH_SHORT).show();
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
                grp =groupentry.getText().toString();
                place =spinner.getSelectedItem().toString();
                if(!grp.equals("")){
                    boolean go=true;
                    String tSch="",tplc="";
                    for(Event_Winner_Model grp : list){
                        if(grp.getPlace().equals(place)) {

                            go=false;
                            tSch=grp.getSchool();
                            tplc=grp.getPlace();
                            break;
                        }
                    }

                    if(go){
                        isAllowedtoEnter();
                    }else {
                        DatabaseReference StdRef = database.getReference("Winners").child(tSch).child(EventName).child(tplc);
                        StdRef.removeValue();
                        isAllowedtoEnter();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Invalid Group ID",Toast.LENGTH_SHORT).show();

                }


            }
        });









    }


    void isAllowedtoEnter(){
        final DatabaseReference EventsRef = database.getReference("Events").child(EventName).child("Groups");
        EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(grp)){
                    WinnerRef.child(place).setValue(grp);
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


    void getSchool(final String grp, final String Place){
        final ArrayList<String> arhnIds =new ArrayList<String>();
        final DatabaseReference EventsRef = database.getReference("Events").child(EventName).child("Groups");
        EventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot std :dataSnapshot.child(grp).getChildren()){
                    arhnIds.add(std.getKey());
                }
                sampleArhnID=arhnIds.get(0);
                getSchoolActual(sampleArhnID,Place,grp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



   void getSchoolActual(final String arhn, final String Place, final String group){
        DatabaseReference StdRef = database.getReference("Students").child(arhn).child("school");

        StdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SchoolName= dataSnapshot.getValue().toString();
                setWinner( group,  Place);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void setWinner(String group, String Place){

        DatabaseReference StdRef = database.getReference("Winners").child(SchoolName).child(EventName).child(Place);
        StdRef.setValue(group);
        list.add(new Event_Winner_Model(group,Place,SchoolName));
        mAdapter.notifyDataSetChanged();


    }

}
