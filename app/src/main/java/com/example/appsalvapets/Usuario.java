package com.example.appsalvapets;



public class Usuario {



    private long id;

    private String username;

    private String password;

    private TipoUsuario tipoUsuario;

    private Ong ong;



    public Usuario(){



    }
    public Usuario(String usuario, String senha) {
        this.username = usuario;
        this.password = senha;
    }



    public Usuario(String usuario, String senha, TipoUsuario tipoUsuario){

        this.username = usuario;

        this.password = senha;

        this.tipoUsuario = tipoUsuario;

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



    public TipoUsuario getTipoUsuario() {

        return tipoUsuario;

    }



    public void setTipoUsuario(TipoUsuario tipoUsuario) {

        this.tipoUsuario = tipoUsuario;

    }



    public void setOng(Ong ong) {

        this.ong = ong;

    }

    public Ong getOng() {

        return ong;

    }

}

