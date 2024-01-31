package com.adeabel.mir.modelos;

public class Usuarios {
    int id_usuario;
    String correo_usuario;
    String nombre_usuario;
    String contraseña_usuario;
    int id_centro_gestor;
    String nombre_centro_gestor;

    public Usuarios() {
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContraseña_usuario() {
        return contraseña_usuario;
    }

    public void setContraseña_usuario(String contraseña_usuario) {
        this.contraseña_usuario = contraseña_usuario;
    }

    public int getId_centro_gestor() {
        return id_centro_gestor;
    }

    public void setId_centro_gestor(int id_centro_gestor) {
        this.id_centro_gestor = id_centro_gestor;
    }

    public String getNombre_centro_gestor() {
        return nombre_centro_gestor;
    }

    public void setNombre_centro_gestor(String nombre_centro_gestor) {
        this.nombre_centro_gestor = nombre_centro_gestor;
    }

}
