package com.example.ctrlbox_app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {

        @GET("CtrlBox_In/{id}")
        Call<Datamodels> getDataById(@Query("id") int BoxId, @Query("fields") String fields);


}
