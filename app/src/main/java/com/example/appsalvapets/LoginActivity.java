package com.example.appsalvapets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener {

    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }


    EditText edtUsuario, edtSenha;
    Button btnEntrar;
    private Retrofit retrofit;
    ServiceLogin serviceUsuario;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(this);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.74:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceUsuario = retrofit.create(ServiceLogin.class);
        context = this;

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnEntrar) {
            realizarLogin();
        }

    }

    private void realizarLogin() {
        String usuario = edtUsuario.getText().toString();
        String senha = edtSenha.getText().toString();

        if (usuario.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioLogin user = new UsuarioLogin(usuario, senha);

        Call<UsuarioLogin> call = serviceUsuario.realizarLogin(user);
        call.enqueue(new Callback<UsuarioLogin>() {
            @Override
            public void onResponse(Call<UsuarioLogin> call, Response<UsuarioLogin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UsuarioLogin usuarioLogado = response.body();
                    Toast.makeText(LoginActivity.this, "Login bem-sucedido! Bem-vindo, " + usuarioLogado.getUsername(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, DenunciaActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(LoginActivity.this, "Erro no login: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Erro ao processar a resposta.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UsuarioLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erro na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
