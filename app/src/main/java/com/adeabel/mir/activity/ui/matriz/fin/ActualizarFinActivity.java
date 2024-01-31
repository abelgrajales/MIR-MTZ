package com.adeabel.mir.activity.ui.matriz.fin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class ActualizarFinActivity extends AppCompatActivity {

    TextInputLayout resumen, supuestos, nombre_nivel;
    Button actualizar_nivel;
    TextView tv_nombre_nivel;

    String nivel, nombre_programa;
    int idprograma, idnivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_fin);

        Intent intent = getIntent();
        idnivel = intent.getExtras().getInt("id");
        nivel = intent.getExtras().getString("nivel");
        nombre_programa = intent.getExtras().getString("programa");
        idprograma = intent.getExtras().getInt("idprograma");


        resumen = findViewById(R.id.ti_resumen_narrativo_actualizar);
        supuestos = findViewById(R.id.ti_supuestos_actualizar);

        resumen.getEditText().setText(intent.getExtras().getString("resumen"));
        supuestos.getEditText().setText(intent.getExtras().getString("supuestos"));

        nombre_nivel = findViewById(R.id.ti_nivel_mir_actualizar);

        nombre_nivel.getEditText().setText(nivel);


        actualizar_nivel = findViewById(R.id.btn_actualizar_nivel_fin);
        tv_nombre_nivel = findViewById(R.id.tv_nivel_nombre);
        tv_nombre_nivel.setText("Nivel " + nivel);

        actualizar_nivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarFin(view);
            }
        });
    }

    public void actualizarFin(View view){
        final String resumen_narrativo = resumen.getEditText().getText().toString();
        final String supuestos1 = supuestos.getEditText().getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/mirfinproposito/actualizar_mirfinproposito.php", new Response.Listener<String>() {
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
                params.put("id_mir_fin_proposito", String.valueOf(idnivel));
                params.put("resumen_narrativo", resumen_narrativo);
                params.put("supuestos", supuestos1);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}