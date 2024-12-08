package com.example.appsalvapets.activity;

import android.content.Context;
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

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private Context context;
    private List<Pet> petList;
    private OnPetActionListener listener;

    public PetAdapter(Context context, List<Pet> petList, OnPetActionListener listener) {
        this.context = context;
        this.petList = petList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.tvNome.setText(pet.getNome());
        holder.tvRaca.setText(pet.getRaca());
        holder.tvIdade.setText("Idade: " + pet.getIdade() + " anos");

        if (pet.getImagemBase64() != null) {
            byte[] imageBytes = Base64.decode(pet.getImagemBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.ivImage.setImageBitmap(bitmap);
        }

        holder.btnEditar.setOnClickListener(v -> listener.onEdit(pet));
        holder.btnDeletar.setOnClickListener(v -> listener.onDelete(pet));
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvNome, tvRaca, tvIdade;
        Button btnEditar, btnDeletar;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivPetImage);
            tvNome = itemView.findViewById(R.id.tvPetNome);
            tvRaca = itemView.findViewById(R.id.tvPetRaca);
            tvIdade = itemView.findViewById(R.id.tvPetIdade);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnDeletar = itemView.findViewById(R.id.btnDeletar);
        }
    }

    public interface OnPetActionListener {
        void onEdit(Pet pet);
        void onDelete(Pet pet);
    }
}
