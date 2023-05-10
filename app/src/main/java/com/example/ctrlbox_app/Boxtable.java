package com.example.ctrlbox_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Boxtable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxtable);
    }
    final String num_BoxId = (getIntent().getStringExtra("Boxtableid"));

}