package com.example.ctrlbox_app;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String BoxId = getIntent().getStringExtra("ScannedData");
        TextView boxid = findViewById(R.id.TextBoxId);
        TextView vendor = findViewById(R.id.ViewVendor);
        TextView vendorname = findViewById(R.id.ViewVendorName);
        getDataId (BoxId,vendor.getText().toString(),vendorname.getText().toString());
        boxid.setText(BoxId);


        TextView trandate = findViewById(R.id.TranDateView);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateString = dateFormat.format(currentDate);
        trandate.setText(currentDateString);



        Button bbtn =findViewById(R.id.Backbtn);
       bbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
                finish();
            }
        });
        }

        private void getDataId (String BoxId, String Vendor, String VendorName){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http//:localhost:5000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Datamodels thebox = new Datamodels(BoxId,Vendor,VendorName);
            Call<Datamodels> call = retrofitAPI.getDataById(thebox);
            call.enqueue(new Callback<Datamodels>(){
                @Override
                public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                        try {
                            Toast.makeText(MainActivity.this, "Data", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                }

                @Override
                public void onFailure(Call<Datamodels> call, Throwable t) {
                    t.getMessage();
                }
            });
        }
}








