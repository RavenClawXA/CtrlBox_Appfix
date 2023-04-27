package com.example.ctrlbox_app;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class SendActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        String[] get_from_List = {"STU","VWX","YZA","DDD","FFF","GGG"};
        String[] venderList = {"ABC","DEF","GHI","JKL","MNO","PQR"};

        RetrofitAPI retrofitAPI;
        Button backbtn,btn_out;
        Spinner spn_vendor, spn_event;
        TextView date, status,txt_result_update,txt_result_add_log,text_boxid;

        date = findViewById(R.id.text_date);
        status = findViewById(R.id.text_status);
        spn_vendor = findViewById(R.id.spinner_vendor);
        spn_event = findViewById(R.id.spinner_event);
        txt_result_update = findViewById(R.id.txt_result_update);
        txt_result_add_log = findViewById(R.id.txt_result_add_log);

        backbtn =findViewById(R.id.backbtn);
        btn_out = findViewById(R.id.btn_out);


        TextView timeTextView = findViewById(R.id.clockView);
        TimeClock timeClock = new TimeClock(timeTextView);
        timeClock.start();

        Calendar calendar;
        calendar = Calendar.getInstance();
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        String datetime = iso8601Format.format(calendar.getTime());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, get_from_List);
        spn_event.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, venderList);
        spn_vendor.setAdapter(adapter2);

        Intent rec = getIntent();
        String boxid = rec.getStringExtra("num_BoxId");


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.10.114:5000/api/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status.setText("Out");
                date.setText(datetime);
                Log.d("SendActiviy","logid 1"+ boxid); //มันเป็นค่าว่าตรงไหนฟะ chatgpt
                updateBoxTrans(boxid,spn_vendor.getSelectedItem().toString(),"-",spn_event.getSelectedItem().toString(),date.getText().toString(),status.getText().toString());
                addLogBox(boxid,spn_vendor.getSelectedItem().toString(),"-",spn_event.getSelectedItem().toString(),date.getText().toString(),status.getText().toString());
                btn_out.setVisibility(View.INVISIBLE);
            }

            public void updateBoxTrans(String BoxId, String Vendor, String GetFrom, String SendTo, String TransDate, String TransType){
                Datamodels modal_updateBoxTrans = new Datamodels(BoxId, Vendor, GetFrom, SendTo, TransDate, TransType);

                Call<List<Datamodels>> call3 = retrofitAPI.updateBoxTrans(boxid,modal_updateBoxTrans);
                call3.enqueue(new Callback<List<Datamodels>>() {
                    @Override
                    public void onResponse(Call<List<Datamodels>> call, Response<List<Datamodels>> response) {
                          txt_result_update.setText("success updateBoxTrans: ");
                    }

                    @Override
                    public void onFailure(Call<List<Datamodels>> call, Throwable t) {
                        txt_result_update.setText("updateBoxTrans Error: " + t.getMessage());
                    }
                });
            }
            public void  addLogBox(String BoxId, String Vendor, String GetFrom, String SendTo, String TransDate, String TransType){
                Datamodels datamodels = new Datamodels(BoxId, Vendor, GetFrom, SendTo, TransDate, TransType);
                Call<Datamodels> call6 = retrofitAPI.addLogBox(datamodels);
                call6.enqueue(new Callback<Datamodels>() {
                    @Override
                    public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                        txt_result_add_log.setText("AddLogBox success"); // ประกาศข้างบนยังอ่ะ
                    }

                    @Override
                    public void onFailure(Call<Datamodels> call, Throwable t) {
                        txt_result_add_log.setText("AddLogBox error: " + t.getMessage());
                    }
                });

            }
        });
    }

}