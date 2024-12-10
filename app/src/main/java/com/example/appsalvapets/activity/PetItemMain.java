package com.example.appsalvapets.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsalvapets.R;
import com.example.appsalvapets.model.Pet;

import java.util.List;

public class PetItemMain extends RecyclerView.Adapter<PetItemMain.PetViewHolder> {

    private List<Pet> pets;
    private Context context;

    public PetItemMain(List<Pet> pets, Context context) {
        this.pets = pets;
        this.context = context;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item_main, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = pets.get(position);

        // Exibir informações do pet
        holder.textViewNome.setText(pet.getNome());
        holder.textViewRaca.setText(pet.getRaca());
        holder.textViewIdade.setText(String.format("%d anos", pet.getIdade()));

        // Decodificar e exibir imagem do pet
        if (pet.getImagemBase64() != null) {
            byte[] decodedString = Base64.decode(pet.getImagemBase64(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imageViewPet.setImageBitmap(decodedByte);
        }

        // Configurar clique no botão "Quero Adotar"
        holder.btnAdotar.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetalhesPetActivity.class); // Nome corrigido
            intent.putExtra("pet", pet); // Passar o objeto Pet para a nova Activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewRaca, textViewIdade;
        ImageView imageViewPet;
        Button btnAdotar; // Declaração do botão

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewPetNome);
            textViewRaca = itemView.findViewById(R.id.textViewPetRaca);
            textViewIdade = itemView.findViewById(R.id.textViewPetIdade);
            imageViewPet = itemView.findViewById(R.id.imageViewPet);
            btnAdotar = itemView.findViewById(R.id.btnAdotar); // Vinculação do botão
        }
    }
}
