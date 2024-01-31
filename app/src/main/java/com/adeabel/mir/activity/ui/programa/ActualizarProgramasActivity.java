package com.adeabel.mir.activity.ui.programa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
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

public class ActualizarProgramasActivity extends AppCompatActivity {

    TextInputLayout nombre_programa, nombre_eje, nombre_subtema, nombre_estrategia, nombre_centro_gestor;
    Button actualizar_programa;
    String id, id_eje, id_subtema, id_estrategia, id_centro_gestor;
    String nombrePrograma;

    AutoCompleteTextView speje;
    AutoCompleteTextView spsubtema;
    AutoCompleteTextView spestrategia;
    AutoCompleteTextView spcentrogestor;

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
        setContentView(R.layout.activity_actualizar_programas);

        nombre_programa = findViewById(R.id.ti_nombre_programa_actualizar);
        actualizar_programa = findViewById(R.id.btn_actualizar_programa);

        Intent intent = getIntent();
        id = Integer.toString(intent.getExtras().getInt("id"));
        id_eje = Integer.toString(intent.getExtras().getInt("id_eje"));
        id_subtema = Integer.toString(intent.getExtras().getInt("id_subtema"));
        id_estrategia = Integer.toString(intent.getExtras().getInt("id_estrategia"));
        id_centro_gestor = Integer.toString(intent.getExtras().getInt("id_centro_gestor"));
        nombrePrograma = intent.getExtras().getString("nombre");

        //completar eje
        nombre_eje = findViewById(R.id.ti_eje_actualizar);
        buscarEjeById(id_eje);

        //completar subtema
        nombre_subtema = findViewById(R.id.ti_subtema_actualizar);
        buscarSubtemaById(id_subtema);

        //completar subtema
        nombre_estrategia = findViewById(R.id.ti_estrategia_actualizar);
        buscarEstrategiaById(id_estrategia);

        //completar subtema
        nombre_centro_gestor = findViewById(R.id.ti_centro_gestor_actualizar);
        buscarCentroGestorById(id_centro_gestor);

        //llenar ejes
        speje = findViewById(R.id.sp_actualizar_eje);
        listaEjes = new ArrayList<>();
        ejesAdapter = new ArrayAdapter<String>(this, R.layout.opcion_item, listaEjes);
        cargarEjes();

        //llenar subtemas
        spsubtema = findViewById(R.id.sp_actualizar_subtema);
        listaSubtemas = new ArrayList<>();
        subtemasAdapter = new ArrayAdapter<String>(this, R.layout.opcion_item, listaSubtemas);
        cargarSubtemas();

        //llenar estrategias
        spestrategia = findViewById(R.id.sp_actualizar_estrategia);
        listaEstrategias = new ArrayList<>();
        estrategiasAdapter = new ArrayAdapter<String>(this, R.layout.opcion_item, listaEstrategias);
        cargarEstrategias();

        //llenar centro gestor
        spcentrogestor = findViewById(R.id.sp_actualizar_centro_gestor);
        listaCentroGestor = new ArrayList<>();
        centroGestorAdapter = new ArrayAdapter<String>(this, R.layout.opcion_item, listaCentroGestor);
        cargarCentroGestor();

        nombre_programa.getEditText().setText(nombrePrograma);


        actualizar_programa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarPrograma(view);
            }
        });


    }

    public void actualizarPrograma(View view){
        String nombreActualizado = nombre_programa.getEditText().getText().toString();
        final String eje = speje.getText().toString();
        final String subtema = spsubtema.getText().toString();
        final String estrategia = spestrategia.getText().toString();
        final String centrogestor = spcentrogestor.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/programas/actualizar_programa.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Actualizado con exito").setConfirmClickListener("Listo", new KAlertDialog.KAlertClickListener() {
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
                params.put("id_programa", id);
                params.put("nombre_programa", nombreActualizado);
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

    private void buscarEjeById(String id){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/ejes/buscar_eje.php?id_eje=" + id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nombre = object.getString("nombre_eje").toString();

                        nombre_eje.getEditText().setText(nombre);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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

    private void buscarSubtemaById(String id){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/subtemas/buscar_subtema.php?id_subtema=" + id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nombre = object.getString("nombre_subtema").toString();

                        nombre_subtema.getEditText().setText(nombre);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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

    private void buscarEstrategiaById(String id){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/estrategias/buscar_estrategia.php?id_estrategia=" + id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nombre = object.getString("nombre_estrategia").toString();

                        nombre_estrategia.getEditText().setText(nombre);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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

    private void buscarCentroGestorById(String id){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/centrogestor/buscar_centro_gestor.php?id_centro_gestor=" + id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String nombre = object.getString("nombre_centro_gestor").toString();

                        nombre_centro_gestor.getEditText().setText(nombre);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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