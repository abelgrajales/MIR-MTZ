package com.adeabel.mir.modelos;

public class MirIndicador {

    int id_mir_indicador;
    String tipo_indicador;
    String nombre_indicador;
    String descripcion_indicador;
    String dimension;
    String frecuencia;
    String algoritmo;
    String variable_a;
    String unidad_a;
    String variable_b;
    String unidad_b;
    int valor_programado_a;
    int valor_programado_b;
    int meta_indicador;
    String medios_verificacion;
    int optimo_min;
    int optimo_max;
    int proceso_min;
    int proceso_max;
    int rezago_min;
    int rezago_max;
    int id_mir_fin_proposito;

    public MirIndicador() {
    }

    public MirIndicador(int id_mir_indicador, String tipo_indicador, String nombre_indicador, String descripcion_indicador, String dimension, String frecuencia, String algoritmo, String variable_a, String unidad_a, String variable_b, String unidad_b, int valor_programado_a, int valor_programado_b, int meta_indicador, String medios_verificacion, int optimo_min, int optimo_max, int proceso_min, int proceso_max, int rezago_min, int rezago_max, int id_mir_fin_proposito) {
        this.id_mir_indicador = id_mir_indicador;
        this.tipo_indicador = tipo_indicador;
        this.nombre_indicador = nombre_indicador;
        this.descripcion_indicador = descripcion_indicador;
        this.dimension = dimension;
        this.frecuencia = frecuencia;
        this.algoritmo = algoritmo;
        this.variable_a = variable_a;
        this.unidad_a = unidad_a;
        this.variable_b = variable_b;
        this.unidad_b = unidad_b;
        this.valor_programado_a = valor_programado_a;
        this.valor_programado_b = valor_programado_b;
        this.meta_indicador = meta_indicador;
        this.medios_verificacion = medios_verificacion;
        this.optimo_min = optimo_min;
        this.optimo_max = optimo_max;
        this.proceso_min = proceso_min;
        this.proceso_max = proceso_max;
        this.rezago_min = rezago_min;
        this.rezago_max = rezago_max;
        this.id_mir_fin_proposito = id_mir_fin_proposito;
    }

    public int getId_mir_indicador() {
        return id_mir_indicador;
    }

    public void setId_mir_indicador(int id_mir_indicador) {
        this.id_mir_indicador = id_mir_indicador;
    }

    public String getTipo_indicador() {
        return tipo_indicador;
    }

    public void setTipo_indicador(String tipo_indicador) {
        this.tipo_indicador = tipo_indicador;
    }

    public String getNombre_indicador() {
        return nombre_indicador;
    }

    public void setNombre_indicador(String nombre_indicador) {
        this.nombre_indicador = nombre_indicador;
    }

    public String getDescripcion_indicador() {
        return descripcion_indicador;
    }

    public void setDescripcion_indicador(String descripcion_indicador) {
        this.descripcion_indicador = descripcion_indicador;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public String getVariable_a() {
        return variable_a;
    }

    public void setVariable_a(String variable_a) {
        this.variable_a = variable_a;
    }

    public String getUnidad_a() {
        return unidad_a;
    }

    public void setUnidad_a(String unidad_a) {
        this.unidad_a = unidad_a;
    }

    public String getVariable_b() {
        return variable_b;
    }

    public void setVariable_b(String variable_b) {
        this.variable_b = variable_b;
    }

    public String getUnidad_b() {
        return unidad_b;
    }

    public void setUnidad_b(String unidad_b) {
        this.unidad_b = unidad_b;
    }

    public int getValor_programado_a() {
        return valor_programado_a;
    }

    public void setValor_programado_a(int valor_programado_a) {
        this.valor_programado_a = valor_programado_a;
    }

    public int getValor_programado_b() {
        return valor_programado_b;
    }

    public void setValor_programado_b(int valor_programado_b) {
        this.valor_programado_b = valor_programado_b;
    }

    public int getMeta_indicador() {
        return meta_indicador;
    }

    public void setMeta_indicador(int meta_indicador) {
        this.meta_indicador = meta_indicador;
    }

    public String getMedios_verificacion() {
        return medios_verificacion;
    }

    public void setMedios_verificacion(String medios_verificacion) {
        this.medios_verificacion = medios_verificacion;
    }

    public int getOptimo_min() {
        return optimo_min;
    }

    public void setOptimo_min(int optimo_min) {
        this.optimo_min = optimo_min;
    }

    public int getOptimo_max() {
        return optimo_max;
    }

    public void setOptimo_max(int optimo_max) {
        this.optimo_max = optimo_max;
    }

    public int getProceso_min() {
        return proceso_min;
    }

    public void setProceso_min(int proceso_min) {
        this.proceso_min = proceso_min;
    }

    public int getProceso_max() {
        return proceso_max;
    }

    public void setProceso_max(int proceso_max) {
        this.proceso_max = proceso_max;
    }

    public int getRezago_min() {
        return rezago_min;
    }

    public void setRezago_min(int rezago_min) {
        this.rezago_min = rezago_min;
    }

    public int getRezago_max() {
        return rezago_max;
    }

    public void setRezago_max(int rezago_max) {
        this.rezago_max = rezago_max;
    }

    public int getId_mir_fin_proposito() {
        return id_mir_fin_proposito;
    }

    public void setId_mir_fin_proposito(int id_mir_fin_proposito) {
        this.id_mir_fin_proposito = id_mir_fin_proposito;
    }
}
