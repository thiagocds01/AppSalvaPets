package com.example.appsalvapets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ServiceOng {


    @Headers("Accept: application/json")
    @GET("api/ongs")
    Call<List<Ong>> getAllOngs();

    @Headers("Accept: application/json")
    @GET("api/ongs/{id}")
    Call<Ong> getOngById(@Path("id") long id);

    @Headers("Accept: application/json")
    @POST("api/ongs")
    Call<Ong> createOng(@Body Ong ong);

    @Headers("Accept: application/json")
    @PUT("api/ongs/{id}")
    Call<Ong> updateOng(@Path("id") long id, @Body Ong ong);

    @Headers("Accept: application/json")
    @DELETE("api/ongs/{id}")
    Call<Void> deleteOng(@Path("id") long id);

    @Headers("Accept: application/json")
    @POST("api/ongs")
    Call<Ong> postOng(@Body Ong ong);
}