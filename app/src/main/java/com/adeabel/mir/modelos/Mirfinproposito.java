package com.adeabel.mir.modelos;

public class Mirfinproposito {

    int idmirfinproposito;
    String resumen_narrativo;
    String supuestos;
    String nombre_nivel;
    int id_programa;

    public Mirfinproposito() {
    }

    public Mirfinproposito(int idmirfinproposito, String resumen_narrativo, String supuestos, String nombre_nivel, int id_programa) {
        this.idmirfinproposito = idmirfinproposito;
        this.resumen_narrativo = resumen_narrativo;
        this.supuestos = supuestos;
        this.nombre_nivel = nombre_nivel;
        this.id_programa = id_programa;
    }

    public int getIdmirfinproposito() {
        return idmirfinproposito;
    }

    public void setIdmirfinproposito(int idmirfinproposito) {
        this.idmirfinproposito = idmirfinproposito;
    }

    public String getResumen_narrativo() {
        return resumen_narrativo;
    }

    public void setResumen_narrativo(String resumen_narrativo) {
        this.resumen_narrativo = resumen_narrativo;
    }

    public String getSupuestos() {
        return supuestos;
    }

    public void setSupuestos(String supuestos) {
        this.supuestos = supuestos;
    }

    public String getNombre_nivel() {
        return nombre_nivel;
    }

    public void setNombre_nivel(String nombre_nivel) {
        this.nombre_nivel = nombre_nivel;
    }

    public int getId_programa() {
        return id_programa;
    }

    public void setId_programa(int id_programa) {
        this.id_programa = id_programa;
    }
}
