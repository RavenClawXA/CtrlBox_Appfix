package com.example.ctrlbox_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {

       /* @PUT("BoxCtrl/update/{id}")
        Call<List<Datamodels>> getPut(@Path("id") String BoxId, @Body Datamodels datamodels);
        @GET("BoxCtrl")
        Call<List<Datamodels>> getAllBC();
        @GET("BoxCtrl/{id}")
        Call<List<Datamodels>> getPosts(@Path("id") String BoxId);

        @GET("TransBox/get")
        Call<List<Datamodels>>getAllTB();

        @POST("TransBox/add")
        Call<Datamodels> addtoTB(@Body Datamodels datamodels);

        @DELETE("BoxCtrl/delete/{id}")
        Call<Datamodels_addBoxCtrl> del_BC(@Path("id") String BoxId);

        @POST("Logbox/add")
        Call<Datamodels> addHistory(@Body Datamodels datamodels);

        @POST("add")
        Call<Datamodels> addtoBC(@Body Datamodels datamodels);

        @DELETE("TransBox/delete/{id}")
        Call<Datamodels_addBoxCtrl> del_TB(@Path("id") String BoxId);  */

        //-----------BoxTrans---------------//
        @GET("BoxTrans/get")
        Call<List<Datamodels>> getAllBoxTrans();

        @PUT("BoxTrans/update/{id}")
        Call<Datamodels> updateBoxTrans(@Path("id") String BoxId, @Body Datamodels datamodels);

        @POST("BoxTrans/add")
        Call<Datamodels> addBoxTrans(@Body Datamodels datamodels);

        //-----------BoxCtrl--------------------//
        @GET("BoxCtrl")
        Call<List<Datamodels_BoxCtrl>> getBoxCtrl ();
        @POST("BoxCtrl/add")
        Call<Datamodels_BoxCtrl> addBoxCtrl(@Body Datamodels_BoxCtrl datamodels_boxCtrl);

        //-----------LogBox-----------------//
        @POST("LogBox/add")
        Call<Datamodels> addLogBox(@Body Datamodels datamodels);
}
