package com.example.appsalvapets.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsalvapets.R;
import com.example.appsalvapets.model.Pet;
import com.example.appsalvapets.service.PetService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;

    private EditText etNome, etRaca, etPorteRaca, etSexo, etCor, etIdade, etHistoria;
    private ImageView ivPetImage;
    private Button btnVoltar, btnEditar, btnSalvar, btnCarregarFoto, btnDeletar;

    private PetService petService;
    private Pet currentPet;
    private String fotoBase64;
    private boolean isEditMode = false;

    public static void startPetActivity(Context context, long petId) {
        Intent intent = new Intent(context, PetActivity.class);
        intent.putExtra("PET_ID", petId);
        context.startActivity(intent);
    }

    public static void startPetActivity(Context context) {
        Intent intent = new Intent(context, PetActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_form);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.74:80/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        petService = retrofit.create(PetService.class);
        PetActivity context = this;


        // Vincular componentes do layout
        etNome = findViewById(R.id.et_nome);
        etRaca = findViewById(R.id.et_raca);
        etPorteRaca = findViewById(R.id.et_porte_raca);
        etSexo = findViewById(R.id.et_sexo);
        etCor = findViewById(R.id.et_cor);
        etIdade = findViewById(R.id.et_idade);
        etHistoria = findViewById(R.id.et_historia);
        ivPetImage = findViewById(R.id.iv_pet_image);
        btnVoltar = findViewById(R.id.btn_voltar);
        btnEditar = findViewById(R.id.btn_editar);
        btnSalvar = findViewById(R.id.btn_salvar);
        btnCarregarFoto = findViewById(R.id.btn_carregar_foto);
        btnDeletar = findViewById(R.id.btn_deletar);

        // Configurar ações dos botões
        btnVoltar.setOnClickListener(view -> finish());
        btnEditar.setOnClickListener(view -> enableFields(true));
        btnSalvar.setOnClickListener(view -> {
            if (isEditMode) {
                updatePet();
            } else {
                createPet();
            }
        });
        btnCarregarFoto.setOnClickListener(view -> abrirGaleria());
        btnDeletar.setOnClickListener(view -> {
            if (currentPet != null) {
                deletePet(currentPet.getId());
            }
        });

        // Verificar se estamos em modo de edição
        long petId = getIntent().getLongExtra("PET_ID", -1);
        if (petId != -1) {
            loadPetDetails(petId);
        } else {
            enableFields(true);
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
                ivPetImage.setImageBitmap(bitmap);
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

    private void enableFields(boolean enable) {
        etNome.setEnabled(enable);
        etRaca.setEnabled(enable);
        etPorteRaca.setEnabled(enable);
        etSexo.setEnabled(enable);
        etCor.setEnabled(enable);
        etIdade.setEnabled(enable);
        etHistoria.setEnabled(enable);
    }

    private void loadPetDetails(long petId) {
        petService.getPetById(petId).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentPet = response.body();
                    populateFields(currentPet);
                    enableFields(false);
                    isEditMode = true;
                } else {
                    Toast.makeText(PetActivity.this, "Erro ao carregar pet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(PetActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateFields(Pet pet) {
        etNome.setText(pet.getNome());
        etRaca.setText(pet.getRaca());
        etPorteRaca.setText(pet.getPorteRaca());
        etSexo.setText(pet.getSexo());
        etCor.setText(pet.getCor());
        etIdade.setText(String.valueOf(pet.getIdade()));
        etHistoria.setText(pet.getHistoria());

        if (pet.getImagemBase64() != null) {
            byte[] imageBytes = Base64.decode(pet.getImagemBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            ivPetImage.setImageBitmap(bitmap);
        }
    }

    private void createPet() {
        Pet pet = new Pet();
        pet.setNome(etNome.getText().toString());
        pet.setRaca(etRaca.getText().toString());
        pet.setPorteRaca(etPorteRaca.getText().toString());
        pet.setSexo(etSexo.getText().toString());
        pet.setCor(etCor.getText().toString());
        pet.setIdade(Integer.parseInt(etIdade.getText().toString()));
        pet.setHistoria(etHistoria.getText().toString());
        pet.setImagemBase64(fotoBase64); // Certifique-se de que a imagem foi carregada

        petService.createPet(pet).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PetActivity.this, "Pet criado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PetActivity.this, "Erro ao criar pet: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(PetActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updatePet() {
        if (currentPet == null) return;

        // Atualizar os campos
        currentPet.setNome(etNome.getText().toString());
        currentPet.setRaca(etRaca.getText().toString());
        currentPet.setPorteRaca(etPorteRaca.getText().toString());
        currentPet.setSexo(etSexo.getText().toString());
        currentPet.setCor(etCor.getText().toString());
        currentPet.setIdade(Integer.parseInt(etIdade.getText().toString()));
        currentPet.setHistoria(etHistoria.getText().toString());

        // Verificar se uma nova foto foi carregada
        if (fotoBase64 != null && !fotoBase64.isEmpty()) {
            currentPet.setImagemBase64(fotoBase64); // Atualizar com a nova imagem
        }

        // Enviar a requisição para atualizar o pet
        petService.updatePet(currentPet.getId(), currentPet).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PetActivity.this, "Pet atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PetActivity.this, "Erro ao atualizar pet: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(PetActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void deletePet(long petId) {
        petService.deletePet(petId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PetActivity.this, "Pet excluído com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PetActivity.this, "Erro ao excluir pet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(PetActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
