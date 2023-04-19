package com.example.ctrlbox_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView boxid, status, textView, clock;
    private TimeClock timeClock;
    private TextView vendor;
    private TextView vendorname;
    private RetrofitAPI retrofitAPI;
    private Button btn_in, btn_out, bbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boxid = findViewById(R.id.TextBoxId);
        vendor = findViewById(R.id.ViewVendor);
        vendorname = findViewById(R.id.ViewVendorName);
        vendorname = findViewById(R.id.ViewVendorName);
        status = findViewById(R.id.TrantypView);
        textView = findViewById(R.id.test);

        clock = findViewById(R.id.clockView);
        timeClock = new TimeClock(clock);

       final String BoxId = (getIntent().getStringExtra("ScannedData"));
        boxid.setText(BoxId);

        /*TextView trandate = findViewById(R.id.TranDateView);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateString = dateFormat.format(currentDate);
        trandate.setText(currentDateString);*/

        bbtn = findViewById(R.id.Backbtn);
        btn_in = findViewById(R.id.btn_in);
        btn_out = findViewById(R.id.btn_out);

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.10.114:5000/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofitAPI = retrofit.create(RetrofitAPI.class);
               Call<List<Datamodels>> call = retrofitAPI.getAllBC();
                    Log.d("MainAcivity","logcess" +BoxId);
                call.enqueue(new Callback<List<Datamodels>>() {
                    @Override
                    public void onResponse(Call<List<Datamodels>> call, Response<List<Datamodels>> response) {
                        if (!response.isSuccessful()) {
                            status.setText("Code " + response.code());
                            return;
                        }
                        List<Datamodels> datamodels = response.body();
                        Datamodels foundDatamodel = null;
                        for (Datamodels get : datamodels) {
                            Log.d("","Logcess51 " +get.getBoxId()+" "+get.getVendor());
                            if (get.getBoxId().equals(BoxId)) {
                                foundDatamodel = get;
                                break;
                            }
                        }
                        if (foundDatamodel != null) {
                            Log.d("", "Logcess52 " + "1");
                            vendor.setText(foundDatamodel.getVendor());
                            vendorname.setText(foundDatamodel.getVendorName());

                            btn_in.setVisibility(View.INVISIBLE);
                        } else {
                            Log.d("", "Logcess52 " + "0");
                            getTb();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Datamodels>> call, Throwable t) {
                        status.setText(t.getMessage());
                    }

                    public void getTb() {
                        Call<List<Datamodels>> call2 = retrofitAPI.getAllTB();
                        call2.enqueue(new Callback<List<Datamodels>>() {
                            @Override
                            public void onResponse(Call<List<Datamodels>> call, Response<List<Datamodels>> response) {
                                if (!response.isSuccessful()) {
                                    status.setText("Code " + response.code());
                                    return;
                                }
                                List<Datamodels> datamodels = response.body();
                                Datamodels foundDatamodel = null;
                                for (Datamodels get : datamodels) {
                                    Log.d("","Logcess51 " +get.getBoxId()+" "+get.getVendor());
                                    if (get.getBoxId().equals(BoxId)) {
                                        foundDatamodel = get;
                                        break;
                                    }
                                }
                                if (foundDatamodel != null) {
                                    Log.d("", "Logcess52 " + "1");
                                    vendor.setText(foundDatamodel.getVendor());
                                    vendorname.setText(foundDatamodel.getVendorName());

                                    btn_out.setVisibility(View.INVISIBLE);
                                } else {
                                    Log.d("", "Logcess52 " + "0");
                                    Toast.makeText(MainActivity.this, "Box data is empty Reject", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Datamodels>> call, Throwable t) {

                            }
                        });
                    }
                });

                bbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
                finish();
            }
        });
                btn_in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btn_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
    }
    @Override
    protected void onResume(){
        super.onResume();
        timeClock.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        timeClock.stop();
    }
}










