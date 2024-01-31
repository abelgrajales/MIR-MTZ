package com.adeabel.mir.activity.ui.matriz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.adeabel.mir.R;
import com.adeabel.mir.modelos.Programa;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class AgregarIndicadorActivity extends AppCompatActivity {
    String idMirNivel;

    ArrayList<String> listaTipoIndicador;
    ArrayList<String> listaDimension;
    ArrayList<String> listaFrecuencia;
    ArrayList<String> listaAlgoritmo;

    SmartMaterialSpinner<String> spTipoIndicador;
    SmartMaterialSpinner<String> spDimension;
    SmartMaterialSpinner<String> spFrecuencia;
    SmartMaterialSpinner<String> spAlgoritmo;

    TextInputLayout nombre_indicador, descripcion_indicador;
    TextInputLayout variable_calculo_a, variable_calculo_b;
    TextInputLayout unidad_a, unidad_b;
    TextInputLayout valor_programado_a, valor_programado_b;
    TextInputLayout medios_verificacion;

    TextInputLayout optimo_min, optimo_max, proceso_min, proceso_max, rezago_min, rezago_max;

    Button agregar_estrategia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_indicador);

        //Vincular sppinner
        spTipoIndicador = findViewById(R.id.sp_tipo_indicador);
        spDimension = findViewById(R.id.sp_dimension);
        spFrecuencia = findViewById(R.id.sp_frecuencia);
        spAlgoritmo = findViewById(R.id.sp_algoritmo);

        variable_calculo_a = findViewById(R.id.ti_variable_a);
        variable_calculo_b = findViewById(R.id.ti_variable_b);
        unidad_a = findViewById(R.id.ti_unidad_a);
        unidad_b = findViewById(R.id.ti_unidad_b);
        valor_programado_a = findViewById(R.id.ti_meta_a);
        valor_programado_b = findViewById(R.id.ti_meta_b);

        nombre_indicador = findViewById(R.id.ti_nombre_indicador);
        descripcion_indicador = findViewById(R.id.ti_descripcion_indicador);
        medios_verificacion = findViewById(R.id.ti_medios_verificacion);

        optimo_min = findViewById(R.id.ti_optimo_min);
        optimo_max = findViewById(R.id.ti_optimo_max);

        proceso_min = findViewById(R.id.ti_proceso_min);
        proceso_max = findViewById(R.id.ti_proceso_max);

        rezago_min = findViewById(R.id.ti_rezago_min);
        rezago_max = findViewById(R.id.ti_rezago_max);

        agregar_estrategia = findViewById(R.id.btn_guardar_indicador);

        Intent intent = getIntent();
        idMirNivel = intent.getExtras().getString("id");

        variable_calculo_a.setEnabled(false);
        variable_calculo_b.setEnabled(false);
        unidad_a.setEnabled(false);
        unidad_b.setEnabled(false);
        valor_programado_a.setEnabled(false);
        valor_programado_b.setEnabled(false);

        //LLenar spiner Tipo Indicador
        listaTipoIndicador = new ArrayList<>();
        listaTipoIndicador.add("Gestión");
        listaTipoIndicador.add("Estrategico");
        spTipoIndicador.setItem(listaTipoIndicador);

        //llenar spiner dimension
        listaDimension = new ArrayList<>();
        listaDimension.add("Eficacia");
        listaDimension.add("Eficiencia");
        listaDimension.add("Calidad");
        listaDimension.add("Economía");
        spDimension.setItem(listaDimension);

        //Llenar spiner frecuencia
        listaFrecuencia = new ArrayList<>();
        listaFrecuencia.add("Mensual");
        listaFrecuencia.add("Bimestral");
        listaFrecuencia.add("Trimestral");
        listaFrecuencia.add("Cuatrimestral");
        listaFrecuencia.add("Semestral");
        listaFrecuencia.add("Anual");
        listaFrecuencia.add("Bienal");
        listaFrecuencia.add("Bianual");
        listaFrecuencia.add("Trianual");
        listaFrecuencia.add("Sexenal");
        spFrecuencia.setItem(listaFrecuencia);

        //llenar algoritmo
        listaAlgoritmo = new ArrayList<>();
        listaAlgoritmo.add("(A/B) * 100");
        listaAlgoritmo.add("((A/B)-1) * 100");
        listaAlgoritmo.add("A/B");
        listaAlgoritmo.add("A");
        spAlgoritmo.setItem(listaAlgoritmo);

        spAlgoritmo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                variable_calculo_a.setEnabled(false);
                variable_calculo_b.setEnabled(false);
                unidad_a.setEnabled(false);
                unidad_b.setEnabled(false);

                if (spAlgoritmo.getSelectedItem().equals("(A/B) * 100") ){
                    variable_calculo_a.setEnabled(true);
                    variable_calculo_b.setEnabled(true);
                    unidad_a.setEnabled(true);
                    unidad_b.setEnabled(true);
                    valor_programado_a.setEnabled(true);
                    valor_programado_b.setEnabled(true);

                }else if (spAlgoritmo.getSelectedItem().equals("((A/B)-1) * 100")){
                    variable_calculo_a.setEnabled(true);
                    variable_calculo_b.setEnabled(true);
                    unidad_a.setEnabled(true);
                    unidad_b.setEnabled(true);
                    valor_programado_a.setEnabled(true);
                    valor_programado_b.setEnabled(true);

                }else if (spAlgoritmo.getSelectedItem().equals("A/B")){
                    variable_calculo_a.setEnabled(true);
                    variable_calculo_b.setEnabled(true);
                    unidad_a.setEnabled(true);
                    unidad_b.setEnabled(true);
                    valor_programado_a.setEnabled(true);
                    valor_programado_b.setEnabled(true);

                }else if (spAlgoritmo.getSelectedItem().equals("A")){
                    variable_calculo_a.setEnabled(true);
                    variable_calculo_b.setEnabled(false);
                    unidad_a.setEnabled(true);
                    unidad_b.setEnabled(false);
                    valor_programado_a.setEnabled(true);
                    valor_programado_b.setEnabled(false);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        agregar_estrategia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos(view);
            }
        });

    }

    private void insertarDatos(View view) {
        final String tipoindicador = spTipoIndicador.getSelectedItem().toString();
        final String nombreindicador = nombre_indicador.getEditText().getText().toString();
        final String descripcionindicador = descripcion_indicador.getEditText().getText().toString();
        final String dimension = spDimension.getSelectedItem().toString();
        final String frecuencia = spFrecuencia.getSelectedItem().toString();
        final String algoritmo = spAlgoritmo.getSelectedItem().toString();
        final String variablecalculoa = variable_calculo_a.getEditText().getText().toString();
        final String unidada = unidad_a.getEditText().getText().toString();
        final String variablecalculob = variable_calculo_b.getEditText().getText().toString();
        final String unidadb = unidad_b.getEditText().getText().toString();
        final String mediosverificacion = medios_verificacion.getEditText().getText().toString();

        System.out.println("Impresion variablecalclob" + variablecalculob);
        System.out.println("Impresion textinputB" + variable_calculo_b.getEditText().getText().toString());

        final float valorprogramadoa;
        final float valorprogramadob;


        if (valor_programado_a.getEditText().getText().toString().isEmpty()){
            valorprogramadoa = 0;
        }else{
            valorprogramadoa = Integer.parseInt(valor_programado_a.getEditText().getText().toString());
        }

        if (valor_programado_b.getEditText().getText().toString().isEmpty()){
            valorprogramadob = 0;
        }else{
            valorprogramadob = Integer.parseInt(valor_programado_b.getEditText().getText().toString());
        }

        float metaindicador;
        if (!spAlgoritmo.getSelectedItem().equals("A")){
            metaindicador = metaIndicador(valorprogramadoa, valorprogramadob);
        }else{
            metaindicador = valorprogramadoa;
        }

        final int optimomin = Integer.parseInt(optimo_min.getEditText().getText().toString());
        final int optimomax = Integer.parseInt(optimo_max.getEditText().getText().toString());
        final int procesomin = Integer.parseInt(proceso_min.getEditText().getText().toString());
        final int procesomax = Integer.parseInt(proceso_max.getEditText().getText().toString());
        final int rezagomin = Integer.parseInt(rezago_min.getEditText().getText().toString());
        final int rezagomax = Integer.parseInt(rezago_max.getEditText().getText().toString());


        if(nombreindicador.isEmpty() || descripcionindicador.isEmpty()){
            Toast.makeText(this, "Ingrese el nombre de la estrategia", Toast.LENGTH_LONG).show();
            return;
        }else if(rezagomin > rezagomax){
            Toasty.error(getApplicationContext(), "El valor del rezago minimo es mayor al maximo", Toasty.LENGTH_LONG, true).show();
        }
        else if(procesomin <= rezagomax){
            Toasty.error(getApplicationContext(), "El valor del proceso minimo es menor o igual al rezago maximo", Toasty.LENGTH_LONG, true).show();
        }else if(optimomin <= procesomax){
            Toasty.error(getApplicationContext(), "El valor del óptimo minimo es menor o igual al proceso maximo", Toasty.LENGTH_LONG, true).show();
        }
        else {
            StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/mirindicador/insertar_indicador.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Agregado con exito").setConfirmClickListener("Listo", new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    kAlertDialog.dismissWithAnimation();
                                }
                            }).show();

                    Toasty.success(getApplicationContext(), "Indicador agregado con exito" + response, Toasty.LENGTH_LONG, true).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new KAlertDialog(view.getContext(), KAlertDialog.ERROR_TYPE)
                            .setTitleText("Ocurrio un error").setConfirmClickListener("Listo", new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    kAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("tipo_indicador", tipoindicador);
                    params.put("nombre_indicador", nombreindicador);
                    params.put("descripcion_indicador", descripcionindicador);
                    params.put("dimension", dimension);
                    params.put("frecuencia", frecuencia);
                    params.put("algoritmo", algoritmo);
                    params.put("variable_a", variablecalculoa);
                    params.put("unidad_a", unidada);
                    params.put("variable_b", variablecalculob);
                    params.put("unidad_b", unidadb);
                    params.put("valor_programado_a", Float.toString(valorprogramadoa));
                    params.put("valor_programado_b", Float.toString(valorprogramadob));
                    params.put("meta_indicador", Float.toString(metaindicador));
                    params.put("medios_verificacion", mediosverificacion);
                    params.put("optimo_min", Integer.toString(optimomin));
                    params.put("optimo_max", Integer.toString(optimomax));
                    params.put("proceso_min", Integer.toString(procesomin));
                    params.put("proceso_max", Integer.toString(procesomax));
                    params.put("rezago_min", Integer.toString(rezagomin));
                    params.put("rezago_max", Integer.toString(rezagomax));
                    params.put("id_mir_fin_proposito", idMirNivel);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }

    public float metaIndicador(float varA, float varB){
        float resultado = 0;

        if (spAlgoritmo.getSelectedItem().equals("(A/B) * 100")){
            resultado = (varA/varB) * 100;
        }else if (spAlgoritmo.getSelectedItem().equals("((A/B)-1) * 100")){
            resultado = ((varA/varB)-1) * 100;
        }else if(spAlgoritmo.getSelectedItem().equals("A/B")){
            resultado = varA/varB;
        }else if(spAlgoritmo.getSelectedItem().equals("A")){
            resultado = varA;
        }
        return resultado;
    }
}