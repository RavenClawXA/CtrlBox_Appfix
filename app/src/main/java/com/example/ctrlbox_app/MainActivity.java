package com.example.ctrlbox_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView boxid, status, clock, txt_date, txt_result;
    private TimeClock timeClock;
    private TextView vendor;
    private TextView vendorname;
    private RetrofitAPI retrofitAPI;
    private Button btn_in, btn_out, bbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        //simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        //dateTime = simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        //iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String iso8601String = iso8601Format.format(calendar.getTime());

        boxid = findViewById(R.id.TextBoxId);
        vendor = findViewById(R.id.ViewVendor);
        vendorname = findViewById(R.id.ViewVendorName);
        status = findViewById(R.id.TrantypView);
        txt_date = findViewById(R.id.TranDateView);
        txt_result = findViewById(R.id.txt_result);

        clock = findViewById(R.id.clockView);
        timeClock = new TimeClock(clock);
        timeClock.start();
        String currentTimeString = timeClock.getTimeString();

       final String num_BoxId = (getIntent().getStringExtra("ScannedData"));
        boxid.setText(num_BoxId);

        bbtn = findViewById(R.id.Backbtn);
        btn_in = findViewById(R.id.btn_in);
        btn_out = findViewById(R.id.btn_out);

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.10.114:5000/api/")
                        .addConverterFactory(new NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofitAPI = retrofit.create(RetrofitAPI.class);
               Call<List<Datamodels>> call = retrofitAPI.getAllBC();
                    Log.d("MainAcivity","logcess" +num_BoxId);
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
                            Log.d("","Logcess51 " +get.getBoxId()+" "+get.getVendor()+" "+get.getTransDate()+" "+get.getTransType());
                            if (get.getBoxId().equals(num_BoxId)) {
                                foundDatamodel = get;
                                break;
                            }
                        }
                        if (foundDatamodel != null) {
                            Log.d("", "Logcess52 " + "1");
                            vendor.setText(foundDatamodel.getVendor());
                            vendorname.setText(foundDatamodel.getVendorName());
                            txt_date.setText(foundDatamodel.getTransDate());
                            status.setText(foundDatamodel.getTransType());
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
                                    if (get.getBoxId().equals(num_BoxId)) {
                                        foundDatamodel = get;
                                        break;
                                    }
                                }
                                if (foundDatamodel != null) {
                                    Log.d("", "Logcess52 " + "1");
                                    vendor.setText(foundDatamodel.getVendor());
                                    vendorname.setText(foundDatamodel.getVendorName());
                                    txt_date.setText(foundDatamodel.getTransDate());
                                    status.setText(foundDatamodel.getTransType());

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
                        //txt_date.setText(currentTimeString);
                        txt_date.setText(iso8601String);
                        status.setText("In");
                        Log.d("Mainactivity","logshow"+status);

                        addToBC(boxid.getText().toString(), vendor.getText().toString(), vendorname.getText().toString(), txt_date.getText().toString(),status.getText().toString());
                        Log.d("Mainactivity","logshow"+boxid.getText()+vendor.getText()+vendorname.getText()+txt_date.getText()+status.getText());

                        addHistory(boxid.getText().toString(), vendor.getText().toString(), vendorname.getText().toString(), txt_date.getText().toString(),status.getText().toString());
                        Log.d("Mainactivity","logshow"+boxid.getText()+vendor.getText()+vendorname.getText()+txt_date.getText()+status.getText());

                        DeleteTB();
                        Log.d("Mainactivity","log delete");

                        btn_in.setVisibility(View.INVISIBLE);
                    }
                });


                btn_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //txt_date.setText(currentTimeString);
                        txt_date.setText(iso8601String);
                        status.setText("Out");
                        Log.d("Mainactivity","logshow"+status);
                        //update_in_BC("",vendor.getText().toString(), vendorname.getText().toString(), txt_date.getText().toString(),status.getText().toString());
                        addtoTB(boxid.getText().toString(), vendor.getText().toString(), vendorname.getText().toString(), txt_date.getText().toString(),status.getText().toString());
                        Log.d("Mainactivity","logshow"+boxid.getText()+vendor.getText()+vendorname.getText()+txt_date.getText()+status.getText());

                        addHistory(boxid.getText().toString(), vendor.getText().toString(), vendorname.getText().toString(), txt_date.getText().toString(),status.getText().toString());
                        Log.d("Mainactivity","logshow"+boxid.getText()+vendor.getText()+vendorname.getText()+txt_date.getText()+status.getText());

                        DeleteBC();
                        Log.d("Mainactivity","log delete");

                        btn_out.setVisibility(View.INVISIBLE);
                    }
                });//ปุ่มส่งมีไหมมื่อกี้ ลืมดูกดมันส์ไปหน่อย ไม่มีนิรีใหม่ แปป
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
    /* public void update_in_BC(String BoxId, String Vendor, String VendorName, String TransDate, String TransType ) {
        Datamodels modal = new Datamodels(BoxId, Vendor, VendorName, TransDate, TransType);
        final String num_BoxId = (getIntent().getStringExtra("ScannedData"));
        Call<List<Datamodels>> call3 = retrofitAPI.getPut(num_BoxId, modal);
        call3.enqueue(new Callback<List<Datamodels>>() {
            @Override
            public void onResponse(Call<List<Datamodels>> call, Response<List<Datamodels>> response) {
                Toast.makeText(MainActivity.this, "Data updated to API", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Datamodels>> call, Throwable t) {
                status.setText(t.getMessage());
            }
        });
    }*/
    //-------------------------------------Button Out---------------------------------------------------//
    public void addtoTB(String BoxId, String Vendor, String VendorName, String TransDate, String TransType){
        Datamodels modal_addTB = new Datamodels(BoxId, Vendor, VendorName, TransDate, TransType);
        Call<Datamodels> call4 = retrofitAPI.addtoTB(modal_addTB);
        call4.enqueue(new Callback<Datamodels>() {
            @Override
            public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                txt_result.setText("Success add");
            }

            @Override
            public void onFailure(Call<Datamodels> call, Throwable t) {
                txt_result.setText("addtoTB Error: " + t.getMessage());
            }
        });
    }

    public void DeleteBC(){
        final String num_BoxId = (getIntent().getStringExtra("ScannedData"));
        Call<Datamodels_del> call5 = retrofitAPI.del_BC(num_BoxId);
        call5.enqueue(new Callback<Datamodels_del>() {
            @Override
            public void onResponse(Call<Datamodels_del> call, Response<Datamodels_del> response) {
                txt_result.setText("Success delete");
            }

            @Override
            public void onFailure(Call<Datamodels_del> call, Throwable t) {
                txt_result.setText("DeleteBC Error: " + t.getMessage());
            }
        });
    }

    public void addHistory(String BoxId, String Vendor, String VendorName, String TransDate, String TransType){
        Datamodels modal_add_Log = new Datamodels(BoxId, Vendor, VendorName, TransDate, TransType);
        Call<Datamodels> call6 = retrofitAPI.addHistory(modal_add_Log);
        call6.enqueue(new Callback<Datamodels>() {
            @Override
            public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                txt_result.setText("Success addHistory");
            }

            @Override
            public void onFailure(Call<Datamodels> call, Throwable t) {
                txt_result.setText("addHistory Error: " + t.getMessage());
            }
        });
    }

    //-------------------------------------Button in---------------------------------------------------//
    public void addToBC(String BoxId, String Vendor, String VendorName, String TransDate, String TransType){
        Datamodels modal_addBC = new Datamodels(BoxId, Vendor, VendorName, TransDate, TransType);
        Call<Datamodels> call7 = retrofitAPI.addtoBC(modal_addBC);
        call7.enqueue(new Callback<Datamodels>() {
            @Override
            public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                txt_result.setText("Success add");
            }

            @Override
            public void onFailure(Call<Datamodels> call, Throwable t) {
                txt_result.setText("addtoBC Error: " + t.getMessage());
            }
        });
    }

    public void DeleteTB(){
        final String num_BoxId = (getIntent().getStringExtra("ScannedData"));
        Call<Datamodels_del> call6 = retrofitAPI.del_TB(num_BoxId);
        call6.enqueue(new Callback<Datamodels_del>() {
            @Override
            public void onResponse(Call<Datamodels_del> call, Response<Datamodels_del> response) {
                txt_result.setText("Success delete");
            }

            @Override
            public void onFailure(Call<Datamodels_del> call, Throwable t) {
                txt_result.setText("DeleteTB Error: " + t.getMessage());
            }
        });
    }
}
