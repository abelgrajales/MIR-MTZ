package com.adeabel.mir.activity.ui.matriz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.adeabel.mir.R;

public class DetallesIndicadorActivity extends AppCompatActivity {

    String tipo, nombre, descripcion, dimension, frecuencia, algoritmo, variablea, unidada, variableb, unidadb, medios;
    int programadoa, programadob, meta, rezagomim, rezagomax, procesomin, procesomax, optimomin, optimomax;

    TextView tv_tipo, tv_nombre, tv_descripcion, tv_dimension, tv_frecuencia, tv_algoritmo, tv_variablea, tv_unidada, tv_variableb, tv_unidadb, tv_medios;
    TextView tv_programadoa, tv_programadob, tv_meta, tv_rezagomim, tv_rezagomax, tv_procesomin, tv_procesomax, tv_optimomin, tv_optimomax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_indicador);

        Intent intent = getIntent();
        tipo = intent.getExtras().getString("tipo");
        nombre = intent.getExtras().getString("nombre");
        descripcion = intent.getExtras().getString("descripcion");
        dimension = intent.getExtras().getString("dimension");
        frecuencia = intent.getExtras().getString("frecuencia");
        algoritmo = intent.getExtras().getString("algoritmo");
        variablea = intent.getExtras().getString("variablea");
        unidada = intent.getExtras().getString("unidada");
        variableb = intent.getExtras().getString("variableb");
        unidadb = intent.getExtras().getString("unidadb");
        programadoa = intent.getExtras().getInt("valorprogramadoa");
        programadob = intent.getExtras().getInt("valorprogramadob");
        meta = intent.getExtras().getInt("metaindicador");
        medios = intent.getExtras().getString("medios");
        rezagomim = intent.getExtras().getInt("rezagomin");
        rezagomax = intent.getExtras().getInt("rezagomax");
        procesomin = intent.getExtras().getInt("procesomin");
        procesomax = intent.getExtras().getInt("procesomax");
        optimomin = intent.getExtras().getInt("optimomin");
        optimomax = intent.getExtras().getInt("optimomax");

        tv_tipo = findViewById(R.id.tv_tipo_indicador_detalles);
        tv_nombre = findViewById(R.id.tv_nombre_indicador_detalles);
        tv_descripcion = findViewById(R.id.tv_descripcion_indicador_detalles);
        tv_dimension = findViewById(R.id.tv_dimension_indicador_detalles);
        tv_frecuencia = findViewById(R.id.tv_frecuencia_indicador_detalles);
        tv_algoritmo = findViewById(R.id.tv_algoritmo_indicador_detalles);
        tv_variablea = findViewById(R.id.tv_variablea_detalles);
        tv_unidada = findViewById(R.id.tv_unidada_detalles);
        tv_variableb = findViewById(R.id.tv_variableb_detalles);
        tv_unidadb = findViewById(R.id.tv_unidadb_detalles);
        tv_medios = findViewById(R.id.tv_medios_verificacion_detalles);
        tv_programadoa = findViewById(R.id.tv_valora_detalles);
        tv_programadob= findViewById(R.id.tv_valorb_detalles);
        tv_meta = findViewById(R.id.tv_meta_detalles);
        tv_rezagomim= findViewById(R.id.tv_rezago_min_detalles);
        tv_rezagomax= findViewById(R.id.tv_rezago_max_detalles);
        tv_procesomin= findViewById(R.id.tv_proceso_min_detalles);
        tv_procesomax= findViewById(R.id.tv_proceso_max_detalles);
        tv_optimomin= findViewById(R.id.tv_optimo_min_detalles);
        tv_optimomax= findViewById(R.id.tv_optimo_max_detalles);

        tv_tipo.setText(tipo);
        tv_nombre.setText(nombre);
        tv_descripcion.setText(descripcion);
        tv_dimension.setText(dimension);
        tv_frecuencia.setText(frecuencia);
        tv_algoritmo.setText(algoritmo);
        tv_variablea.setText(variablea);
        tv_unidada.setText(unidada);
        tv_variableb.setText(variableb);
        tv_unidadb.setText(unidadb);
        tv_medios.setText(medios);
        tv_programadoa.setText(String.valueOf(programadoa));
        tv_programadob.setText(String.valueOf(programadob));
        tv_meta.setText(String.valueOf(meta));
        tv_rezagomim.setText(String.valueOf(rezagomim));
        tv_rezagomax.setText(String.valueOf(rezagomax));
        tv_procesomin.setText(String.valueOf(procesomin));
        tv_procesomax.setText(String.valueOf(procesomax));
        tv_optimomin.setText(String.valueOf(optimomin));
        tv_optimomax.setText(String.valueOf(optimomax));

    }
}