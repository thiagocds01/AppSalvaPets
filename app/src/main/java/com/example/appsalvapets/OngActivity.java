package com.example.appsalvapets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OngActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextNomeOng;
    EditText editTextEndOng;
    EditText editTextContatOng;
    Context context;

    Button btnGravarOng;
    Button btnVoltarOng;

    private Retrofit retrofit;
    ServiceOng service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNomeOng = findViewById(R.id.editTextNomeOng);
        editTextEndOng = findViewById(R.id.editTextEndOng); // Corrigido ID
        editTextContatOng = findViewById(R.id.editTextContatOng);
        btnGravarOng = findViewById(R.id.btnGravarOng);
        btnVoltarOng = findViewById(R.id.btnVoltarOng);

        btnGravarOng.setOnClickListener(this);
        btnVoltarOng.setOnClickListener(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.102:8089/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ServiceOng.class);
        context = this;
    }

    @Override
    public void onClick(View v) {
        if (v == btnGravarOng) {
            Ong ong = new Ong(
                    editTextNomeOng.getText().toString(),
                    editTextEndOng.getText().toString(),
                    editTextContatOng.getText().toString()
            );

            Call<Ong> call = service.postOng(ong);
            call.enqueue(new Callback<Ong>() {
                @Override
                public void onResponse(Call<Ong> call, Response<Ong> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Ong ongSalva = response.body();
                        String str = "Id: " + ongSalva.getId() + "  Nome: " + ongSalva.getNome();
                        Toast.makeText(context, "ONG inserida com sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("ERRO", response.toString());
                        Toast.makeText(context, "Falha ao inserir ONG", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Ong> call, Throwable t) {

                }
            });
        } else if (v == btnVoltarOng) {
            finish();
        }
    }
}