package com.adeabel.mir.activity.ui.matriz.actividad;

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

import com.adeabel.mir.R;
import com.adeabel.mir.activity.ui.matriz.DetallesCompActActivity;
import com.adeabel.mir.modelos.MirCompAct;
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

public class AdapterMirAct extends RecyclerView.Adapter<AdapterMirAct.ViewHolder>{
    LayoutInflater inflater;
    ArrayList<MirCompAct> mirAct;

    public AdapterMirAct(Context ctx, ArrayList<MirCompAct> mirAct){
        this.inflater = LayoutInflater.from(ctx);
        this.mirAct = mirAct;
    }

    @NonNull
    @Override
    public AdapterMirAct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.miract_list, parent,false);
        return  new AdapterMirAct.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMirAct.ViewHolder holder, int position) {
        String resumen = mirAct.get(position).getResumen_narrativo();
        String indicador = mirAct.get(position).getNombre_indicador();
        String id = Integer.toString(mirAct.get(position).getId_mir_indicador_ca());
        holder.resumen.setText(resumen);
        holder.indicador.setText(indicador);
    }

    @Override
    public int getItemCount() {
        return mirAct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView resumen;
        TextView indicador;
        Button abrirDetalles, borrarNivel;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resumen = itemView.findViewById(R.id.tv_resumen_act);
            indicador = itemView.findViewById(R.id.tv_indicador_act);
            cardView = itemView.findViewById(R.id.cardview_mir_act);
            abrirDetalles = itemView.findViewById(R.id.btn_detalles_act);
            borrarNivel = itemView.findViewById(R.id.btn_borrar_act);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            abrirDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetallesCompActActivity.class)
                            .putExtra("id", mirAct.get(getAdapterPosition()).getId_mir_indicador_ca())
                            .putExtra("resumen", mirAct.get(getAdapterPosition()).getResumen_narrativo())
                            .putExtra("supuestos", mirAct.get(getAdapterPosition()).getSupuestos())
                            .putExtra("tipo", mirAct.get(getAdapterPosition()).getTipo_indicador())
                            .putExtra("nombre", mirAct.get(getAdapterPosition()).getNombre_indicador())
                            .putExtra("descripcion", mirAct.get(getAdapterPosition()).getDescripcion_indicador())
                            .putExtra("dimension", mirAct.get(getAdapterPosition()).getDimension())
                            .putExtra("frecuencia", mirAct.get(getAdapterPosition()).getFrecuencia())
                            .putExtra("algoritmo", mirAct.get(getAdapterPosition()).getAlgoritmo())
                            .putExtra("variablea", mirAct.get(getAdapterPosition()).getVariable_a())
                            .putExtra("unidada", mirAct.get(getAdapterPosition()).getUnidad_a())
                            .putExtra("variableb", mirAct.get(getAdapterPosition()).getVariable_b())
                            .putExtra("unidadb", mirAct.get(getAdapterPosition()).getUnidad_b())
                            .putExtra("valorprogramadoa", mirAct.get(getAdapterPosition()).getValor_programado_a())
                            .putExtra("valorprogramadob", mirAct.get(getAdapterPosition()).getValor_programado_b())
                            .putExtra("metaindicador", mirAct.get(getAdapterPosition()).getMeta_indicador())
                            .putExtra("medios", mirAct.get(getAdapterPosition()).getMedios_verificacion())
                            .putExtra("rezagomin", mirAct.get(getAdapterPosition()).getRezago_min())
                            .putExtra("rezagomax", mirAct.get(getAdapterPosition()).getRezago_max())
                            .putExtra("procesomin", mirAct.get(getAdapterPosition()).getProceso_min())
                            .putExtra("procesomax", mirAct.get(getAdapterPosition()).getProceso_max())
                            .putExtra("optimomin", mirAct.get(getAdapterPosition()).getOptimo_min())
                            .putExtra("optimomax", mirAct.get(getAdapterPosition()).getOptimo_max())
                            .putExtra("nivel", mirAct.get(getAdapterPosition()).getNombre_nivel());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    view.getContext().startActivity(intent);
                }
            });

            borrarNivel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borrarActividad(String.valueOf(mirAct.get(getAdapterPosition()).getId_mir_indicador_ca()), view);
                    mirAct.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

        }
    }

    private void borrarActividad(String id, View view) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/mircomact/borrar_comact.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("datos eliminados")){
                    new KAlertDialog(view.getContext(), KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Eliminado con exito").setConfirmClickListener("Listo", new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    kAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                }

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
                params.put("id_mir_indicador_ca", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);

    }
}
