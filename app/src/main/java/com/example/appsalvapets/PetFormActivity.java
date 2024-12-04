package com.example.appsalvapets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PetFormActivity extends AppCompatActivity {

    private EditText etNome, etRaca, etPorteRaca, etSexo, etCor, etIdade, etHistoria;
    private ImageView ivPetImage;
    private Button btnVoltar, btnEditar, btnSalvar;

    private boolean isEditMode = false; // Controla se estamos editando ou criando um pet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_form);

        // Vinculando os componentes da interface
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

        // Configurando ações dos botões
        btnVoltar.setOnClickListener(view -> finish());

        btnEditar.setOnClickListener(view -> {
            isEditMode = true;
            enableFields(true);
        });

        btnSalvar.setOnClickListener(view -> {
            if (isEditMode) {
                updatePet();
            } else {
                createPet();
            }
        });

        // Inicialmente, desabilitar os campos
        enableFields(false);
    }

    // Habilita ou desabilita os campos de texto
    private void enableFields(boolean enable) {
        etNome.setEnabled(enable);
        etRaca.setEnabled(enable);
        etPorteRaca.setEnabled(enable);
        etSexo.setEnabled(enable);
        etCor.setEnabled(enable);
        etIdade.setEnabled(enable);
        etHistoria.setEnabled(enable);
    }

    // Metodo para criar um novo pet
    private void createPet() {
        String nome = etNome.getText().toString();
        String raca = etRaca.getText().toString();
        String porteRaca = etPorteRaca.getText().toString();
        String sexo = etSexo.getText().toString();
        String cor = etCor.getText().toString();
        int idade = Integer.parseInt(etIdade.getText().toString());
        String historia = etHistoria.getText().toString();

        // Exibir uma mensagem (em vez de integração com API por enquanto)
        Toast.makeText(this, "Pet criado: " + nome, Toast.LENGTH_SHORT).show();

        // Limpar os campos após salvar
        clearFields();
    }

    // Metodo para atualizar um pet existente
    private void updatePet() {
        String nome = etNome.getText().toString();
        // Aqui você adicionaria a lógica de atualizar o pet na API REST
        Toast.makeText(this, "Pet atualizado: " + nome, Toast.LENGTH_SHORT).show();
    }

    // Limpa os campos do formulário
    private void clearFields() {
        etNome.setText("");
        etRaca.setText("");
        etPorteRaca.setText("");
        etSexo.setText("");
        etCor.setText("");
        etIdade.setText("");
        etHistoria.setText("");
    }
}
