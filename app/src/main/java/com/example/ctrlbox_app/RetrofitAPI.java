package com.example.ctrlbox_app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPI {

        @GET ("CtrlBox_In/{id}")
        Call<Datamodels>getDataById(@Path("id") Datamodels BoxId);

}
