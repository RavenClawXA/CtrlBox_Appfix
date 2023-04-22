package com.example.ctrlbox_app;

import android.os.Handler;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeClock {

    private Handler handler;
    private SimpleDateFormat iso8601Format;
    private TextView clock;

    public TimeClock(TextView clock) {
        this.clock = clock;
        handler = new Handler();
        iso8601Format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
       // iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    private Runnable updateTimeRunnable = new Runnable(){
        @Override
        public void run() {
            long currerntTimeMillis = System.currentTimeMillis();
            String currentTimeString = iso8601Format.format(new Date(currerntTimeMillis));
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
        long currentTimeMillis = System.currentTimeMillis();
        return iso8601Format.format(new Date(currentTimeMillis));
    }
}
