package com.adeabel.mir.activity.ui.centrogestor;

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
import com.adeabel.mir.modelos.CentroGestor;
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

public class ListarCentroGestorFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<CentroGestor> listaCentroGestor;
    AdapterCentroGestor adapterCentroGestor;
    Button agregar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_centro_gestor, container, false);

        recyclerView = view.findViewById(R.id.recyclercentrogestor);

        listaCentroGestor = new ArrayList<>();

        agregar = view.findViewById(R.id.agregar_centro_gestor_button);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AgregarCentroGestorActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        cargarEjes();
        super.onResume();
    }

    @Override
    public void onPause() {
        listaCentroGestor.clear();
        super.onPause();
    }

    public void cargarEjes(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/centrogestor/listar_centro_gestor.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_centro_gestor = object.getInt("id_centro_gestor");
                        String nombre = object.getString("nombre_centro_gestor").toString();
                        System.out.println(nombre);

                        CentroGestor centroGestor = new CentroGestor(id_centro_gestor, nombre);
                        centroGestor.setId_centro_gestor(id_centro_gestor);
                        centroGestor.setNombre_centro_gestor(nombre);
                        listaCentroGestor.add(centroGestor);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapterCentroGestor = new AdapterCentroGestor(getContext(), listaCentroGestor);
                recyclerView.setAdapter(adapterCentroGestor);
                adapterCentroGestor.notifyDataSetChanged();

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