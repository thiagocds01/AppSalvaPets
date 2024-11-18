package com.example.appsalvapets;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Denuncia implements Serializable {

    @SerializedName("id")
    private long id; // Identificador único da denúncia

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("assunto")
    private String assunto; // Assunto da denúncia

    @SerializedName("relato")
    private String relato; // Relato detalhado sobre a denúncia

    @SerializedName("latitude")
    private double latitude; // Coordenada de latitude

    @SerializedName("longitude")
    private double longitude; // Coordenada de longitude

    @SerializedName("imagem")
    private String imagemDenuncia; // Imagem codificada em Base64

    // Construtor padrão
    public Denuncia() {
    }

    // Construtor com argumentos
    public Denuncia(String tipo, String assunto, String relato, double latitude, double longitude, String imagemDenuncia) {
        this.tipo = tipo;
        this.assunto = assunto;
        this.relato = relato;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagemDenuncia = imagemDenuncia;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getRelato() {
        return relato;
    }

    public void setRelato(String relato) {
        this.relato = relato;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImagemDenuncia() {
        return imagemDenuncia;
    }

    public void setImagemDenuncia(String imagemDenuncia) {
        this.imagemDenuncia = imagemDenuncia;
    }

    @Override
    public String toString() {
        return "Denuncia{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", assunto='" + assunto + '\'' +
                ", relato='" + relato + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", imagemDenuncia='" + imagemDenuncia + '\'' +
                '}';
    }
}
