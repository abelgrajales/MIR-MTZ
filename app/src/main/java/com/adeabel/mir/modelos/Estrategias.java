package com.adeabel.mir.modelos;

public class Estrategias {

    public int id_estrategia;
    public String nombre_estrategia;

    public Estrategias() {
    }

    public Estrategias(int id_estrategia, String nombre_estrategia) {
        this.id_estrategia = id_estrategia;
        this.nombre_estrategia = nombre_estrategia;
    }

    public int getId_estrategia() {
        return id_estrategia;
    }

    public void setId_estrategia(int id_estrategia) {
        this.id_estrategia = id_estrategia;
    }

    public String getNombre_estrategia() {
        return nombre_estrategia;
    }

    public void setNombre_estrategia(String nombre_estrategia) {
        this.nombre_estrategia = nombre_estrategia;
    }
}
