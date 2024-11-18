package com.example.appsalvapets;

import com.example.appsalvapets.Denuncia;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceDenuncia {
    @POST("api/denuncias")
    Call<Denuncia> postDenuncia(@Body Denuncia denuncia);
}
