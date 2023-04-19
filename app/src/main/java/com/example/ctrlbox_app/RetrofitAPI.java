package com.example.ctrlbox_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {

        @PUT("BoxCtrl/update/{id}")
        Call<Datamodels> getPut(@Path("id") String BoxId, @Body Datamodels datamodels);
        @GET("BoxCtrl")
        Call<List<Datamodels>> getAllBC();
        @GET("BoxCtrl/{id}")
        Call<List<Datamodels>> getPosts(@Path("id") String BoxId);

        @GET("TransBox/get")
        Call<List<Datamodels>>getAllTB();
}
