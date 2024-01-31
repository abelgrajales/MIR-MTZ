package com.adeabel.mir.activity.ui.matriz.componente;

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
import com.adeabel.mir.activity.ui.matriz.DetallesIndicadorActivity;
import com.adeabel.mir.activity.ui.matriz.fin.ActualizarFinActivity;
import com.adeabel.mir.activity.ui.matriz.fin.ListarIndicadoresFinActivity;
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

public class AdapterMirComp extends RecyclerView.Adapter<AdapterMirComp.ViewHolder>{
    LayoutInflater inflater;
    ArrayList<MirCompAct> mirComp;

    public AdapterMirComp(Context ctx, ArrayList<MirCompAct> mirComp){
        this.inflater = LayoutInflater.from(ctx);
        this.mirComp = mirComp;
    }

    @NonNull
    @Override
    public AdapterMirComp.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.mircomp_list, parent,false);
        return  new AdapterMirComp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMirComp.ViewHolder holder, int position) {
        String resumen = mirComp.get(position).getResumen_narrativo();
        String indicador = mirComp.get(position).getNombre_indicador();
        String id = Integer.toString(mirComp.get(position).getId_mir_indicador_ca());
        holder.resumen.setText(resumen);
        holder.indicador.setText(indicador);
    }

    @Override
    public int getItemCount() {
        return mirComp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView resumen;
        TextView indicador;
        Button abrirDetalles, borrarNivel;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resumen = itemView.findViewById(R.id.tv_resumen_comp);
            indicador = itemView.findViewById(R.id.tv_indicador_comp);
            cardView = itemView.findViewById(R.id.cardview_mir_comp);
            abrirDetalles = itemView.findViewById(R.id.btn_detalles_comp);
            borrarNivel = itemView.findViewById(R.id.btn_borrar_comp);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            abrirDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetallesCompActActivity.class)
                            .putExtra("id", mirComp.get(getAdapterPosition()).getId_mir_indicador_ca())
                            .putExtra("resumen", mirComp.get(getAdapterPosition()).getResumen_narrativo())
                            .putExtra("supuestos", mirComp.get(getAdapterPosition()).getSupuestos())
                            .putExtra("tipo", mirComp.get(getAdapterPosition()).getTipo_indicador())
                            .putExtra("nombre", mirComp.get(getAdapterPosition()).getNombre_indicador())
                            .putExtra("descripcion", mirComp.get(getAdapterPosition()).getDescripcion_indicador())
                            .putExtra("dimension", mirComp.get(getAdapterPosition()).getDimension())
                            .putExtra("frecuencia", mirComp.get(getAdapterPosition()).getFrecuencia())
                            .putExtra("algoritmo", mirComp.get(getAdapterPosition()).getAlgoritmo())
                            .putExtra("variablea", mirComp.get(getAdapterPosition()).getVariable_a())
                            .putExtra("unidada", mirComp.get(getAdapterPosition()).getUnidad_a())
                            .putExtra("variableb", mirComp.get(getAdapterPosition()).getVariable_b())
                            .putExtra("unidadb", mirComp.get(getAdapterPosition()).getUnidad_b())
                            .putExtra("valorprogramadoa", mirComp.get(getAdapterPosition()).getValor_programado_a())
                            .putExtra("valorprogramadob", mirComp.get(getAdapterPosition()).getValor_programado_b())
                            .putExtra("metaindicador", mirComp.get(getAdapterPosition()).getMeta_indicador())
                            .putExtra("medios", mirComp.get(getAdapterPosition()).getMedios_verificacion())
                            .putExtra("rezagomin", mirComp.get(getAdapterPosition()).getRezago_min())
                            .putExtra("rezagomax", mirComp.get(getAdapterPosition()).getRezago_max())
                            .putExtra("procesomin", mirComp.get(getAdapterPosition()).getProceso_min())
                            .putExtra("procesomax", mirComp.get(getAdapterPosition()).getProceso_max())
                            .putExtra("optimomin", mirComp.get(getAdapterPosition()).getOptimo_min())
                            .putExtra("optimomax", mirComp.get(getAdapterPosition()).getOptimo_max())
                            .putExtra("nivel", mirComp.get(getAdapterPosition()).getNombre_nivel());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    view.getContext().startActivity(intent);
                }
            });

            borrarNivel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borrarActividad(String.valueOf(mirComp.get(getAdapterPosition()).getId_mir_indicador_ca()), view);
                    mirComp.remove(getAdapterPosition());
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
