package com.example.ctrlbox_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

public class ScanActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);

            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setOrientationLocked(false);
            integrator.setCaptureActivity(CaptureActivity.class);
            Toast.makeText(this, "Scan QRCode", Toast.LENGTH_LONG).show();
            integrator.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,@Nullable Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();

            } else {
                    String scannedData = result.getContents();
                Intent intent = new Intent(ScanActivity.this, MainActivity.class);
                intent.putExtra("activity_main",scannedData);
                startActivity(intent);
                finish();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        Button scnb = findViewById(R.id.Scanbtn);
        scnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanActivity.this, CaptureActivity.class);
                startActivity(getIntent());
                finish();
            }
        });

    }

}




