package com.example.ctrlbox_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPI {

        @GET("CtrlBox_In/{id}")
        Call<List<Datamodels>> getPosts(@Path("id") String BoxId);


}
