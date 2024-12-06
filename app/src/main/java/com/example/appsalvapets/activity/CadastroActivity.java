package com.example.appsalvapets.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsalvapets.R;
import com.example.appsalvapets.model.Ong;
import com.example.appsalvapets.model.TipoUsuario;
import com.example.appsalvapets.model.Usuario;
import com.example.appsalvapets.service.ServiceCadastro;
import com.example.appsalvapets.service.ServiceOng;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static void startCadastroActivity(Context context) {
        Intent intent = new Intent(context, CadastroActivity.class);
        context.startActivity(intent);
    }

    private EditText edtCadUsuario, edtCadSenha;
    private Spinner spnTipoUsuario, spnSeleOng;
    private Button btnCadUsuario, btnCadCancelar;
    private Retrofit retrofit;
    private Context context;
    private Usuario usuario;
    private ServiceCadastro serviceCadastro;
    private TextView txtSeleOng;
    ImageButton btnVoltar, btnDenuncia, btnPet;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtSeleOng = findViewById(R.id.txtSeleOng);
        edtCadUsuario = findViewById(R.id.edtCadUsuario);
        edtCadSenha = findViewById(R.id.edtCadSenha);
        spnTipoUsuario = findViewById(R.id.spnTipoUsuario);
        spnSeleOng = findViewById(R.id.spnSeleOng);
        btnCadUsuario = findViewById(R.id.btnCadUsuario);
        btnCadUsuario.setOnClickListener(this);
        btnCadCancelar = findViewById(R.id.btnCadCancelar);
        btnCadCancelar.setOnClickListener(this);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnDenuncia = findViewById(R.id.btnDenuncia);
        btnVoltar.setOnClickListener(this);
        btnDenuncia.setOnClickListener(this);




        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceCadastro = retrofit.create(ServiceCadastro.class);

        spnTipoUsuario.setOnItemSelectedListener(this);
        ArrayAdapter<TipoUsuario> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, TipoUsuario.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoUsuario.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCadUsuario) {
            registrarUsuario();
        }else if (v.getId() == R.id.btnCadCancelar) {
            finish();

        }
        else if (v.getId() == R.id.btnVoltar) {
            Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (v.getId() == R.id.btnDenuncia) {
            DenunciaActivity.startDenunciaActivity(this);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       TipoUsuario selecionado = (TipoUsuario) parent.getItemAtPosition(position);

        if(selecionado == TipoUsuario.ONG){
            spnSeleOng.setVisibility(View.VISIBLE);
            txtSeleOng.setVisibility(View.VISIBLE);
            carregarOngs();
        }else if (selecionado == TipoUsuario.PESSOA_FISICA) {
            spnSeleOng.setVisibility(View.GONE);
            txtSeleOng.setVisibility(View.GONE);
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void carregarOngs() {
        serviceCadastro.getAllOngs().enqueue(new Callback<List<Ong>>() {
            @Override
            public void onResponse(Call<List<Ong>> call, Response<List<Ong>> response) {
                if (response.isSuccessful() && response.body() != null){
            ArrayAdapter<Ong> adapter = new ArrayAdapter<>(CadastroActivity.this,
                    android.R.layout.simple_spinner_item, response.body());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnSeleOng.setAdapter(adapter);
                Toast.makeText(CadastroActivity.this, "Sucesso ao carregar ONGs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ong>> call, Throwable t) {
                Toast.makeText(CadastroActivity.this, "Erro ao carregar ONGs", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void registrarUsuario() {
        String username = edtCadUsuario.getText().toString();
        String password = edtCadSenha.getText().toString();
        TipoUsuario tipoUsuario = (TipoUsuario) spnTipoUsuario.getSelectedItem();
        String ong = spnSeleOng.getVisibility() == View.VISIBLE && spnSeleOng.getSelectedItem() != null
                ? spnSeleOng.getSelectedItem().toString() :null;

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }
        Usuario usuario = new Usuario(username, password, tipoUsuario.name(), ong);
        serviceCadastro.createUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(CadastroActivity.this, "Falha na comunicação com o servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }

}