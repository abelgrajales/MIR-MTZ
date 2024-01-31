package com.adeabel.mir.modelos;

public class Subtemas {

    public int id_subtema;
    public String nombre_subtema;

    public Subtemas() {
    }

    public Subtemas(int id_subtema, String nombre_subtema) {
        this.id_subtema = id_subtema;
        this.nombre_subtema = nombre_subtema;
    }

    public int getId_subtema() {
        return id_subtema;
    }

    public void setId_subtema(int id_subtema) {
        this.id_subtema = id_subtema;
    }

    public String getNombre_subtema() {
        return nombre_subtema;
    }

    public void setNombre_subtema(String nombre_subtema) {
        this.nombre_subtema = nombre_subtema;
    }
}
