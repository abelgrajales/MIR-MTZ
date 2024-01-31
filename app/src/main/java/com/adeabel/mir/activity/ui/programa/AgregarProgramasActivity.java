package com.adeabel.mir.activity.ui.programa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.adeabel.mir.R;
import com.adeabel.mir.activity.ui.ejes.AdapterEjes;
import com.adeabel.mir.modelos.Ejes;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgregarProgramasActivity extends AppCompatActivity {

    TextInputLayout nombre_programa;
    AutoCompleteTextView speje;
    AutoCompleteTextView spsubtema;
    AutoCompleteTextView spestrategia;
    AutoCompleteTextView spcentrogestor;
    Button agregar_programa;

    ArrayList<String> listaEjes;
    ArrayAdapter<String> ejesAdapter;

    ArrayList<String> listaSubtemas;
    ArrayAdapter<String> subtemasAdapter;

    ArrayList<String> listaEstrategias;
    ArrayAdapter<String> estrategiasAdapter;

    ArrayList<String> listaCentroGestor;
    ArrayAdapter<String> centroGestorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_programas);

        nombre_programa = findViewById(R.id.ti_nombre_programa);
        agregar_programa = findViewById(R.id.btn_guardar_programa);

        //llenar ejes
        speje = findViewById(R.id.sp_agregar_eje);
        listaEjes = new ArrayList<>();
        ejesAdapter = new ArrayAdapter<String>(this, R.layout.opcion_item, listaEjes);
        cargarEjes();

        //llenar subtemas
        spsubtema = findViewById(R.id.sp_agregar_subtema);
        listaSubtemas = new ArrayList<>();
        subtemasAdapter = new ArrayAdapter<String>(this, R.layout.opcion_item, listaSubtemas);
        cargarSubtemas();

        //llenar estrategias
        spestrategia = findViewById(R.id.sp_agregar_estrategia);
        listaEstrategias = new ArrayList<>();
        estrategiasAdapter = new ArrayAdapter<String>(this, R.layout.opcion_item, listaEstrategias);
        cargarEstrategias();

        //llenar centro gestor
        spcentrogestor = findViewById(R.id.sp_agregar_centro_gestor);
        listaCentroGestor = new ArrayList<>();
        centroGestorAdapter = new ArrayAdapter<String>(this, R.layout.opcion_item, listaCentroGestor);
        cargarCentroGestor();

        agregar_programa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos(view);
            }
        });
    }


    private void insertarDatos(View view) {
        final String programa = nombre_programa.getEditText().getText().toString();
        final String eje = speje.getText().toString();
        final String subtema = spsubtema.getText().toString();
        final String estrategia = spestrategia.getText().toString();
        final String centrogestor = spcentrogestor.getText().toString();

        if(programa.isEmpty() || eje.isEmpty() || subtema.isEmpty() || estrategia.isEmpty()){
            Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_LONG).show();
            return;
        }else {
            StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/programas/insertar_programa.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Agregado con exito").setConfirmClickListener("Listo", new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    kAlertDialog.dismissWithAnimation();
                                }
                            }).show();
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
                    params.put("nombre_programa", programa);
                    params.put("nombre_eje", eje);
                    params.put("nombre_subtema", subtema);
                    params.put("nombre_estrategia", estrategia);
                    params.put("nombre_centro_gestor", centrogestor);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }

    private void cargarEjes(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/ejes/listar_eje.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nombre = object.getString("nombre_eje").toString();

                        listaEjes.add(nombre);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                speje.setAdapter(ejesAdapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void cargarSubtemas(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/subtemas/listar_subtema.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nombre = object.getString("nombre_subtema").toString();

                        listaSubtemas.add(nombre);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //aqui
                spsubtema.setAdapter(subtemasAdapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void cargarEstrategias(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/estrategias/listar_estrategia.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nombre = object.getString("nombre_estrategia").toString();

                        listaEstrategias.add(nombre);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                spestrategia.setAdapter(estrategiasAdapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void cargarCentroGestor(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/centrogestor/listar_centro_gestor.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nombre = object.getString("nombre_centro_gestor").toString();

                        listaCentroGestor.add(nombre);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                spcentrogestor.setAdapter(centroGestorAdapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}