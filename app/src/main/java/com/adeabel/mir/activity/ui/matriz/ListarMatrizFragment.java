package com.adeabel.mir.activity.ui.matriz;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.adeabel.mir.R;
import com.adeabel.mir.activity.ui.matriz.actividad.AdapterMirAct;
import com.adeabel.mir.activity.ui.matriz.componente.AdapterMirComp;
import com.adeabel.mir.activity.ui.matriz.fin.AdapterMirFin;
import com.adeabel.mir.activity.ui.matriz.proposito.AdapterMirProposito;
import com.adeabel.mir.modelos.MirCompAct;
import com.adeabel.mir.modelos.MirIndicador;
import com.adeabel.mir.modelos.Mirfinproposito;
import com.adeabel.mir.modelos.Programa;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLOutput;
import java.sql.Struct;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


public class ListarMatrizFragment extends Fragment {

    SmartMaterialSpinner<Programa> spprograma;
    ArrayList<Programa> listaProgramas;
    Button agregar_fin;
    Button agregar_proposito;
    Button agregar_componente;
    Button agregar_actividad;
    Button generar_pdf;

    //Lista MIR NIVEL FIN
    String idNivelFin;
    RecyclerView recyclerViewFin;
    ArrayList<Mirfinproposito> listaMirFin;
    AdapterMirFin adapterMirFin;

    //LISTA MIR NIVEL PROPOSITO
    RecyclerView recyclerViewProposito;
    ArrayList<Mirfinproposito> listaMirProposito;
    AdapterMirProposito adapterMirProposito;

    //Listar MIR NIVEL COMPONENTE
    RecyclerView recyclerViewComponente;
    ArrayList<MirCompAct> listaComponente;
    AdapterMirComp adapterMirComp;

    //LISTAR NIVEL ACTVIDIAD
    RecyclerView recyclerViewActividad;
    ArrayList<MirCompAct> listaActividad;
    AdapterMirAct adapterMirAct;

    //Listar Indicadores FIN
    ArrayList<MirIndicador> listaIndicadoresFin;

