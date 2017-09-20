package com.amrita.aarohanregistration;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Akhilesh on 9/9/2017.
 */

public class FacultyProfile extends AppCompatActivity {

    TextView txt_name, txt_phone, txt_email;
    Button call, emailsend;
    String Name, Phone, Email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_profile);

        //Getting Intent Extras
        Name = getIntent().getExtras().getString("Name");
        Phone = getIntent().getExtras().getString("Phone");
        Email = getIntent().getExtras().getString("Email");

        //View Bindings
        txt_name = (TextView) findViewById(R.id.textView15);
        txt_phone = (TextView) findViewById(R.id.textView19);
        txt_email = (TextView) findViewById(R.id.textView18);
        emailsend = (Button) findViewById(R.id.button7);
        call = (Button) findViewById(R.id.button6);

        //Set Data
        txt_name.setText(Name);
        txt_phone.setText(Phone);
        txt_email.setText(Email);


        //When CallButton is Pressed
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", Phone, null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent);
            }
        });

        //When Email Button is Pressed
        emailsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Email", Email);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Copied Email to Clipboard",Toast.LENGTH_LONG).show();
            }
        });





    }
}
