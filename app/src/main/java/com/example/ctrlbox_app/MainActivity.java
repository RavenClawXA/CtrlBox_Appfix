package com.example.ctrlbox_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView boxid, vendor, vendorname, trandate, trantype;
       Button btnsend, btnget, bbtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.Backbtn);
        boxid = findViewById(R.id.TextBoxId);
        boxid = findViewById(R.id.TextBoxId);



        bbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
                finish();
            }
        });
         String scannedData = getIntent().getStringExtra("activity_main");
         boxid.setText(scannedData);

        }

    }








