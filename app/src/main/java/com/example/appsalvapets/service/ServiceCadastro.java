package com.example.appsalvapets.service;

import com.example.appsalvapets.model.Ong;
import com.example.appsalvapets.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ServiceCadastro {

    @Headers("Accept: application/json")
    @POST("api/usuario")
    Call<Usuario> createUsuario(@Body Usuario usuario);

    @Headers("Accept: application/json")
    @GET("api/ongs")
    Call<List<Ong>> getAllOngs();
}
