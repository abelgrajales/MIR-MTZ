package com.adeabel.mir.activity.ui.matriz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import es.dmoral.toasty.Toasty;

public class AgregarNivelMirActivity extends AppCompatActivity {

    TextInputLayout resumen, supuestos, nombre_nivel;
    Button agregar_nivel;
    TextView tv_programa;

    String nivel, nombre_programa;
    int idprograma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nivel_mir);

        Intent intent = getIntent();
        nivel = intent.getExtras().getString("nivel");
        nombre_programa = intent.getExtras().getString("programa");
        idprograma = intent.getExtras().getInt("idprograma");

        tv_programa = findViewById(R.id.tv_mir_nombre_programa);
        tv_programa.setText("Programa " + nombre_programa);

        resumen = findViewById(R.id.ti_resumen_narrativo);
        supuestos = findViewById(R.id.ti_supuestos);
        nombre_nivel = findViewById(R.id.ti_nivel_mir);

        nombre_nivel.getEditText().setText(nivel);

        agregar_nivel = findViewById(R.id.btn_guardar_nivel);


        agregar_nivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos(view);
            }
        });

    }

    private void insertarDatos(View view) {
        final String resumen_narrativo = resumen.getEditText().getText().toString();
        final String supuestos1 = supuestos.getEditText().getText().toString();
        final String nombrenivel = nombre_nivel.getEditText().getText().toString();
        final String id_programa = String.valueOf(idprograma);

        if(resumen_narrativo.isEmpty() || supuestos1.isEmpty()){
            Toasty.error(getApplicationContext(), "Ingrese todos los datos", Toasty.LENGTH_LONG, true).show();
            return;
        }else {
            StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/mirfinproposito/insertar_mirfinproposito.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Agregado con exito").setConfirmClickListener("Listo", new KAlertDialog.KAlertClickListener() {
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
                    params.put("resumen_narrativo", resumen_narrativo);
                    params.put("supuestos", supuestos1);
                    params.put("nombre_nivel", nombrenivel);
                    params.put("id_programa", id_programa);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }
}