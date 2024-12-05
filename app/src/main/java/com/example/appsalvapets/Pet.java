package com.example.appsalvapets;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Pet {
    @SerializedName ("id")
    private Long id;

    @SerializedName ("nome")
    private String nome;

    @SerializedName ("raca")
    private String raca;

    @SerializedName ("porteRaca")
    private String porteRaca;

    @SerializedName ("sexo")
    private String sexo;

    @SerializedName ("cor")
    private String cor;

    @SerializedName ("idade")
    private int idade;

    @SerializedName ("historia")
    private String historia;

    @SerializedName("imagem")
    private String imagemBase64; // Base64 da imagem em formato de byte array

    // Construtor vazio (necessário para Retrofit e bibliotecas de mapeamento JSON)
    public Pet() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPorteRaca() {
        return porteRaca;
    }

    public void setPorteRaca(String porteRaca) {
        this.porteRaca = porteRaca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getImagemBase64() { return imagemBase64; }
    public void setImagemBase64(String imagemBase64) { this.imagemBase64 = imagemBase64; }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", raca='" + raca + '\'' +
                ", porteRaca='" + porteRaca + '\'' +
                ", sexo='" + sexo + '\'' +
                ", cor='" + cor + '\'' +
                ", idade=" + idade +
                ", historia='" + historia + '\'' +
                ", imagemBase64='" + imagemBase64 + '\'' +
                '}';
    }
}
