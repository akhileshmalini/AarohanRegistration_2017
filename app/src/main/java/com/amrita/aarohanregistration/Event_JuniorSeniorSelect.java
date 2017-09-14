package com.amrita.aarohanregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Akhilesh on 9/13/2017.
 */

public class Event_JuniorSeniorSelect extends AppCompatActivity {

    Button Junior,Senior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.junior_senior_select);


        Junior= (Button) findViewById(R.id.btn_junior);
        Senior= (Button) findViewById(R.id.btn_senior);

        Junior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Event_JuniorSeniorSelect.this,Event_List_Junior.class);
                startActivity(intent);

            }
        });

        Senior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Event_JuniorSeniorSelect.this,Event_List_Senior.class);
                startActivity(intent);

            }
        });




    }
}
