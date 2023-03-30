package com.example.ctrlbox_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface RetrofitAPI {

        @GET ("get")
        Call<Datamodels>createPost(@Body Datamodels datamodels);

}
