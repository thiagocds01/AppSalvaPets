package com.example.appsalvapets.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsalvapets.R;
import com.example.appsalvapets.model.Pet;
import com.example.appsalvapets.service.PetService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetAdapter adapter;
    private List<Pet> petList = new ArrayList<>();
    private PetService petService;

    public static void startPetListActivity(Context context) {
        Intent intent = new Intent(context, PetListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.74:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        petService = retrofit.create(PetService.class);

        // Configurar Adapter
        adapter = new PetAdapter(this, petList, new PetAdapter.OnPetActionListener() {
            @Override
            public void onEdit(Pet pet) {
                PetActivity.startPetActivity(PetListActivity.this, pet.getId());
            }

            @Override
            public void onDelete(Pet pet) {
                deletePet(pet);
            }
        });

        recyclerView.setAdapter(adapter);

        // Configurar Botão para Adicionar Pet
        Button btnAddPet = findViewById(R.id.btnAddPet);
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetActivity.startPetActivity(PetListActivity.this); // Chamar a Activity de criação
            }
        });

        // Carregar Pets
        loadPets();
    }

    private void loadPets() {
        petService.getAllPets().enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    petList.clear();
                    petList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                // Tratar falha
            }
        });
    }

    private void deletePet(Pet pet) {
        petService.deletePet(pet.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    petList.remove(pet);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Tratar falha
            }
        });
    }
}
