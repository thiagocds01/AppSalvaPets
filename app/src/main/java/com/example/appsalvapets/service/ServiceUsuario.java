package com.example.appsalvapets.service;

import com.example.appsalvapets.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceUsuario {

    @Headers("Accept: application/json")
    @GET("api/usuario")
    Call<List<Usuario>> getAllUsuario();

    @Headers("Accept: application/json")
    @GET("api/usuario/{id}")
    Call<Usuario> getUsuarioById(@Path("id") long id);

    @Headers("Accept: application/json")
    @POST("api/usuario")
    Call<Usuario> createUsuario(@Body Usuario usuario);

    @Headers("Accept: application/json")
    @PUT("api/usuario/{id}")
    Call<Usuario> updateUsuario(@Path("id") long id, @Body Usuario usuario);

    @Headers("Accept: application/json")
    @DELETE("api/usuario/{id}")
    Call<Usuario> deleteUsuario(@Path("id") long id);

    @Headers("Accept: application/json")
    @POST("api/usuario")
    Call<Usuario> postUsuario(@Body String usuario);

    @Headers("Accept: application/json")
    @GET("api/usuario/login")
    Call<Usuario> relizarLogin(@Body Usuario usuario);
}