    //Listar Indicadores Proposito
    ArrayList<MirIndicador> listaIndicadoresProposito;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_matriz, container, false);

        spprograma = view.findViewById(R.id.sp_seleccionar_programa);
        listaProgramas = new ArrayList<>();

        listaMirFin = new ArrayList<>();
        listaMirProposito = new ArrayList<>();
        listaComponente = new ArrayList<>();
        listaActividad = new ArrayList<>();
        listaIndicadoresFin = new ArrayList<>();
        listaIndicadoresProposito = new ArrayList<>();

        agregar_fin = view.findViewById(R.id.btn_agregar_fin);
        agregar_proposito = view.findViewById(R.id.btn_agregar_proposito);
        agregar_componente = view.findViewById(R.id.btn_agregar_componente);
        agregar_actividad = view.findViewById(R.id.btn_agregar_actividad);

        recyclerViewFin = view.findViewById(R.id.recyclermatrizfin);
        recyclerViewProposito = view.findViewById(R.id.recyclermatrizproposito);
        recyclerViewComponente = view.findViewById(R.id.recyclermatrizcomponente);
        recyclerViewActividad = view.findViewById(R.id.recyclermatrizactividad);

        cargarProgramas();

        agregar_fin.setVisibility(View.GONE);
        agregar_proposito.setVisibility(view.GONE);
        agregar_componente.setVisibility(View.GONE);
        agregar_actividad.setVisibility(View.GONE);

        generar_pdf = view.findViewById(R.id.btn_generar_matriz);

        agregar_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AgregarIndicadorActivity.class);
                startActivity(intent);
            }
        });

        spprograma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), Integer.toString(listaProgramas.get(i).getCentrogestorId()), Toast.LENGTH_LONG).show();

                agregar_fin.setVisibility(View.VISIBLE);
                agregar_proposito.setVisibility(View.VISIBLE);
                listaMirFin.clear();
                listaMirProposito.clear();
                listaComponente.clear();
                listaActividad.clear();
                listaMirFin.clear();
                listaIndicadoresFin.clear();
                listaIndicadoresProposito.clear();

                cargarMatrizFin();
                cargarMatrizProposito();
                cargarMatrizComponente();
                cargarMatrizActividad();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        agregar_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AgregarNivelMirActivity.class).putExtra("nivel", "Fin")
                        .putExtra("programa", spprograma.getSelectedItem().getNombre_programa())
                        .putExtra("idprograma", spprograma.getSelectedItem().getId_programa());
                startActivity(intent);
            }
        });

        agregar_proposito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AgregarNivelMirActivity.class).putExtra("nivel", "Proposito")
                        .putExtra("programa", spprograma.getSelectedItem().getNombre_programa())
                        .putExtra("idprograma", spprograma.getSelectedItem().getId_programa());
                startActivity(intent);
            }
        });

        agregar_componente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AgregarComActActivity.class).putExtra("nivel", "Componente")
                        .putExtra("programa", spprograma.getSelectedItem().getNombre_programa())
                        .putExtra("idprograma", spprograma.getSelectedItem().getId_programa());
                startActivity(intent);
            }
        });

        agregar_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AgregarComActActivity.class).putExtra("nivel", "Actividad")
                        .putExtra("programa", spprograma.getSelectedItem().getNombre_programa())
                        .putExtra("idprograma", spprograma.getSelectedItem().getId_programa());
                startActivity(intent);
            }
        });

        generar_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()){
                    Toasty.success(getContext(), "Permiso aceptado", Toasty.LENGTH_SHORT).show();
                    generarPDF();
                }else{
                    requestPermissions();
                    Toasty.error(getContext(), "Debes aceptar los permisos", Toasty.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    public void cargarProgramas(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/programas/listar_programa.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_programa = object.getInt("id_programa");
                        String nombre_programa = object.getString("nombre_programa").toString();
                        int id_eje = object.getInt("id_eje");
                        int id_subtema = object.getInt("id_subtema");
                        int id_estrategia = object.getInt("id_estrategia");
                        int id_centro_gestor = object.getInt("id_centro_gestor");

                        Programa programa = new Programa();
                        programa.setId_programa(id_programa);
                        programa.setNombre_programa(nombre_programa);
                        programa.setEjesId(id_eje);
                        programa.setSubtemasId(id_subtema);
                        programa.setEstrategiasId(id_estrategia);
                        programa.setCentrogestorId(id_centro_gestor);
                        listaProgramas.add(programa);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                spprograma.setItem(listaProgramas);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    public void cargarMatrizFin(){
        String  idprograma = Integer.toString(spprograma.getSelectedItem().getId_programa());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/mirfinproposito/listar_finproposito.php?id_programa="+idprograma+"&nombre_nivel=Fin", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_mir_fin_proposito = object.getInt("id_mir_fin_proposito");
                        String resumen_narrativo = object.getString("resumen_narrativo").toString();
                        String supuestos = object.getString("supuestos").toString();
                        String nombre_nivel = object.getString("nombre_nivel").toString();
                        int id_programa = object.getInt("id_programa");

                        Mirfinproposito mirfin = new Mirfinproposito();
                        mirfin.setIdmirfinproposito(id_mir_fin_proposito);
                        mirfin.setResumen_narrativo(resumen_narrativo);
                        mirfin.setSupuestos(supuestos);
                        mirfin.setNombre_nivel(nombre_nivel);
                        mirfin.setId_programa(id_programa);
                        listaMirFin.add(mirfin);

                        agregar_fin.setVisibility(View.GONE);
                        agregar_componente.setVisibility(View.VISIBLE);
                        agregar_actividad.setVisibility(View.VISIBLE);

                        cargarIndicadorFin();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerViewFin.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapterMirFin = new AdapterMirFin(getContext(), listaMirFin);
                recyclerViewFin.setAdapter(adapterMirFin);
                adapterMirFin.notifyDataSetChanged();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    public void cargarMatrizProposito(){
        String  idprograma = Integer.toString(spprograma.getSelectedItem().getId_programa());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/mirfinproposito/listar_finproposito.php?id_programa="+idprograma+"&nombre_nivel=Proposito", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_mir_fin_proposito = object.getInt("id_mir_fin_proposito");
                        String resumen_narrativo = object.getString("resumen_narrativo").toString();
                        String supuestos = object.getString("supuestos").toString();
                        String nombre_nivel = object.getString("nombre_nivel").toString();
                        int id_programa = object.getInt("id_programa");

                        Mirfinproposito mirproposito = new Mirfinproposito();
                        mirproposito.setIdmirfinproposito(id_mir_fin_proposito);
                        mirproposito.setResumen_narrativo(resumen_narrativo);
                        mirproposito.setSupuestos(supuestos);
                        mirproposito.setNombre_nivel(nombre_nivel);
                        mirproposito.setId_programa(id_programa);
                        listaMirProposito.add(mirproposito);

                        cargarIndicadoresProposito();

                        agregar_proposito.setVisibility(View.GONE);
                        agregar_componente.setVisibility(View.VISIBLE);
                        agregar_actividad.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerViewProposito.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapterMirProposito = new AdapterMirProposito(getContext(), listaMirProposito);
                recyclerViewProposito.setAdapter(adapterMirProposito);
                adapterMirProposito.notifyDataSetChanged();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    public void cargarMatrizComponente(){
        String  idprograma = Integer.toString(spprograma.getSelectedItem().getId_programa());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/mircomact/listar_comact.php?id_programa="+idprograma+"&nombre_nivel=Componente", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_mir_indicador_ca = object.getInt("id_mir_indicador_ca");
                        String resumen = object.getString("resumen_narrativo");
                        String supuestos = object.getString("supuestos");
                        String tipo_indicador = object.getString("tipo_indicador").toString();
                        String nombre_indicador = object.getString("nombre_indicador").toString();
                        String descripcion_indicador = object.getString("descripcion_indicador").toString();
                        String dimension = object.getString("dimension");
                        String frecuencia = object.getString("frecuencia");
                        String algoritmo = object.getString("algoritmo");
                        String variable_a = object.getString("variable_a");
                        String unidad_a = object.getString("unidad_a");
                        String variable_b = object.getString("variable_b");
                        String unidad_b = object.getString("unidad_b");
                        int valor_programado_a = object.getInt("valor_programado_a");
                        int valor_programado_b = object.getInt("valor_programado_b");
                        int meta_indicador = object.getInt("meta_indicador");
                        String medios_verificacion = object.getString("medios_verificacion");
                        int optimo_min = object.getInt("optimo_min");
                        int optimo_max = object.getInt("optimo_max");
                        int proceso_min = object.getInt("proceso_min");
                        int proceso_max = object.getInt("proceso_max");
                        int rezago_min = object.getInt("rezago_min");
                        int rezago_max = object.getInt("rezago_max");
                        String nombrenivel = object.getString("nombre_nivel");
                        String id_programa = object.getString("id_programa");

                        MirCompAct mirCompAct = new MirCompAct();
                        mirCompAct.setId_mir_indicador_ca(id_mir_indicador_ca);
                        mirCompAct.setResumen_narrativo(resumen);
                        mirCompAct.setSupuestos(supuestos);
                        mirCompAct.setTipo_indicador(tipo_indicador);
                        mirCompAct.setNombre_indicador(nombre_indicador);
                        mirCompAct.setDescripcion_indicador(descripcion_indicador);
                        mirCompAct.setDimension(dimension);
                        mirCompAct.setFrecuencia(frecuencia);
                        mirCompAct.setAlgoritmo(algoritmo);
                        mirCompAct.setVariable_a(variable_a);
                        mirCompAct.setUnidad_a(unidad_a);
                        mirCompAct.setVariable_b(variable_b);
                        mirCompAct.setUnidad_b(unidad_b);
                        mirCompAct.setValor_programado_a(valor_programado_a);
                        mirCompAct.setValor_programado_b(valor_programado_b);
                        mirCompAct.setMeta_indicador(meta_indicador);
                        mirCompAct.setMedios_verificacion(medios_verificacion);
                        mirCompAct.setMedios_verificacion(medios_verificacion);
                        mirCompAct.setOptimo_min(optimo_min);
                        mirCompAct.setOptimo_max(optimo_max);
                        mirCompAct.setProceso_min(proceso_min);
                        mirCompAct.setProceso_max(proceso_max);
                        mirCompAct.setRezago_min(rezago_min);
                        mirCompAct.setRezago_max(rezago_max);
                        mirCompAct.setNombre_nivel(nombrenivel);
                        mirCompAct.setId_programa(Integer.parseInt(id_programa));
                        listaComponente.add(mirCompAct);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerViewComponente.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapterMirComp = new AdapterMirComp(getContext(), listaComponente);
                recyclerViewComponente.setAdapter(adapterMirComp);
                adapterMirComp.notifyDataSetChanged();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    public void cargarMatrizActividad(){
        String  idprograma = Integer.toString(spprograma.getSelectedItem().getId_programa());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/mircomact/listar_comact.php?id_programa="+idprograma+"&nombre_nivel=Actividad", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_mir_indicador_ca = object.getInt("id_mir_indicador_ca");
                        String resumen = object.getString("resumen_narrativo");
                        String supuestos = object.getString("supuestos");
                        String tipo_indicador = object.getString("tipo_indicador").toString();
                        String nombre_indicador = object.getString("nombre_indicador").toString();
                        String descripcion_indicador = object.getString("descripcion_indicador").toString();
                        String dimension = object.getString("dimension");
                        String frecuencia = object.getString("frecuencia");
                        String algoritmo = object.getString("algoritmo");
                        String variable_a = object.getString("variable_a");
                        String unidad_a = object.getString("unidad_a");
                        String variable_b = object.getString("variable_b");
                        String unidad_b = object.getString("unidad_b");
                        int valor_programado_a = object.getInt("valor_programado_a");
                        int valor_programado_b = object.getInt("valor_programado_b");
                        int meta_indicador = object.getInt("meta_indicador");
                        String medios_verificacion = object.getString("medios_verificacion");
                        int optimo_min = object.getInt("optimo_min");
                        int optimo_max = object.getInt("optimo_max");
                        int proceso_min = object.getInt("proceso_min");
                        int proceso_max = object.getInt("proceso_max");
                        int rezago_min = object.getInt("rezago_min");
                        int rezago_max = object.getInt("rezago_max");
                        String nombrenivel = object.getString("nombre_nivel");
                        String id_programa = object.getString("id_programa");

                        MirCompAct mirCompAct = new MirCompAct();
                        mirCompAct.setId_mir_indicador_ca(id_mir_indicador_ca);
                        mirCompAct.setResumen_narrativo(resumen);
                        mirCompAct.setSupuestos(supuestos);
                        mirCompAct.setTipo_indicador(tipo_indicador);
                        mirCompAct.setNombre_indicador(nombre_indicador);
                        mirCompAct.setDescripcion_indicador(descripcion_indicador);
                        mirCompAct.setDimension(dimension);
                        mirCompAct.setFrecuencia(frecuencia);
                        mirCompAct.setAlgoritmo(algoritmo);
                        mirCompAct.setVariable_a(variable_a);
                        mirCompAct.setUnidad_a(unidad_a);
                        mirCompAct.setVariable_b(variable_b);
                        mirCompAct.setUnidad_b(unidad_b);
                        mirCompAct.setValor_programado_a(valor_programado_a);
                        mirCompAct.setValor_programado_b(valor_programado_b);
                        mirCompAct.setMeta_indicador(meta_indicador);
                        mirCompAct.setMedios_verificacion(medios_verificacion);
                        mirCompAct.setMedios_verificacion(medios_verificacion);
                        mirCompAct.setOptimo_min(optimo_min);
                        mirCompAct.setOptimo_max(optimo_max);
                        mirCompAct.setProceso_min(proceso_min);
                        mirCompAct.setProceso_max(proceso_max);
                        mirCompAct.setRezago_min(rezago_min);
                        mirCompAct.setRezago_max(rezago_max);
                        mirCompAct.setNombre_nivel(nombrenivel);
                        mirCompAct.setId_programa(Integer.parseInt(id_programa));
                        listaActividad.add(mirCompAct);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerViewActividad.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapterMirAct = new AdapterMirAct(getContext(), listaActividad);
                recyclerViewActividad.setAdapter(adapterMirAct);
                adapterMirAct.notifyDataSetChanged();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    public void cargarIndicadorFin(){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/mirindicador/listar_indicador.php?id_mir_fin_proposito="+ listaMirFin.get(0).getIdmirfinproposito(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_mir_indicador = object.getInt("id_mir_indicador");
                        String tipo_indicador = object.getString("tipo_indicador").toString();
                        String nombre_indicador = object.getString("nombre_indicador").toString();
                        String descripcion_indicador = object.getString("descripcion_indicador").toString();
                        String dimension = object.getString("dimension");
                        String frecuencia = object.getString("frecuencia");
                        String algoritmo = object.getString("algoritmo");
                        String variable_a = object.getString("variable_a");
                        String unidad_a = object.getString("unidad_a");
                        String variable_b = object.getString("variable_b");
                        String unidad_b = object.getString("unidad_b");
                        int valor_programado_a = object.getInt("valor_programado_a");
                        int valor_programado_b = object.getInt("valor_programado_b");
                        int meta_indicador = object.getInt("meta_indicador");
                        String medios_verificacion = object.getString("medios_verificacion");
                        int optimo_min = object.getInt("optimo_min");
                        int optimo_max = object.getInt("optimo_max");
                        int proceso_min = object.getInt("proceso_min");
                        int proceso_max = object.getInt("proceso_max");
                        int rezago_min = object.getInt("rezago_min");
                        int rezago_max = object.getInt("rezago_max");
                        int id_mir_fin_proposito = object.getInt("id_mir_fin_proposito");

                        MirIndicador mirIndicador = new MirIndicador();

                        mirIndicador.setId_mir_indicador(id_mir_indicador);
                        mirIndicador.setTipo_indicador(tipo_indicador);
                        mirIndicador.setNombre_indicador(nombre_indicador);
                        mirIndicador.setDescripcion_indicador(descripcion_indicador);
                        mirIndicador.setDimension(dimension);
                        mirIndicador.setFrecuencia(frecuencia);
                        mirIndicador.setAlgoritmo(algoritmo);
                        mirIndicador.setVariable_a(variable_a);
                        mirIndicador.setUnidad_a(unidad_a);
                        mirIndicador.setVariable_b(variable_b);
                        mirIndicador.setUnidad_b(unidad_b);
                        mirIndicador.setValor_programado_a(valor_programado_a);
                        mirIndicador.setValor_programado_b(valor_programado_b);
                        mirIndicador.setMeta_indicador(meta_indicador);
                        mirIndicador.setMedios_verificacion(medios_verificacion);
                        mirIndicador.setOptimo_min(optimo_min);
                        mirIndicador.setOptimo_max(optimo_max);
                        mirIndicador.setProceso_min(proceso_min);
                        mirIndicador.setProceso_max(proceso_max);
                        mirIndicador.setRezago_min(rezago_min);
                        mirIndicador.setRezago_max(rezago_max);
                        mirIndicador.setId_mir_fin_proposito(id_mir_fin_proposito);

                        listaIndicadoresFin.add(mirIndicador);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    public void cargarIndicadoresProposito(){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/mirindicador/listar_indicador.php?id_mir_fin_proposito="+ listaMirProposito.get(0).getIdmirfinproposito(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_mir_indicador = object.getInt("id_mir_indicador");
                        String tipo_indicador = object.getString("tipo_indicador").toString();
                        String nombre_indicador = object.getString("nombre_indicador").toString();
                        String descripcion_indicador = object.getString("descripcion_indicador").toString();
                        String dimension = object.getString("dimension");
                        String frecuencia = object.getString("frecuencia");
                        String algoritmo = object.getString("algoritmo");
                        String variable_a = object.getString("variable_a");
                        String unidad_a = object.getString("unidad_a");
                        String variable_b = object.getString("variable_b");
                        String unidad_b = object.getString("unidad_b");
                        int valor_programado_a = object.getInt("valor_programado_a");
                        int valor_programado_b = object.getInt("valor_programado_b");
                        int meta_indicador = object.getInt("meta_indicador");
                        String medios_verificacion = object.getString("medios_verificacion");
                        int optimo_min = object.getInt("optimo_min");
                        int optimo_max = object.getInt("optimo_max");
                        int proceso_min = object.getInt("proceso_min");
                        int proceso_max = object.getInt("proceso_max");
                        int rezago_min = object.getInt("rezago_min");
                        int rezago_max = object.getInt("rezago_max");
                        int id_mir_fin_proposito = object.getInt("id_mir_fin_proposito");

                        MirIndicador mirIndicador = new MirIndicador();

                        mirIndicador.setId_mir_indicador(id_mir_indicador);
                        mirIndicador.setTipo_indicador(tipo_indicador);
                        mirIndicador.setNombre_indicador(nombre_indicador);
                        mirIndicador.setDescripcion_indicador(descripcion_indicador);
                        mirIndicador.setDimension(dimension);
                        mirIndicador.setFrecuencia(frecuencia);
                        mirIndicador.setAlgoritmo(algoritmo);
                        mirIndicador.setVariable_a(variable_a);
                        mirIndicador.setUnidad_a(unidad_a);
                        mirIndicador.setVariable_b(variable_b);
                        mirIndicador.setUnidad_b(unidad_b);
                        mirIndicador.setValor_programado_a(valor_programado_a);
                        mirIndicador.setValor_programado_b(valor_programado_b);
                        mirIndicador.setMeta_indicador(meta_indicador);
                        mirIndicador.setMedios_verificacion(medios_verificacion);
                        mirIndicador.setOptimo_min(optimo_min);
                        mirIndicador.setOptimo_max(optimo_max);
                        mirIndicador.setProceso_min(proceso_min);
                        mirIndicador.setProceso_max(proceso_max);
                        mirIndicador.setRezago_min(rezago_min);
                        mirIndicador.setRezago_max(rezago_max);
                        mirIndicador.setId_mir_fin_proposito(id_mir_fin_proposito);

                        listaIndicadoresProposito.add(mirIndicador);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    public void generarPDF(){

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download";

            File dir = new File(path);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "MIR_" + spprograma.getSelectedItem().getNombre_programa() + ".pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            Document documento = new Document();
            documento.setPageSize(PageSize.A3.rotate());
            PdfWriter.getInstance(documento, fileOutputStream);

            documento.open();

            Paragraph tituloMatriz = new Paragraph("Matriz de indicadores para resultados", FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLACK));
            tituloMatriz.setAlignment(Element.ALIGN_CENTER);
            documento.add(tituloMatriz);

            documento.add(new Chunk(""));

            Paragraph Pcentrogestor = new Paragraph("Centro gestor", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE));
            Paragraph Peje = new Paragraph("Eje", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE));
            Paragraph Psubtema = new Paragraph("Subtema", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE));
            Paragraph Pestrategia = new Paragraph("Estrategia", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE));
            Paragraph Pprograma = new Paragraph("Programa", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE));

            PdfPCell cell;

            BaseColor colorGris = WebColors.getRGBColor("#cdcccd");
            BaseColor colorPantone = WebColors.getRGBColor("#9D2449");

            PdfPTable tablaDetalles = new PdfPTable(2);
            tablaDetalles.setWidthPercentage(100);
            tablaDetalles.setWidths(new int[]{1, 10});
            cell = new PdfPCell(Pcentrogestor);
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tablaDetalles.addCell(cell);
            tablaDetalles.addCell("Aqui va el centro gestor");

            cell = new PdfPCell(Peje);
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tablaDetalles.addCell(cell);
            tablaDetalles.addCell("Aqui va el eje");

            cell = new PdfPCell(Psubtema);
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tablaDetalles.addCell(cell);
            tablaDetalles.addCell("aUI VA EL SUBTEMA");

            cell = new PdfPCell(Pestrategia);
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tablaDetalles.addCell(cell);
            tablaDetalles.addCell("AQUI VA LA ESTRATEGIA");

            cell = new PdfPCell(Pprograma);
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tablaDetalles.addCell(cell);
            tablaDetalles.addCell("Aqui va el programa");
            documento.add(tablaDetalles);

            PdfPTable tabla = new PdfPTable(15);
            tabla.setSpacingBefore(20);
            tabla.setSpacingAfter(20);

            tabla.setWidthPercentage(100);

            cell = new PdfPCell(new Paragraph("Nivel", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setRowspan(2);
            cell.setPadding(5);
            cell.setBackgroundColor(colorPantone);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Resumen narrativo", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            cell.setRowspan(2);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Indicadores", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setColspan(12);
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Supuestos del indicador", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setRowspan(2);
            cell.setPadding(5);
            cell.setBackgroundColor(colorPantone);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Nombre", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Descripción", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Dimensión", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);


            cell = new PdfPCell(new Phrase("Tipo de indicador", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Algoritmo", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Valor programado A (Numerador)", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Unidad de medida A", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Valor programado B (Denominador)", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Unidad de medida B", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Meta del indicador", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Frecuencia", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Medios de verificación", FontFactory.getFont("arial", 12, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(colorPantone);
            cell.setPadding(5);
            tabla.addCell(cell);



            //Llenado indicadores FIN
            cell = new PdfPCell(new Phrase(listaMirFin.get(0).getNombre_nivel()));
            cell.setRowspan(listaIndicadoresFin.size());
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase(listaMirFin.get(0).getResumen_narrativo()));
            cell.setRowspan(listaIndicadoresFin.size());
            tabla.addCell(cell);

            for (int i = 0; i < listaIndicadoresFin.size(); i++){
                tabla.addCell(listaIndicadoresFin.get(i).getNombre_indicador());
                tabla.addCell(listaIndicadoresFin.get(i).getDescripcion_indicador());
                tabla.addCell(listaIndicadoresFin.get(i).getDimension());
                tabla.addCell(listaIndicadoresFin.get(i).getTipo_indicador());
                tabla.addCell(listaIndicadoresFin.get(i).getAlgoritmo());
                tabla.addCell(String.valueOf(listaIndicadoresFin.get(i).getValor_programado_a()));
                tabla.addCell(listaIndicadoresFin.get(i).getUnidad_a());
                tabla.addCell(String.valueOf(listaIndicadoresFin.get(i).getValor_programado_b()));
                tabla.addCell(listaIndicadoresFin.get(i).getUnidad_b());
                tabla.addCell(String.valueOf(listaIndicadoresFin.get(i).getMeta_indicador()));
                tabla.addCell(listaIndicadoresFin.get(i).getFrecuencia());
                tabla.addCell(listaIndicadoresFin.get(i).getMedios_verificacion());

                if (i == 0){
                    cell = new PdfPCell(new Phrase(listaMirFin.get(0).getSupuestos()));
                    cell.setRowspan(listaIndicadoresFin.size());
                    tabla.addCell(cell);
                }

            }

            //Llenado indicadores Proposito
            cell = new PdfPCell(new Phrase(listaMirProposito.get(0).getNombre_nivel()));
            cell.setRowspan(listaIndicadoresProposito.size());
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase(listaMirProposito.get(0).getResumen_narrativo()));
            cell.setRowspan(listaIndicadoresProposito.size());
            tabla.addCell(cell);

            for (int i = 0; i < listaIndicadoresProposito.size(); i++){
                tabla.addCell(listaIndicadoresProposito.get(i).getNombre_indicador());
                tabla.addCell(listaIndicadoresProposito.get(i).getDescripcion_indicador());
                tabla.addCell(listaIndicadoresProposito.get(i).getDimension());
                tabla.addCell(listaIndicadoresProposito.get(i).getTipo_indicador());
                tabla.addCell(listaIndicadoresProposito.get(i).getAlgoritmo());
                tabla.addCell(String.valueOf(listaIndicadoresProposito.get(i).getValor_programado_a()));
                tabla.addCell(listaIndicadoresProposito.get(i).getUnidad_a());
                tabla.addCell(String.valueOf(listaIndicadoresProposito.get(i).getValor_programado_b()));
                tabla.addCell(listaIndicadoresProposito.get(i).getUnidad_b());
                tabla.addCell(String.valueOf(listaIndicadoresProposito.get(i).getMeta_indicador()));
                tabla.addCell(listaIndicadoresProposito.get(i).getFrecuencia());
                tabla.addCell(listaIndicadoresProposito.get(i).getMedios_verificacion());

                if (i == 0){
                    cell = new PdfPCell(new Phrase(listaMirProposito.get(0).getSupuestos()));
                    cell.setRowspan(listaIndicadoresProposito.size());
                    tabla.addCell(cell);
                }
            }

            //Llenado componente
            for (int i = 0 ; i < listaComponente.size() ; i++) {
                tabla.addCell(listaComponente.get(i).getNombre_nivel());
                tabla.addCell(listaComponente.get(i).getResumen_narrativo());
                tabla.addCell(listaComponente.get(i).getNombre_indicador());
                tabla.addCell(listaComponente.get(i).getDescripcion_indicador());
                tabla.addCell(listaComponente.get(i).getDimension());
                tabla.addCell(listaComponente.get(i).getTipo_indicador());
                tabla.addCell(listaComponente.get(i).getAlgoritmo());
                tabla.addCell(String.valueOf(listaComponente.get(i).getValor_programado_a()));
                tabla.addCell(listaComponente.get(i).getUnidad_a());
                tabla.addCell(String.valueOf(listaComponente.get(i).getValor_programado_b()));
                tabla.addCell(listaComponente.get(i).getUnidad_b());
                tabla.addCell(String.valueOf(listaComponente.get(i).getMeta_indicador()));
                tabla.addCell(listaComponente.get(i).getFrecuencia());
                tabla.addCell(listaComponente.get(i).getMedios_verificacion());
                tabla.addCell(listaComponente.get(i).getSupuestos());
            }

            //Llenado actividades
            for (int i = 0 ; i < listaActividad.size(); i++) {
                tabla.addCell(listaActividad.get(i).getNombre_nivel());
                tabla.addCell(listaActividad.get(i).getResumen_narrativo());
                tabla.addCell(listaActividad.get(i).getNombre_indicador());
                tabla.addCell(listaActividad.get(i).getDescripcion_indicador());
                tabla.addCell(listaActividad.get(i).getDimension());
                tabla.addCell(listaActividad.get(i).getTipo_indicador());
                tabla.addCell(listaActividad.get(i).getAlgoritmo());
                tabla.addCell(String.valueOf(listaActividad.get(i).getValor_programado_a()));
                tabla.addCell(listaActividad.get(i).getUnidad_a());
                tabla.addCell(String.valueOf(listaActividad.get(i).getValor_programado_b()));
                tabla.addCell(listaActividad.get(i).getUnidad_b());
                tabla.addCell(String.valueOf(listaActividad.get(i).getMeta_indicador()));
                tabla.addCell(listaActividad.get(i).getFrecuencia());
                tabla.addCell(listaActividad.get(i).getMedios_verificacion());
                tabla.addCell(listaActividad.get(i).getSupuestos());
            }

            documento.add(tabla);

            documento.close();

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
            System.out.println(e);
        }catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 200) {
            if(grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage) {
                    Toast.makeText(getContext(), "Permiso concedido", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Permiso denegado", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

}