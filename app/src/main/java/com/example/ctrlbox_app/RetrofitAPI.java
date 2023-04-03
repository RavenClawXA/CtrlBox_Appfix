package com.example.ctrlbox_app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

        @GET ("Ctrlbox_In/{id}")
        Call<Datamodels>getDataById(@Path("id") int BoxId, @Query("fields") String fields);

}
