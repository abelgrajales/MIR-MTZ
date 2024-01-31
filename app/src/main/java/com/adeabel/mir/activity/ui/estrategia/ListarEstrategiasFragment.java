package com.adeabel.mir.activity.ui.estrategia;

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
import android.widget.Toast;

import com.adeabel.mir.R;
import com.adeabel.mir.modelos.Estrategias;
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

public class ListarEstrategiasFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Estrategias> listaEstrategias;
    AdapterEstrategias adapterEstrategias;
    Button agregar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_estrategias, container, false);

        recyclerView = view.findViewById(R.id.recyclerestrategias);

        listaEstrategias = new ArrayList<>();

        agregar = view.findViewById(R.id.agregar_estrategias_button);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AgregarEstrategiasActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        cargarEstrategias();
        super.onResume();
    }

    @Override
    public void onPause() {
        listaEstrategias.clear();
        super.onPause();
    }

    public void cargarEstrategias(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/estrategias/listar_estrategia.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_estrategia = object.getInt("id_estrategia");
                        String nombre_estrategia = object.getString("nombre_estrategia").toString();

                        Estrategias estrategias = new Estrategias(id_estrategia, nombre_estrategia);
                        estrategias.setId_estrategia(id_estrategia);
                        estrategias.setNombre_estrategia(nombre_estrategia);
                        listaEstrategias.add(estrategias);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapterEstrategias = new AdapterEstrategias(getContext(), listaEstrategias);
                recyclerView.setAdapter(adapterEstrategias);
                adapterEstrategias.notifyDataSetChanged();

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