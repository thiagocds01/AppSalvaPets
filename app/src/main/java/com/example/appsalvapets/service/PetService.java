package com.example.appsalvapets.service;

import com.example.appsalvapets.model.Pet;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface PetService {

    @GET("api/pets")
    Call<List<Pet>> getPets();

    @GET("api/pets/{id}")
    Call<Pet> getPetById(@Path("id") long id);

    @POST("api/pets")
    Call<Pet> createPet(@Body Pet pet);

    @PUT("api/pets/{id}")
    Call<Pet> updatePet(@Path("id") long id, @Body Pet pet);

    @DELETE("api/pets/{id}")
    Call<Void> deletePet(@Path("id") long id);
}
