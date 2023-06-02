package com.example.ctrlbox_app;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScanActivity extends AppCompatActivity {
    public boolean isTorchOn = false;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(TorchOnCaptureActivity.class);
        integrator.setOrientationLocked(false);
        integrator.addExtra(appConstants.CAMERA_FLASH_ON,false);
        Toast.makeText(this, "Scan QRCode", Toast.LENGTH_LONG).show();
        integrator.initiateScan();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
            } else {
                String scannedData = result.getContents();
                Intent intent = new Intent(ScanActivity.this, MainActivity.class);
                intent.putExtra("ScannedData", scannedData);
                startActivity(intent);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        Button scnb = findViewById(R.id.Scanbtn);
        scnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getIntent());
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Open the camera and enable torch mode
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        isTorchOn = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Release the camera and turn off the torch
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
            isTorchOn = false;
        }
    }
}
