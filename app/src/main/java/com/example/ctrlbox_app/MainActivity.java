package com.example.ctrlbox_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
    private TextView txt_boxid, txt_date,txt_status, txt_result_add, txt_result_add_log, txt_result_update, sendto, txt_result_add1,textVendor,textTo;
    private RetrofitAPI retrofitAPI;
    private Button btn_in, btn_out, bbtn, btn_add;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_boxid = findViewById(R.id.text_boxid);
        textVendor= findViewById(R.id.textVendor);
        textTo= findViewById(R.id.textTo);
        txt_date = findViewById(R.id.text_date);
        txt_status = findViewById(R.id.text_status);
        txt_result_add = findViewById(R.id.txt_result_add);
        txt_result_add_log = findViewById(R.id.txt_result_add_log);
        txt_result_update = findViewById(R.id.txt_result_update);
        //txt_result_add1 = findViewById(R.id.txt_result_add1);
        sendto = findViewById(R.id.event);

        TextView timeTextView = findViewById(R.id.clockView);
        TimeClock timeClock = new TimeClock(timeTextView);
        timeClock.start();

        final String num_BoxId = (getIntent().getStringExtra("ScannedData"));
        txt_boxid.setText(num_BoxId);

        Calendar calendar;
        calendar = Calendar.getInstance();
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        String datetime = iso8601Format.format(calendar.getTime());


        bbtn = findViewById(R.id.Backbtn);
        btn_in = findViewById(R.id.btn_in);
        btn_out = findViewById(R.id.btn_out);
        btn_add = findViewById(R.id.addbtn);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.10.114:5000/api/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<Datamodels>> call = retrofitAPI.getAllBoxTrans();
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
                    Log.d("", "Logcess51 " + get.getBoxId() + " " + get.getVendor() + " " + get.getTransDate() + " " + get.getTransType());
                    if (get.getBoxId().equals(num_BoxId)) {
                        foundDatamodel = get;
                        break;
                    }

                }
                if (foundDatamodel != null) {
                    Log.d("", "Logcess52 " + "1");
                    txt_date.setText(foundDatamodel.getTransDate());
                    txt_status.setText(foundDatamodel.getTransType());
                    if(foundDatamodel.getTransType().equals("In")) {
                        textVendor.setText(foundDatamodel.getVendor());
                        textTo.setText(foundDatamodel.getGetFrom());
                        btn_add.setVisibility(View.INVISIBLE);
                        btn_in.setVisibility(View.INVISIBLE);
                       // sendto.setText("GetFrom ");

                    }else {
                        textVendor.setText(foundDatamodel.getVendor());
                        textTo.setText(foundDatamodel.getSendTo());
                        btn_add.setVisibility(View.INVISIBLE);
                        btn_out.setVisibility(View.INVISIBLE);
                       // sendto.setText("Send To ");

                    }
                } else {
                    Log.d("", "Logcess52 " + "0");
                    btn_in.setVisibility(View.INVISIBLE);
                    btn_out.setVisibility(View.INVISIBLE);
                    //textVendor.setText("CYF");

                    String check = num_BoxId;
                    int count = check.length();
                    int pattern = 10;

                    if (count == pattern) {

                        btn_in.setVisibility(View.INVISIBLE);
                        btn_out.setVisibility(View.INVISIBLE);
                    } else {
                        Log.d("", "Logcess52 " + "0");
                        Toast.makeText(MainActivity.this, "Box data is empty Reject", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                        startActivity(intent);
                        finish();
                    }
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
                    txt_status.setText("In");
                    txt_date.setText(datetime);
                    updateBoxTrans(txt_boxid.getText().toString(),textVendor.getText().toString(),"-",textTo.getText().toString(),txt_date.getText().toString(),txt_status.getText().toString());
                    addLogBox(txt_boxid.getText().toString(),textVendor.getText().toString(),"-",textTo.getText().toString(),txt_date.getText().toString(),txt_status.getText().toString());
                    btn_in.setVisibility(View.INVISIBLE);
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SendActivity.class);
                intent.putExtra("num_BoxId",txt_boxid.getText().toString());
                intent.putExtra("Vendor",textVendor.getText().toString());
                startActivity(intent);

//                txt_status.setText("Out");
//                txt_date.setText(datetime);
//                updateBoxTrans(txt_boxid.getText().toString(),spn_vendor.getSelectedItem().toString(),"-",spn_event.getSelectedItem().toString(),txt_date.getText().toString(),txt_status.getText().toString());
//                addLogBox(txt_boxid.getText().toString(),spn_vendor.getSelectedItem().toString(),"-",spn_event.getSelectedItem().toString(),txt_date.getText().toString(),txt_status.getText().toString());
//                btn_out.setVisibility(View.INVISIBLE);
             }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_status.setText("none");
                txt_date.setText(datetime);
                addBoxCtrl(txt_boxid.getText().toString(),"CYF","CYF");
                addBoxTrans(txt_boxid.getText().toString(),"CYF", "-", "-", txt_date.getText().toString(), txt_status.getText().toString());
                btn_add.setVisibility(View.INVISIBLE);
            }
        });
    }

    //-------------------------------------Button Update---------------------------------------------------//
    public void updateBoxTrans(String BoxId, String Vendor, String GetFrom, String SendTo, String TransDate, String TransType){
        Datamodels modal_updateBoxTrans = new Datamodels(BoxId, Vendor, GetFrom, SendTo, TransDate, TransType);
        final String num_BoxId = (getIntent().getStringExtra("ScannedData"));
        Call<List<Datamodels>> call3 = retrofitAPI.updateBoxTrans(num_BoxId,modal_updateBoxTrans);
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

    //------------------------//
    public void addBoxCtrl(String BoxId, String BoxName, String Vendor){
        Datamodels_BoxCtrl datamodels_boxCtrl = new Datamodels_BoxCtrl(BoxId, BoxName, Vendor);
        Call<Datamodels_BoxCtrl> call4 = retrofitAPI.addBoxCtrl(datamodels_boxCtrl);
        call4.enqueue(new Callback<Datamodels_BoxCtrl>() {
            @Override
            public void onResponse(Call<Datamodels_BoxCtrl> call, Response<Datamodels_BoxCtrl> response) {
                txt_result_add.setText("addBoxCtrl success");
            }

            @Override
            public void onFailure(Call<Datamodels_BoxCtrl> call, Throwable t) {
                txt_result_add.setText("addBoxCtrl error : " + t.getMessage());
            }
        });
    }

    public void addBoxTrans(String BoxId, String Vendor, String GetFrom, String SendTo, String TransDate, String TransType){
        Datamodels datamodels = new Datamodels(BoxId, Vendor, GetFrom, SendTo, TransDate, TransType);
        Call<Datamodels> call5 = retrofitAPI.addBoxTrans(datamodels);
        call5.enqueue(new Callback<Datamodels>() {
            @Override
            public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                txt_result_add.setText("addBoxTrans success");
            }

            @Override
            public void onFailure(Call<Datamodels> call, Throwable t) {
                txt_result_add.setText("addBoxTrans error : " + t.getMessage());
            }
        });
    }

    public void  addLogBox(String BoxId, String Vendor, String GetFrom, String SendTo, String TransDate, String TransType){
        Datamodels datamodels = new Datamodels(BoxId, Vendor, GetFrom, SendTo, TransDate, TransType);
        Call<Datamodels> call6 = retrofitAPI.addLogBox(datamodels);
        call6.enqueue(new Callback<Datamodels>() {
            @Override
            public void onResponse(Call<Datamodels> call, Response<Datamodels> response) {
                txt_result_add_log.setText("AddLogBox success");
            }

            @Override
            public void onFailure(Call<Datamodels> call, Throwable t) {
                txt_result_add_log.setText("AddLogBox error: " + t.getMessage());
            }
        });

    }


}
