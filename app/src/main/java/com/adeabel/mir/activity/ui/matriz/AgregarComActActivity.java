package com.adeabel.mir.activity.ui.matriz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adeabel.mir.R;
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

public class AgregarComActActivity extends AppCompatActivity {

    //Resumen y supuestos
    TextInputLayout resumen, supuestos;
    Button agregar_nivel;
    TextView tv_programa, tv_nivel;

    String nivel, nombre_programa;
    int idprograma;

    String idMirNivel;

    //Resto del indicador
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_com_act);

        Intent intent = getIntent();
        nivel = intent.getExtras().getString("nivel");
        nombre_programa = intent.getExtras().getString("programa");
        idprograma = intent.getExtras().getInt("idprograma");

        tv_programa = findViewById(R.id.tv_mir_nombre_programa_ca);
        tv_nivel = findViewById(R.id.tv_agregar_ca);


        //Resumen y supuestos
        tv_programa.setText(nombre_programa);
        tv_nivel.setText(nivel);

        resumen = findViewById(R.id.ti_resumen_narrativo_ca);
        supuestos = findViewById(R.id.ti_supuestos_ca);

        //Resto del indicador
        //Vincular sppinner
        spTipoIndicador = findViewById(R.id.sp_tipo_indicador_ca);
        spDimension = findViewById(R.id.sp_dimension_ca);
        spFrecuencia = findViewById(R.id.sp_frecuencia_ca);
        spAlgoritmo = findViewById(R.id.sp_algoritmo_ca);

        variable_calculo_a = findViewById(R.id.ti_variable_a_ca);
        variable_calculo_b = findViewById(R.id.ti_variable_b_ca);
        unidad_a = findViewById(R.id.ti_unidad_a_ca);
        unidad_b = findViewById(R.id.ti_unidad_b_ca);
        valor_programado_a = findViewById(R.id.ti_meta_a_ca);
        valor_programado_b = findViewById(R.id.ti_meta_b_ca);

        nombre_indicador = findViewById(R.id.ti_nombre_indicador_ca);
        descripcion_indicador = findViewById(R.id.ti_descripcion_indicador_ca);
        medios_verificacion = findViewById(R.id.ti_medios_verificacion_ca);

        optimo_min = findViewById(R.id.ti_optimo_min_ca);
        optimo_max = findViewById(R.id.ti_optimo_max_ca);

        proceso_min = findViewById(R.id.ti_proceso_min_ca);
        proceso_max = findViewById(R.id.ti_proceso_max_ca);

        rezago_min = findViewById(R.id.ti_rezago_min_ca);
        rezago_max = findViewById(R.id.ti_rezago_max_ca);

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

        //boton guardar
        agregar_nivel = findViewById(R.id.btn_guardar_indicador_ca);

        spAlgoritmo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                variable_calculo_a.setEnabled(false);
                variable_calculo_b.setEnabled(false);
                unidad_a.setEnabled(false);
                unidad_b.setEnabled(false);

                if (spAlgoritmo.getSelectedItem().equals("(A/B) * 100")) {
                    variable_calculo_a.setEnabled(true);
                    variable_calculo_b.setEnabled(true);
                    unidad_a.setEnabled(true);
                    unidad_b.setEnabled(true);
                    valor_programado_a.setEnabled(true);
                    valor_programado_b.setEnabled(true);

                } else if (spAlgoritmo.getSelectedItem().equals("((A/B)-1) * 100")) {
                    variable_calculo_a.setEnabled(true);
                    variable_calculo_b.setEnabled(true);
                    unidad_a.setEnabled(true);
                    unidad_b.setEnabled(true);
                    valor_programado_a.setEnabled(true);
                    valor_programado_b.setEnabled(true);

                } else if (spAlgoritmo.getSelectedItem().equals("A/B")) {
                    variable_calculo_a.setEnabled(true);
                    variable_calculo_b.setEnabled(true);
                    unidad_a.setEnabled(true);
                    unidad_b.setEnabled(true);
                    valor_programado_a.setEnabled(true);
                    valor_programado_b.setEnabled(true);

                } else if (spAlgoritmo.getSelectedItem().equals("A")) {
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

        agregar_nivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos(view);
            }
        });

    }

    private void insertarDatos(View view) {
        final String resumen_narrativo = resumen.getEditText().getText().toString();
        final String supuestos1 = supuestos.getEditText().getText().toString();
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

        final float valorprogramadoa;
        final float valorprogramadob;


        if (valor_programado_a.getEditText().getText().toString().isEmpty()) {
            valorprogramadoa = 0;
        } else {
            valorprogramadoa = Integer.parseInt(valor_programado_a.getEditText().getText().toString());
        }

        if (valor_programado_b.getEditText().getText().toString().isEmpty()) {
            valorprogramadob = 0;
        } else {
            valorprogramadob = Integer.parseInt(valor_programado_b.getEditText().getText().toString());
        }

        float metaindicador;
        if (!spAlgoritmo.getSelectedItem().equals("A")) {
            metaindicador = metaIndicador(valorprogramadoa, valorprogramadob);
        } else {
            metaindicador = valorprogramadoa;
        }

        final int optimomin = Integer.parseInt(optimo_min.getEditText().getText().toString());
        final int optimomax = Integer.parseInt(optimo_max.getEditText().getText().toString());
        final int procesomin = Integer.parseInt(proceso_min.getEditText().getText().toString());
        final int procesomax = Integer.parseInt(proceso_max.getEditText().getText().toString());
        final int rezagomin = Integer.parseInt(rezago_min.getEditText().getText().toString());
        final int rezagomax = Integer.parseInt(rezago_max.getEditText().getText().toString());


        if (nombreindicador.isEmpty() || descripcionindicador.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre de la estrategia", Toast.LENGTH_LONG).show();
            return;
        } else if (rezagomin > rezagomax) {
            Toasty.error(getApplicationContext(), "El valor del rezago minimo es mayor al maximo", Toasty.LENGTH_LONG, true).show();
        } else if (procesomin <= rezagomax) {
            Toasty.error(getApplicationContext(), "El valor del proceso minimo es menor o igual al rezago maximo", Toasty.LENGTH_LONG, true).show();
        } else if (optimomin <= procesomax) {
            Toasty.error(getApplicationContext(), "El valor del óptimo minimo es menor o igual al proceso maximo", Toasty.LENGTH_LONG, true).show();
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/mircomact/insertar_comact.php", new Response.Listener<String>() {
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
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("resumen_narrativo", resumen_narrativo);
                    params.put("supuestos", supuestos1);
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
                    params.put("nombre_nivel", nivel);
                    params.put("id_programa", String.valueOf(idprograma));
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }

        public float metaIndicador ( float varA, float varB){
            float resultado = 0;

            if (spAlgoritmo.getSelectedItem().equals("(A/B) * 100")) {
                resultado = (varA / varB) * 100;
            } else if (spAlgoritmo.getSelectedItem().equals("((A/B)-1) * 100")) {
                resultado = ((varA / varB) - 1) * 100;
            } else if (spAlgoritmo.getSelectedItem().equals("A/B")) {
                resultado = varA / varB;
            } else if (spAlgoritmo.getSelectedItem().equals("A")) {
                resultado = varA;
            }
            return resultado;
        }
}