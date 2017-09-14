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

    TextView name, phone, email;
    Button call,emailsend;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_profile);

        String n = getIntent().getExtras().getString("Name");
        final String p = getIntent().getExtras().getString("Phone");
        final String e = getIntent().getExtras().getString("Email");


        name = (TextView) findViewById(R.id.textView15);
        phone = (TextView) findViewById(R.id.textView19);
        email = (TextView) findViewById(R.id.textView18);
        emailsend= (Button) findViewById(R.id.button7);
        call = (Button) findViewById(R.id.button6);

        name.setText(n);
        phone.setText(p);
        email.setText(e);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Phone", p);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Copied Phone Number to Clipboard",Toast.LENGTH_LONG).show();

            }
        });

        emailsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Email", e);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Copied Email to Clipboard",Toast.LENGTH_LONG).show();
            }
        });





    }
}
