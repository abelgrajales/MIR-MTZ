package com.adeabel.mir.activity.ui.centrogestor;

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
import com.adeabel.mir.modelos.CentroGestor;
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

public class AdapterCentroGestor extends RecyclerView.Adapter<com.adeabel.mir.activity.ui.centrogestor.AdapterCentroGestor.ViewHolder>{

    LayoutInflater inflater;
    ArrayList<CentroGestor> centroGestor;

    public AdapterCentroGestor(Context ctx, ArrayList<CentroGestor> centroGestor){
        this.inflater = LayoutInflater.from(ctx);
        this.centroGestor = centroGestor;
    }


    @NonNull
    @Override
    public com.adeabel.mir.activity.ui.centrogestor.AdapterCentroGestor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.centrogestor_list, parent,false);
        return  new com.adeabel.mir.activity.ui.centrogestor.AdapterCentroGestor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.adeabel.mir.activity.ui.centrogestor.AdapterCentroGestor.ViewHolder holder, int position) {
        String nombre = centroGestor.get(position).getNombre_centro_gestor();
        String id = Integer.toString(centroGestor.get(position).getId_centro_gestor());
        holder.nombreCentroGestor.setText(nombre);
    }

    @Override
    public int getItemCount() {
        return centroGestor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreCentroGestor;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreCentroGestor = itemView.findViewById(R.id.tv_listar_centro_gestor);
            cardView = itemView.findViewById(R.id.cardview_centrogestor);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence[] dialogItems = {"Editar", "Borrar"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                    dialog.setTitle(centroGestor.get(getAdapterPosition()).getNombre_centro_gestor()).setItems(dialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case 0:
                                    Intent intent = new Intent(view.getContext().getApplicationContext(), ActualizarCentroGestorActivity.class)
                                            .putExtra("nombre", centroGestor.get(getAdapterPosition()).nombre_centro_gestor)
                                            .putExtra("id", centroGestor.get(getAdapterPosition()).id_centro_gestor);

                                    view.getContext().startActivity(intent);
                                    break;
                                case 1:
                                    borrarCentroGestor(Integer.toString(centroGestor.get(getAdapterPosition()).getId_centro_gestor()), view);
                                    centroGestor.remove(getAdapterPosition());
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

    private void borrarCentroGestor(String id, View view) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/centrogestor/borrar_centro_gestor.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("id_centro_gestor", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
}
