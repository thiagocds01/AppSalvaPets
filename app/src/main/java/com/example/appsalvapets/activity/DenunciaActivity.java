package com.example.appsalvapets.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appsalvapets.R;
import com.example.appsalvapets.model.Denuncia;
import com.example.appsalvapets.service.ServiceDenuncia;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DenunciaActivity extends AppCompatActivity implements View.OnClickListener {

    public static void startDenunciaActivity(Context context) {
        Intent intent = new Intent(context, DenunciaActivity.class);
        context.startActivity(intent);
    }


    private static final int REQUEST_IMAGE_PICK = 1;
    private EditText editTextTextAssuntoDenuncia, editTextRelatoDenuncia;
    private TextView textViewLatitude, textViewLongitude;
    private ImageView imageViewFoto;
    private CheckBox checkBoxAbandono, checkBoxMausTratos, checkBoxOutros;
    private Button btnEnviarDenuncia, btnCarregarFoto;
    ImageButton btnObterLocalizacao, btnVoltar, btnLogin, btnPet;
    private Retrofit retrofit;
    private ServiceDenuncia service;
    private Context context;
    private String fotoBase64;
    private FusedLocationProviderClient fusedLocationProviderClient;



    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_denuncia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        editTextTextAssuntoDenuncia = findViewById(R.id.editTextTextAssuntoDenuncia);
        editTextRelatoDenuncia = findViewById(R.id.editTextRelatoDenuncia);
        textViewLatitude = findViewById(R.id.TextViewLatitude);
        textViewLongitude = findViewById(R.id.TextViewLongitude);
        imageViewFoto = findViewById(R.id.imageViewFotoDenuncia);
        checkBoxAbandono = findViewById(R.id.checkBoxAbandono);
        checkBoxMausTratos = findViewById(R.id.checkBoxMausTratos);
        checkBoxOutros = findViewById(R.id.checkBoxOutros);
        btnEnviarDenuncia = findViewById(R.id.btnEnviarDenuncia);
        btnObterLocalizacao = findViewById(R.id.btnObterLocalizacao);
        btnCarregarFoto = findViewById(R.id.btnCarregarFoto);

        btnEnviarDenuncia.setOnClickListener(this);
        btnObterLocalizacao.setOnClickListener(this);
        btnCarregarFoto.setOnClickListener(this);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnLogin = findViewById(R.id.btnLogin);


        btnVoltar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);



        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.102:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ServiceDenuncia.class);
        context = this;


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnEnviarDenuncia) {
            enviarDenuncia();
        } else if (v == btnObterLocalizacao) {
            obterLocalizacao();
        } else if (v == btnCarregarFoto) {
            abrirGaleria();
        }
        else if (v == btnVoltar) {
            MainActivity.startMainActivity(this);
        }
        else if (v == btnLogin) {
            LoginActivity.startLoginActivity(this);
        }

    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                fotoBase64 = converterParaBase64(bitmap);


                imageViewFoto.setImageBitmap(bitmap);

                Toast.makeText(this, "Foto carregada com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Erro ao carregar a foto.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String converterParaBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void obterLocalizacao() {
        // Verificar permissões
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // Intervalo em milissegundos
        locationRequest.setFastestInterval(5000); // Intervalo mais rápido

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Location location = locationResult.getLastLocation();
                    textViewLatitude.setText("Latitude: " + location.getLatitude());
                    textViewLongitude.setText("Longitude: " + location.getLongitude());

                    // Parar as atualizações após obter a localização
                    fusedLocationProviderClient.removeLocationUpdates(this);
                    Toast.makeText(context, "Localização obtida com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Erro ao obter a localização.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Iniciar as atualizações de localização
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    private void enviarDenuncia() {
        String assunto = editTextTextAssuntoDenuncia.getText().toString().trim();
        String relato = editTextRelatoDenuncia.getText().toString().trim();
        String latitudeStr = textViewLatitude.getText().toString().replace("Latitude: ", "").trim();
        String longitudeStr = textViewLongitude.getText().toString().replace("Longitude: ", "").trim();
        boolean abandono = checkBoxAbandono.isChecked();
        boolean mausTratos = checkBoxMausTratos.isChecked();
        boolean outros = checkBoxOutros.isChecked();

        String tipo;
        if (abandono) {
            tipo = "Abandono";
        } else if (mausTratos) {
            tipo = "Maus-Tratos";
        } else if (outros) {
            tipo = "Outros";
        } else {
            Toast.makeText(context, "Selecione pelo menos um tipo de denúncia.", Toast.LENGTH_SHORT).show();
            return;
        }

        Denuncia denuncia = new Denuncia();
        denuncia.setTipo(tipo);
        denuncia.setAssunto(assunto);
        denuncia.setRelato(relato);
        denuncia.setImagemDenuncia(fotoBase64);

        try {
            denuncia.setLatitude(Double.parseDouble(latitudeStr));
        } catch (NumberFormatException e) {
            denuncia.setLatitude(0.0);
        }

        try {
            denuncia.setLongitude(Double.parseDouble(longitudeStr));
        } catch (NumberFormatException e) {
            denuncia.setLongitude(0.0);
        }

        Call<Denuncia> call = service.postDenuncia(denuncia);
        call.enqueue(new Callback<Denuncia>() {
            @Override
            public void onResponse(Call<Denuncia> call, Response<Denuncia> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, "Denúncia enviada com sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } else {
                    Toast.makeText(context, "Erro ao enviar denúncia.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Denuncia> call, Throwable t) {
                Toast.makeText(context, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limparCampos() {
        editTextTextAssuntoDenuncia.setText("");
        editTextRelatoDenuncia.setText("");
        textViewLatitude.setText("Latitude: --");
        textViewLongitude.setText("Longitude: --");
        checkBoxAbandono.setChecked(false);
        checkBoxMausTratos.setChecked(false);
        checkBoxOutros.setChecked(false);
        imageViewFoto.setImageResource(android.R.color.transparent); // Remove a imagem exibida
        fotoBase64 = null;
    }
}
