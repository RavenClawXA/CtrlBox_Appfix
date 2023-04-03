package com.example.ctrlbox_app;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private  TextView boxid;
    private TextView vendor;
    private TextView vendorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String BoxId = (getIntent().getStringExtra("ScannedData"));

        boxid = findViewById(R.id.TextBoxId);
        vendor = findViewById(R.id.ViewVendor);
        vendorname = findViewById(R.id.ViewVendorName);
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
       getDataId(BoxId);
        }

        private void getDataId (String BoxId){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.10.114:5000/api/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<Datamodels> call = retrofitAPI.getDataById(Integer.parseInt((BoxId)),"Vendor,VendorName,Trandate");
            Toast.makeText(MainActivity.this, BoxId, Toast.LENGTH_SHORT).show();
            call.enqueue(new Callback<Datamodels>(){
                @Override
                public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                        if (response.isSuccessful()){
                            Log.d("MainActivity", "BoxId: " + BoxId);
                            Datamodels data = response.body();
                            vendor.setText(data.getVendor());
                            vendorname.setText(data.getVendorName());
                        }
                }

                @Override
                public void onFailure(Call<Datamodels> call, Throwable t) {
                    Log.d("MainActivity", "BoxId: " + BoxId);
                    Toast.makeText(MainActivity.this, "Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
}