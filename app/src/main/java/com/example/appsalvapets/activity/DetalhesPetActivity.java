package com.example.appsalvapets.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appsalvapets.R;
import com.example.appsalvapets.model.Pet;

public class DetalhesPetActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewNome, textViewRaca, textViewIdade, textViewPorte, textViewSexo, textViewCor, textViewHistoria;
    private ImageView imageViewPet;
    ImageButton btnDenuncia, btnLogin,btnAtualizar, btnPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pet);

        // Vincular os elementos do layout
        textViewNome = findViewById(R.id.textViewDetalhesNome);
        textViewRaca = findViewById(R.id.textViewDetalhesRaca);
        textViewIdade = findViewById(R.id.textViewDetalhesIdade);
        textViewPorte = findViewById(R.id.tv_porte_raca);
        textViewSexo = findViewById(R.id.tv_sexo);
        textViewCor = findViewById(R.id.tv_cor);
        textViewHistoria = findViewById(R.id.tv_historia);
        imageViewPet = findViewById(R.id.imageViewDetalhesPet);
        btnDenuncia = findViewById(R.id.btnDenuncia);
        btnDenuncia.setOnClickListener(this);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnPet = findViewById(R.id.btnPet);
        btnPet.setOnClickListener(this);

        // Receber o objeto Pet da Intent
        Pet pet = (Pet) getIntent().getSerializableExtra("pet");

        // Preencher os campos com os dados do Pet
        if (pet != null) {
            textViewNome.setText(pet.getNome());
            textViewRaca.setText(pet.getRaca());
            textViewIdade.setText(String.format("%d anos", pet.getIdade()));
            textViewPorte.setText(pet.getPorteRaca());
            textViewSexo.setText(pet.getSexo());
            textViewCor.setText(pet.getCor());
            textViewHistoria.setText(pet.getHistoria());

            // Decodificar e exibir a imagem do Pet, se disponível
            if (pet.getImagemBase64() != null) {
                byte[] decodedString = Base64.decode(pet.getImagemBase64(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageViewPet.setImageBitmap(decodedByte);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDenuncia) {
            DenunciaActivity.startDenunciaActivity(this);
        }else if (v.getId() == R.id.btnAtualizar) {
            MainActivity.startMainActivity(this);
            Toast.makeText(this, "Atualizando lista de pets...", Toast.LENGTH_SHORT).show();
        }
        else if  (v.getId() == R.id.btnLogin) {
            LoginActivity.startLoginActivity(this);

        }else if (v.getId() == R.id.btnPet) {
            PetListActivity.startPetListActivity(this);
        }
    }
}