package com.example.ctrlbox_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Boxtable extends AppCompatActivity {

    private RetrofitAPI retrofitAPI;
    TextView boxId ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxtable);

        boxId = findViewById(R.id.text_boxid);
        TableLayout tableLayout = findViewById(R.id.tableLayout);


        Intent rec = getIntent();
        String boxtableid = rec.getStringExtra("Boxtableid");
        boxId.setText(boxtableid);
        //Log.d("Boxtable", "boxacc" + boxtableid);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://49.0.65.4:3002/ctrl/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<Datamodels>> call10 = retrofitAPI.getLogById(boxtableid);
        //Log.d("Boxtable", "boxaccess" + retrofitAPI.getLogById(boxtableid));
        call10.enqueue(new Callback<List<Datamodels>>() {
            @Override
            public void onResponse(Call<List<Datamodels>> call, Response<List<Datamodels>> response) {

               if (!response.isSuccessful()) {

               }
                    List<Datamodels> datamodels = response.body();
                    Log.d("Boxtable", "Boxacc: " + "234");
                        for (Datamodels model : datamodels) {
                            Log.d("Boxtable", "Boxacc: " + model.getBoxId()+" "+model.getGetFrom()+" "+model.getSendTo()+" "+model.getTransDate());

                            TableRow row = new TableRow(Boxtable.this);

                            TextView boxid = new TextView(Boxtable.this);
                            boxid.setText(String.valueOf(model.getBoxId()));

                            TextView GetFrom = new TextView(Boxtable.this);
                            GetFrom.setText(String.valueOf(model.getGetFrom()));

                            TextView SendTo = new TextView(Boxtable.this);
                            SendTo.setText(String.valueOf(model.getSendTo()));

                            TextView TransDate = new TextView(Boxtable.this);
                            TransDate.setText(String.valueOf(model.getTransDate()));

                            TextView TransType = new TextView(Boxtable.this);
                            TransType.setText(String.valueOf(model.getTransType()));

                            row.addView(boxid);
                            row.addView(GetFrom);
                            row.addView(SendTo);
                            row.addView(TransDate);
                            row.addView(TransType);

                            tableLayout.addView(row);
                        }
                }

            @Override
            public void onFailure(Call<List<Datamodels>> call, Throwable t) {
                Toast.makeText(Boxtable.this, "error"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        }
        }
