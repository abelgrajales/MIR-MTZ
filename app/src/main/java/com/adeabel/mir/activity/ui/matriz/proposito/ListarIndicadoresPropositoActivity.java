package com.adeabel.mir.activity.ui.matriz.proposito;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adeabel.mir.R;
import com.adeabel.mir.activity.ui.matriz.AdapterMirIndicador;
import com.adeabel.mir.activity.ui.matriz.AgregarIndicadorActivity;
import com.adeabel.mir.modelos.MirIndicador;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListarIndicadoresPropositoActivity extends AppCompatActivity {

    String idMirNivel, nivelMir;
    TextView nivel_mir;

    RecyclerView recyclerView;
    ArrayList<MirIndicador> listaIndicadores;
    AdapterMirIndicador adapterIndicadores;
    Button agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_indicadores_proposito);

        agregar = findViewById(R.id.agregar_indicador_proposito_button);
        listaIndicadores= new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerindicadoresproposito);

        Intent intent = getIntent();
        idMirNivel = Integer.toString(intent.getExtras().getInt("id"));
        nivelMir = intent.getExtras().getString("nivel");

        cargarIndicador();

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AgregarIndicadorActivity.class).putExtra("id", idMirNivel);
                startActivity(intent);
            }
        });
    }

    public void cargarIndicador(){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/mirindicador/listar_indicador.php?id_mir_fin_proposito="+idMirNivel, null, new Response.Listener<JSONArray>() {
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

                        listaIndicadores.add(mirIndicador);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapterIndicadores = new AdapterMirIndicador(getApplicationContext(), listaIndicadores);
                recyclerView.setAdapter(adapterIndicadores);
                adapterIndicadores.notifyDataSetChanged();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}