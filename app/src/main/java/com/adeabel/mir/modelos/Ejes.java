package com.adeabel.mir.modelos;

public class Ejes {
    public int id_eje;
    public String nombre_eje;

    public Ejes() {
    }

    public Ejes(int id_eje, String nombre_eje) {
        this.id_eje = id_eje;
        this.nombre_eje = nombre_eje;
    }

    public int getId_eje() {
        return id_eje;
    }

    public void setId_eje(int id_eje) {
        this.id_eje = id_eje;
    }

    public String getNombre_eje() {
        return nombre_eje;
    }

    public void setNombre_eje(String nombre_eje) {
        this.nombre_eje = nombre_eje;
    }

    @Override
    public String toString(){
        return nombre_eje;
    }
}
