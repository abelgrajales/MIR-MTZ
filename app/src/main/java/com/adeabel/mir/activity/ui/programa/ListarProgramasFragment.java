package com.adeabel.mir.activity.ui.programa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.adeabel.mir.R;
import com.adeabel.mir.activity.ui.subtema.AdapterSubtemas;
import com.adeabel.mir.activity.ui.subtema.AgregarSubtemasActivity;
import com.adeabel.mir.modelos.Programa;
import com.adeabel.mir.modelos.Subtemas;
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

public class ListarProgramasFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<Programa> listaProgramas;
    AdapterProgramas adapterProgramas;
    Button agregar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_programas, container, false);

        recyclerView = view.findViewById(R.id.recyclerprogramas);

        listaProgramas = new ArrayList<>();

        agregar = view.findViewById(R.id.agregar_programas_button);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AgregarProgramasActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        cargarProgramas();
        super.onResume();
    }

    @Override
    public void onPause() {
        listaProgramas.clear();
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

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapterProgramas = new AdapterProgramas(getContext(), listaProgramas);
                recyclerView.setAdapter(adapterProgramas);
                adapterProgramas.notifyDataSetChanged();

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
}