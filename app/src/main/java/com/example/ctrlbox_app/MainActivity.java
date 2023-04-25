package com.example.ctrlbox_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView txt_boxid, txt_date,txt_status, txt_result_add, txt_result_add_log, txt_result_update, event;
    private RetrofitAPI retrofitAPI;
    private Button btn_in, btn_out, bbtn;

    private Spinner spn_vendor, spn_event;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar;
        calendar = Calendar.getInstance();
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        String datetime = iso8601Format.format(calendar.getTime());

        txt_boxid = findViewById(R.id.text_boxid);
        spn_vendor = findViewById(R.id.spinner_vendor);
        spn_event = findViewById(R.id.spinner_event);
        txt_date = findViewById(R.id.text_date);
        txt_status = findViewById(R.id.text_status);
        txt_result_add = findViewById(R.id.txt_result_add);
        txt_result_add_log = findViewById(R.id.txt_result_add_log);
        txt_result_update = findViewById(R.id.txt_result_update);
        event = findViewById(R.id.event);

        //clock = findViewById(R.id.clockView);// clock พี่ยังเอาอยู่มั้ย เอาไว้ก่อนเดี่ยวผมหาวิธีใส่เอง
        //timeClock = new TimeClock(clock);
        //timeClock.start();
        //String currentTimeString = timeClock.getTimeString();

        final String num_BoxId = (getIntent().getStringExtra("ScannedData"));
        txt_boxid.setText(num_BoxId);

        bbtn = findViewById(R.id.Backbtn);
        btn_in = findViewById(R.id.btn_in);
        btn_out = findViewById(R.id.btn_out);

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.10.114:5000/api/")
                        .addConverterFactory(new NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofitAPI = retrofit.create(RetrofitAPI.class);
               Call<List<Datamodels>> call = retrofitAPI.getAllBoxTrans();
                    Log.d("MainAcivity","logcess " +num_BoxId);
                call.enqueue(new Callback<List<Datamodels>>() {
                    @Override
                    public void onResponse(Call<List<Datamodels>> call, Response<List<Datamodels>> response) {
                        if (!response.isSuccessful()) {
                            txt_result_add.setText("Code " + response.code());
                            return;
                        }
                        List<Datamodels> datamodels = response.body();
                        Datamodels foundDatamodel = null;
                        for (Datamodels get : datamodels) {
                            Log.d("","Logcess55 " +get.getBoxId()+" "+ get.getVendor()+" "+get.getGetFrom() +" "+get.getSendTo() +" "+get.getTransDate() +" "+get.getTransType());
                            if (get.getBoxId().equals(num_BoxId)) {
                                foundDatamodel = get;
                                break;
                            }
                        }
                        if (foundDatamodel != null) {
                            Log.d("", "Logcess52 " + "1");
                            txt_date.setText(foundDatamodel.getTransType());
                            txt_status.setText(foundDatamodel.getTransType());
                            event.setText("From to : ");
                            //txt_date.setText(foundDatamodel.getTransDate());
                            //status.setText(foundDatamodel.getTransType());
                            //btn_in.setVisibility(View.INVISIBLE);
                        } else {
                            Log.d("", "Logcess52 " + "0");
                    }
                }
                    @Override
                    public void onFailure(Call<List<Datamodels>> call, Throwable t) {
                        txt_result_add.setText(t.getMessage());
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
                        txt_date.setText(datetime);
                        txt_status.setText("In");
                        Log.d("Mainactivity","logshow"+txt_status);
                        updateBoxTrans(txt_boxid.getText().toString(),spn_vendor.getSelectedItem().toString(),spn_event.getSelectedItem().toString(),"",txt_date.getText().toString(),txt_status.getText().toString());
                        addHistory(txt_boxid.getText().toString(),spn_vendor.getSelectedItem().toString(),spn_event.getSelectedItem().toString(),"",txt_date.getText().toString(),txt_status.getText().toString());
                        btn_in.setVisibility(View.INVISIBLE);
                    }
                });
                btn_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txt_date.setText(datetime);
                        txt_status.setText("Out");
                        Log.d("Mainactivity","logshow"+txt_status);
                        updateBoxTrans(txt_boxid.getText().toString(),spn_vendor.getSelectedItem().toString(),"",spn_event.getSelectedItem().toString(),txt_date.getText().toString(),txt_status.getText().toString());
                        addHistory(txt_boxid.getText().toString(),spn_vendor.getSelectedItem().toString(),"",spn_event.getSelectedItem().toString(),txt_date.getText().toString(),txt_status.getText().toString());
                        Log.d("Mainactivity","log delete");

                        btn_out.setVisibility(View.INVISIBLE);
                    }
                });
    }
