package com.adeabel.mir.modelos;

public class CentroGestor {

    public int id_centro_gestor;
    public String nombre_centro_gestor;

    public CentroGestor() {
    }

    public CentroGestor(int id_centro_gestor, String nombre_centro_gestor) {
        this.id_centro_gestor = id_centro_gestor;
        this.nombre_centro_gestor = nombre_centro_gestor;
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
