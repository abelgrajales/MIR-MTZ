package com.adeabel.mir.activity.ui.subtema;

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

public class ActualizarSubtemasActivity extends AppCompatActivity {

    TextInputLayout nombre_subtema;
    Button actualizar_subtema;
    String id;
    String nombreSubtema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_subtemas);

        nombre_subtema = findViewById(R.id.ti_nombre_subtema_actualizar);
        actualizar_subtema = findViewById(R.id.btn_actualizar_subtema);

        Intent intent = getIntent();
        id = Integer.toString(intent.getExtras().getInt("id"));
        nombreSubtema = intent.getExtras().getString("nombre");
        nombre_subtema.getEditText().setText(nombreSubtema);


        actualizar_subtema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarSubtema(view);
            }
        });
    }

    public void actualizarSubtema(View view){
        String nombreActualizado = nombre_subtema.getEditText().getText().toString();
        System.out.println(nombreActualizado);

        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/subtemas/actualizar_subtema.php", new Response.Listener<String>() {
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
                params.put("id_subtema", id);
                params.put("nombre_subtema", nombreActualizado);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}