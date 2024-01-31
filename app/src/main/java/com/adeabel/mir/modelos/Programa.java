package com.adeabel.mir.modelos;

public class Programa {

    public int id_programa;
    public String nombre_programa;
    public int ejesId, subtemasId, estrategiasId, centrogestorId;

    public Programa() {
    }

    public Programa(int id_programa, String nombre_programa, int ejesId, int subtemasId, int estrategiasId, int centrogestorId) {
        this.id_programa = id_programa;
        this.nombre_programa = nombre_programa;
        this.ejesId = ejesId;
        this.subtemasId = subtemasId;
        this.estrategiasId = estrategiasId;
        this.centrogestorId = centrogestorId;
    }

    public int getId_programa() {
        return id_programa;
    }

    public void setId_programa(int id_programa) {
        this.id_programa = id_programa;
    }

    public String getNombre_programa() {
        return nombre_programa;
    }

    public void setNombre_programa(String nombre_programa) {
        this.nombre_programa = nombre_programa;
    }

    public int getEjesId() {
        return ejesId;
    }

    public void setEjesId(int ejesId) {
        this.ejesId = ejesId;
    }

    public int getSubtemasId() {
        return subtemasId;
    }

    public void setSubtemasId(int subtemasId) {
        this.subtemasId = subtemasId;
    }

    public int getEstrategiasId() {
        return estrategiasId;
    }

    public void setEstrategiasId(int estrategiasId) {
        this.estrategiasId = estrategiasId;
    }

    public int getCentrogestorId() {
        return centrogestorId;
    }

    public void setCentrogestorId(int centrogestorId) {
        this.centrogestorId = centrogestorId;
    }

    public String toString(){
        return nombre_programa;
    }
}
