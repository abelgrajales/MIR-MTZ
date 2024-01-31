package com.adeabel.mir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adeabel.mir.activity.InicioActivity;
import com.adeabel.mir.activity.ui.subtema.AdapterSubtemas;
import com.adeabel.mir.modelos.Programa;
import com.adeabel.mir.modelos.Subtemas;
import com.adeabel.mir.modelos.Usuarios;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout correo, contraseña;
    Button iniciosesion;
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciosesion = findViewById(R.id.btn_incio_sesion);
        correo = findViewById(R.id.ti_correo_usuario);
        contraseña = findViewById(R.id.ti_contraseña_usuario);

        mPref = getApplicationContext().getSharedPreferences("tipoUsuario", MODE_PRIVATE);
        String usuarioSeleccionado = mPref.getString("user", "");

        Toasty.success(this, "VALOR SELECCIONADO" + usuarioSeleccionado, Toasty.LENGTH_SHORT).show();

        iniciosesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usuarioSeleccionado.equals("Usuario")){
                    validarUsuario();
                }else{
                    validarAdministrador();
                }

                //Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                //startActivity(intent);
            }
        });
    }

    public void validarUsuario() {
        //Toasty.success(getApplicationContext(), "DISTE CLIC", Toasty.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/usuario/validar_usuario.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("datos validos")){
                    Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                    startActivity(intent);
                }else{
                    Toasty.error(getApplicationContext(), "Usuario o contraseña incorrectos", Toasty.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.success(getApplicationContext(), error.toString(), Toasty.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo_usuario", correo.getEditText().getText().toString());
                parametros.put("contraseña_usuario", contraseña.getEditText().getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void validarAdministrador() {
        //Toasty.success(getApplicationContext(), "DISTE CLIC", Toasty.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/usuario/validar_administrador.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("datos validos")){
                    Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                    startActivity(intent);
                }else{
                    Toasty.error(getApplicationContext(), "Usuario o contraseña incorrectos", Toasty.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.success(getApplicationContext(), error.toString(), Toasty.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo_administrador", correo.getEditText().getText().toString());
                parametros.put("contraseña_administrador", contraseña.getEditText().getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}