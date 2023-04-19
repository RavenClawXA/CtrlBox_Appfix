package com.example.ctrlbox_app;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class TimeClock {

    private Handler handler;
    private SimpleDateFormat sdf;
    private TextView clock;

    public TimeClock(TextView clock) {
        this.clock = clock;
        handler = new Handler();
        sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
    }
    private Runnable updateTimeRunnable = new Runnable(){
        @Override
        public void run() {
            long currerntTimeMillis = System.currentTimeMillis();
            String currentTimeString = sdf.format(new Date(currerntTimeMillis));
            clock.setText(currentTimeString);
            handler.postDelayed(this, 1000);
        }
    };
    public void start(){
        handler.post(updateTimeRunnable);
    }
    public void stop(){
        handler.removeCallbacks(updateTimeRunnable);
    }
    public String getTimeString() {
        long currentTimeMillis = SystemClock.elapsedRealtime();
        return sdf.format(new Date(currentTimeMillis));
    }
}