//    @Override
//    protected void onResume(){
//        super.onResume();
//        timeClock.start();
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        timeClock.stop();
//    }
    //-------------------------------------Button Update---------------------------------------------------//
    public void updateBoxTrans(String BoxId, String Vendor, String GetFrom, String SendTo, String TransDate, String TransType){
    Datamodels modal_updateBoxTrans = new Datamodels(BoxId, Vendor, GetFrom, SendTo, TransDate, TransType);
    final String num_BoxId = (getIntent().getStringExtra("ScannedData"));
    Call<Datamodels> call3 = retrofitAPI.updateBoxTrans(num_BoxId,modal_updateBoxTrans);
    call3.enqueue(new Callback<Datamodels>() {
        @Override
        public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
            txt_result_update.setText("success updateBoxTrans: ");
        }

        @Override
        public void onFailure(Call<Datamodels> call, Throwable t) {
            txt_result_update.setText("updateBoxTrans Error: " + t.getMessage());
        }
    });
}
    //-----------------------------------Button in/out addHistory----------------------------------------------//
    public void addHistory(String BoxId, String Vendor, String GetFrom, String SendTo, String TransDate, String TransType){
        Datamodels modal_add_Log = new Datamodels(BoxId, Vendor, GetFrom, SendTo, TransDate, TransType);
        Call<Datamodels> call4 = retrofitAPI.addLogBox(modal_add_Log);
        call4.enqueue(new Callback<Datamodels>() {
            @Override
            public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                txt_result_add_log.setText("Success addHistory");
            }

            @Override
            public void onFailure(Call<Datamodels> call, Throwable t) {
                txt_result_add_log.setText("addHistory Error: " + t.getMessage());
            }
        });
    }

    //-------------------------------------add to BoxCtrl and BoxTrans---------------------------------------------------//
    public void addBoxCtrl(String BoxId, String BoxName, String Vendor){
        Datamodels_BoxCtrl modal_addBoxCtrl = new Datamodels_BoxCtrl(BoxId, BoxName, Vendor);
        Call<Datamodels_BoxCtrl> call5 = retrofitAPI.addBoxCtrl(modal_addBoxCtrl);
        call5.enqueue(new Callback<Datamodels_BoxCtrl>() {
            @Override
            public void onResponse(Call<Datamodels_BoxCtrl> call, Response<Datamodels_BoxCtrl> response) {
                txt_status.setText("Success addBoxCtrl");
            }

            @Override
            public void onFailure(Call<Datamodels_BoxCtrl> call, Throwable t) {
                txt_status.setText("addBoxCtrl Error: " + t.getMessage());
            }
        });
    }

    public void addBoxTrans(String BoxId, String Vendor, String GetFrom, String SendTo, String TransDate, String TransType){
        Datamodels modal_addBoxTrans = new Datamodels(BoxId, Vendor, GetFrom, SendTo, TransDate, TransType);
        Call<Datamodels> call6 = retrofitAPI.addBoxTrans(modal_addBoxTrans);
        call6.enqueue(new Callback<Datamodels>() {
            @Override
            public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                txt_result_add.setText("Success addBoxTrans");
            }

            @Override
            public void onFailure(Call<Datamodels> call, Throwable t) {
                txt_result_add.setText("addBoxTrans Error: " + t.getMessage());
            }
        });
    }
}  //แค่นี้ก่อน พรุ่งนี้ค่อยมาเทส ok ty เด๋วไล่อ่านแปป เหมือนเดมิแหละพี่

