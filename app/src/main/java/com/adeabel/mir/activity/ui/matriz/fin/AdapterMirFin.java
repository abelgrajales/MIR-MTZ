package com.adeabel.mir.activity.ui.matriz.fin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adeabel.mir.MainActivity;
import com.adeabel.mir.R;
import com.adeabel.mir.activity.InicioActivity;
import com.adeabel.mir.modelos.Mirfinproposito;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.kalert.KAlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterMirFin extends RecyclerView.Adapter<AdapterMirFin.ViewHolder>{
    LayoutInflater inflater;
    ArrayList<Mirfinproposito> mirfin;

    public AdapterMirFin(Context ctx, ArrayList<Mirfinproposito> mirfin){
        this.inflater = LayoutInflater.from(ctx);
        this.mirfin = mirfin;
    }

    @NonNull
    @Override
    public AdapterMirFin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mirfin_list, parent,false);
        return  new AdapterMirFin.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMirFin.ViewHolder holder, int position) {
        String resumen = mirfin.get(position).getResumen_narrativo();
        String supuestos = mirfin.get(position).getSupuestos();
        String id = Integer.toString(mirfin.get(position).getIdmirfinproposito());
        holder.resumen.setText(resumen);
        holder.supuestos.setText(supuestos);
    }

    @Override
    public int getItemCount() {
        return mirfin.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView resumen;
        TextView supuestos;
        Button abrirIndicadores, editarNivelFin;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resumen = itemView.findViewById(R.id.tv_resumen_fin);
            supuestos = itemView.findViewById(R.id.tv_supuestos_fin);
            cardView = itemView.findViewById(R.id.cardview_mir_fin);
            abrirIndicadores = itemView.findViewById(R.id.btn_abrir_indicadores_fin);
            editarNivelFin = itemView.findViewById(R.id.btn_editar_fin);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            abrirIndicadores.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext().getApplicationContext(), ListarIndicadoresFinActivity.class).putExtra("id", mirfin.get(getAdapterPosition()).getIdmirfinproposito())
                            .putExtra("nivel", "Fin");
                    view.getContext().startActivity(intent);
                }
            });

            editarNivelFin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ActualizarFinActivity.class).putExtra("id", mirfin.get(getAdapterPosition()).getIdmirfinproposito())
                            .putExtra("nivel", "Fin")
                            .putExtra("resumen", mirfin.get(getAdapterPosition()).getResumen_narrativo())
                            .putExtra("supuestos", mirfin.get(getAdapterPosition()).getSupuestos());
                    view.getContext().startActivity(intent);
                }
            });

        }
    }

    private void borrarPrograma(String id, View view) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/programas/borrar_programa.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("datos eliminados")){

                }
                new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Eliminado con exito").setConfirmClickListener("Listo", new KAlertDialog.KAlertClickListener() {
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
                params.put("id_programa", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);

    }
}
