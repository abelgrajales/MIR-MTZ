package com.adeabel.mir.activity.ui.matriz;

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
import com.adeabel.mir.activity.ui.matriz.fin.ListarIndicadoresFinActivity;
import com.adeabel.mir.modelos.MirIndicador;
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

import es.dmoral.toasty.Toasty;

public class AdapterMirIndicador extends RecyclerView.Adapter<AdapterMirIndicador.ViewHolder>{
    LayoutInflater inflater;
    ArrayList<MirIndicador> mirindicador;

    public AdapterMirIndicador(Context ctx, ArrayList<MirIndicador> mirindicador){
        this.inflater = LayoutInflater.from(ctx);
        this.mirindicador = mirindicador;
    }

    @NonNull
    @Override
    public AdapterMirIndicador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.indicadores_list, parent,false);
        return  new AdapterMirIndicador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMirIndicador.ViewHolder holder, int position) {
        String nombre = mirindicador.get(position).getNombre_indicador();
        String id = Integer.toString(mirindicador.get(position).getId_mir_indicador());
        holder.nombre.setText(nombre);
    }

    @Override
    public int getItemCount() {
        return mirindicador.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        Button abrirDetalles, borrarIndicador;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tv_listar_indicador);
            cardView = itemView.findViewById(R.id.cardview_indicadores);
            abrirDetalles = itemView.findViewById(R.id.btn_detalles_indicador);
            borrarIndicador = itemView.findViewById(R.id.btn_borrar_indicador);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            abrirDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetallesIndicadorActivity.class)
                            .putExtra("idfin", mirindicador.get(getAdapterPosition()).getId_mir_indicador())
                            .putExtra("tipo", mirindicador.get(getAdapterPosition()).getTipo_indicador())
                            .putExtra("nombre", mirindicador.get(getAdapterPosition()).getNombre_indicador())
                            .putExtra("descripcion", mirindicador.get(getAdapterPosition()).getDescripcion_indicador())
                            .putExtra("dimension", mirindicador.get(getAdapterPosition()).getDimension())
                            .putExtra("frecuencia", mirindicador.get(getAdapterPosition()).getFrecuencia())
                            .putExtra("algoritmo", mirindicador.get(getAdapterPosition()).getAlgoritmo())
                            .putExtra("variablea", mirindicador.get(getAdapterPosition()).getVariable_a())
                            .putExtra("unidada", mirindicador.get(getAdapterPosition()).getUnidad_a())
                            .putExtra("variableb", mirindicador.get(getAdapterPosition()).getVariable_b())
                            .putExtra("unidadb", mirindicador.get(getAdapterPosition()).getUnidad_b())
                            .putExtra("valorprogramadoa", mirindicador.get(getAdapterPosition()).getValor_programado_a())
                            .putExtra("valorprogramadob", mirindicador.get(getAdapterPosition()).getValor_programado_b())
                            .putExtra("metaindicador", mirindicador.get(getAdapterPosition()).getMeta_indicador())
                            .putExtra("medios", mirindicador.get(getAdapterPosition()).getMedios_verificacion())
                            .putExtra("rezagomin", mirindicador.get(getAdapterPosition()).getRezago_min())
                            .putExtra("rezagomax", mirindicador.get(getAdapterPosition()).getRezago_max())
                            .putExtra("procesomin", mirindicador.get(getAdapterPosition()).getProceso_min())
                            .putExtra("procesomax", mirindicador.get(getAdapterPosition()).getProceso_max())
                            .putExtra("optimomin", mirindicador.get(getAdapterPosition()).getOptimo_min())
                            .putExtra("optimomax", mirindicador.get(getAdapterPosition()).getOptimo_max());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            view.getContext().startActivity(intent);

                }
            });

            borrarIndicador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borrarIndicador( String.valueOf(mirindicador.get(getAdapterPosition()).getId_mir_indicador()), view);
                    mirindicador.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

        }
    }

    private void borrarIndicador(String id, View view) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/mirindicador/borrar_indicador.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("datos eliminados")){
                    Toasty.success(view.getContext(), "Indicador eliminado", Toasty.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(view.getContext(), "Ocurrio un error", Toasty.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_mir_indicador", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);

    }
}
