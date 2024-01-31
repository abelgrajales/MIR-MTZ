package com.adeabel.mir.activity.ui.ejes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adeabel.mir.R;
import com.adeabel.mir.modelos.Ejes;
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

public class ListarEjesFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Ejes> listaEjes;
    AdapterEjes adapterEjes;
    Button agregar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejes, container, false);

        recyclerView = view.findViewById(R.id.recyclerejes);

        listaEjes = new ArrayList<>();

        agregar = view.findViewById(R.id.agregar_ejes_button);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Agregar_Ejes_Activity.class);
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
        listaEjes.clear();
        super.onPause();
    }

    public void cargarEjes(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/ejes/listar_eje.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_eje = object.getInt("id_eje");
                        String nombre = object.getString("nombre_eje").toString();
                        System.out.println(nombre);

                        Ejes ejes = new Ejes(id_eje, nombre);
                        ejes.setId_eje(id_eje);
                        ejes.setNombre_eje(nombre);
                        listaEjes.add(ejes);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapterEjes = new AdapterEjes(getContext(), listaEjes);
                recyclerView.setAdapter(adapterEjes);
                adapterEjes.notifyDataSetChanged();

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