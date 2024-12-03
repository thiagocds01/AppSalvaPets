package com.example.appsalvapets;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pet {
    @SerializedName("id")
    private long id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("raca")
    private String raca;

    @SerializedName("idade")
    private int idade;

    @SerializedName("imagem")
    private String imagemBase64;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getImagemBase64() { return imagemBase64; }
    public void setImagemBase64(String imagemBase64) { this.imagemBase64 = imagemBase64; }
}

