package com.adeabel.mir.activity.ui.programa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adeabel.mir.R;
import com.adeabel.mir.activity.ui.subtema.ActualizarSubtemasActivity;
import com.adeabel.mir.modelos.Programa;
import com.adeabel.mir.modelos.Subtemas;
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

public class AdapterProgramas extends RecyclerView.Adapter<com.adeabel.mir.activity.ui.programa.AdapterProgramas.ViewHolder>{
    LayoutInflater inflater;
    ArrayList<Programa> programas;

    public AdapterProgramas(Context ctx, ArrayList<Programa> programas){
        this.inflater = LayoutInflater.from(ctx);
        this.programas = programas;
    }

    @NonNull
    @Override
    public com.adeabel.mir.activity.ui.programa.AdapterProgramas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.programas_list, parent,false);
        return  new com.adeabel.mir.activity.ui.programa.AdapterProgramas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.adeabel.mir.activity.ui.programa.AdapterProgramas.ViewHolder holder, int position) {
        String nombre = programas.get(position).getNombre_programa();
        String id = Integer.toString(programas.get(position).getId_programa());
        holder.nombrePrograma.setText(nombre);
    }

    @Override
    public int getItemCount() {
        return programas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombrePrograma;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombrePrograma = itemView.findViewById(R.id.tv_listar_programas);
            cardView = itemView.findViewById(R.id.cardview_programa);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence[] dialogItems = {"Editar", "Borrar"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                    dialog.setTitle(programas.get(getAdapterPosition()).getNombre_programa()).setItems(dialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case 0:
                                    Intent intent = new Intent(view.getContext().getApplicationContext(), ActualizarProgramasActivity.class)
                                            .putExtra("nombre", programas.get(getAdapterPosition()).nombre_programa)
                                            .putExtra("id", programas.get(getAdapterPosition()).id_programa)
                                            .putExtra("id_eje", programas.get(getAdapterPosition()).getEjesId())
                                            .putExtra("id_subtema", programas.get(getAdapterPosition()).getSubtemasId())
                                            .putExtra("id_estrategia", programas.get(getAdapterPosition()).getEstrategiasId())
                                            .putExtra("id_centro_gestor", programas.get(getAdapterPosition()).getCentrogestorId());

                                    view.getContext().startActivity(intent);
                                    break;
                                case 1:
                                    borrarPrograma(Integer.toString(programas.get(getAdapterPosition()).getId_programa()), view);
                                    programas.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    break;
                            }
                        }
                    });
                    dialog.create().show();
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
