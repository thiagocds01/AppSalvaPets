package com.example.appsalvapets.model;



public class Usuario {



    private long id;
    private String username;
    private String password;
    private String tipoUsuario;
    private Long ong;


    public Usuario(){

    }

    public Usuario(String usuario, String senha, String tipoUsuario, Long Ong){
        this.username = usuario;
        this.password = senha;
        this.tipoUsuario = tipoUsuario;
        this.ong = Ong;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Long getOng() {
        return ong;
    }

    public void setOng(Long ong) {
        this.ong = ong;
    }



}

