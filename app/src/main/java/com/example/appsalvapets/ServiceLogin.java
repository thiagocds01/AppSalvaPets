package com.example.appsalvapets;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceLogin {

    @Headers("Accept: application/json")
    @POST("api/usuario/login")
    Call<UsuarioLogin> realizarLogin(@Body UsuarioLogin usuario);


    @GET("api/usuario/login")
    Call<UsuarioLogin> realizarLogin(@Query("username") String username, @Query("password") String password);
}
