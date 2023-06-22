package com.example.ctrlbox_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WipActivity extends AppCompatActivity {
    TextView txt_job, txt_item, txt_username, resultwip;
    EditText txt_qty;
    Button btn_scanwip, btn_savewip;
    ImageView picture;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wip);

        txt_job = findViewById(R.id.txt_job);
        txt_item = findViewById(R.id.txt_item);
        txt_qty = findViewById(R.id.txt_qty);
        txt_username = findViewById(R.id.txt_username);
        picture = findViewById(R.id.Pic_View);
        btn_scanwip = findViewById(R.id.btn_scanwip);
        btn_savewip = findViewById(R.id.btn_savewip);
        resultwip = findViewById(R.id.txt_result_wip1);

        Intent rec = getIntent();
        String Username = rec.getStringExtra("USERNAME");
        txt_username.setText(Username);
        //picture.setImageBitmap('');

        btn_savewip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_job.getText().toString().isEmpty() && txt_item.getText().toString().isEmpty() && txt_qty.getText().toString().isEmpty()) {
                    Toast.makeText(WipActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                postData(txt_job.getText().toString(), txt_item.getText().toString(), txt_qty.getText().toString(), txt_username.getText().toString());
                int delayMillis = 2000;   //2sec
                btn_savewip.setEnabled(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_savewip.setEnabled(true);
                    }
                },delayMillis);
            }
        });

        btn_scanwip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startScanning();

            }
        });
    }
    private void startScanning() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan a QR code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String scanResult = result.getContents();
                if(scanResult.contains("||")) {
                    String[] inputArr = scanResult.split("\\|\\|");
                        txt_job.setText(inputArr[0]);
                        String job = txt_job.getText().toString();
                        job = job.trim();
                        txt_job.setText(job);

                        txt_item.setText(inputArr[3]);
                        String item = txt_item.getText().toString();
                        item =item.trim();
                        txt_item.setText(item);
                }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.76:5000/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<List<Datamodels_Item>> call2 = retrofitAPI.GetPicture(txt_item.getText().toString());
        call2.enqueue(new Callback<List<Datamodels_Item>>() {
            @Override
            public void onResponse(Call<List<Datamodels_Item>> call, Response<List<Datamodels_Item>> response) {
                if (response.isSuccessful()) {
                    List<Datamodels_Item> res = response.body();
                    if (res != null && !res.isEmpty()) {
                        Datamodels_Item item = res.get(0);
                        String encodedImage = item.getPicture();
                        if (encodedImage != null && !encodedImage.isEmpty()) {
                            byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                            picture.setImageBitmap(decodedBitmap);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Datamodels_Item>> call, Throwable t) {
                resultwip.setText(t.getMessage());
                Toast.makeText(WipActivity.this, "error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postData (String Job, String Item, String Quantity, String RecipientName){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.76:5000/")
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Datamodels_Wip dataWip = new Datamodels_Wip(Job, Item, Quantity, RecipientName );
        Call<Datamodels_Wip> call = retrofitAPI.PostDataWip(dataWip);
        call.enqueue(new Callback<Datamodels_Wip>() {
            @Override
            public void onResponse(Call<Datamodels_Wip> call, Response<Datamodels_Wip> response) {
                Toast.makeText(WipActivity.this, "Save data success", Toast.LENGTH_SHORT).show();
                txt_job.setText("");
                txt_item.setText("");
                txt_qty.setText("");
            }

            @Override
            public void onFailure(Call<Datamodels_Wip> call, Throwable t) {
                Toast.makeText(WipActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}