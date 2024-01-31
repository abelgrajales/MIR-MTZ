package com.adeabel.mir.activity.ui.subtema;

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

public class ListarSubtemasFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Subtemas> listaSubtemas;
    AdapterSubtemas adapterSubtemas;
    Button agregar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_subtemas, container, false);

        recyclerView = view.findViewById(R.id.recyclersubtemas);

        listaSubtemas = new ArrayList<>();

        agregar = view.findViewById(R.id.agregar_subtemas_button);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AgregarSubtemasActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        cargarSubtemas();
        super.onResume();
    }

    @Override
    public void onPause() {
        listaSubtemas.clear();
        super.onPause();
    }

    public void cargarSubtemas(){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://adeabel.000webhostapp.com/mir/subtemas/listar_subtema.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        int id_subtema = object.getInt("id_subtema");
                        String nombre_subtema = object.getString("nombre_subtema").toString();
                        System.out.println(nombre_subtema);

                        Subtemas subtemas = new Subtemas(id_subtema, nombre_subtema);
                        subtemas.setId_subtema(id_subtema);
                        subtemas.setNombre_subtema(nombre_subtema);
                        listaSubtemas.add(subtemas);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                adapterSubtemas = new AdapterSubtemas(getContext(), listaSubtemas);
                recyclerView.setAdapter(adapterSubtemas);
                adapterSubtemas.notifyDataSetChanged();

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