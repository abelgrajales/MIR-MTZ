package com.adeabel.mir.activity.ui.centrogestor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.adeabel.mir.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class ActualizarCentroGestorActivity extends AppCompatActivity {

    TextInputLayout nombre_centro_gestor;
    Button actualizar_centro_gestor;
    String  id;
    String nombreCentroGestor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_centro_gestor);

        nombre_centro_gestor = findViewById(R.id.ti_nombre_centro_gestor_actualizar);
        actualizar_centro_gestor = findViewById(R.id.btn_actualizar_centro_gestor);

        Intent intent = getIntent();
        id = Integer.toString(intent.getExtras().getInt("id"));
        nombreCentroGestor = intent.getExtras().getString("nombre");
        nombre_centro_gestor.getEditText().setText(nombreCentroGestor);


        actualizar_centro_gestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarCentroGestor(view);
            }
        });
    }

    public void actualizarCentroGestor(View view){
        String nombreActualizado = nombre_centro_gestor.getEditText().getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/centrogestor/actualizar_centro_gestor.php", new Response.Listener<String>() {
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
                params.put("id_centro_gestor", id);
                params.put("nombre_centro_gestor", nombreActualizado);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}