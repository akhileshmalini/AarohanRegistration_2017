package com.amrita.aarohanregistration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

public class RandomStudentScanActivity extends AppCompatActivity {
    Button scanbtn , manualEntry,btn;
    EditText manualCode;
    TextView result,textTitle;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    String EventName,cate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentscan);
        scanbtn = (Button) findViewById(R.id.scanbtn);
        manualEntry = (Button) findViewById(R.id.button4);
        manualCode= (EditText) findViewById(R.id.editArhnID);
        textTitle= (TextView) findViewById(R.id.textTitle);
        btn= (Button) findViewById(R.id.button8);
        btn.setVisibility(View.INVISIBLE);
        textTitle.setText("Get All Student Details");

        result = (TextView) findViewById(R.id.result);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomStudentScanActivity.this, ScanActivity.class);


                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        manualEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String entry=manualCode.getText().toString();
                if(entry.matches("^ARHN\\d{4}")){
                    Intent intent = new Intent(RandomStudentScanActivity.this, ShowStudent.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    intent.putExtra("ArhnId",entry);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid ID",Toast.LENGTH_SHORT).show();

                }

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(RandomStudentScanActivity.this, ShowStudent.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



                        intent.putExtra("ArhnId",barcode.displayValue);
                        startActivity(intent);

                    }
                });
            }
        }
    }
}