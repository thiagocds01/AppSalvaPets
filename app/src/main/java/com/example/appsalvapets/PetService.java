package com.example.appsalvapets;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PetService {
    @GET("api/pets")
    Call<List<Pet>> getPets();
}
