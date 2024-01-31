package com.adeabel.mir.activity.ui.estrategia;

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
import com.adeabel.mir.modelos.Estrategias;
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

public class AdapterEstrategias extends RecyclerView.Adapter<AdapterEstrategias.ViewHolder>{

    LayoutInflater inflater;
    ArrayList<Estrategias> estrategias;

    public AdapterEstrategias(Context ctx, ArrayList<Estrategias> estrategias){
        this.inflater = LayoutInflater.from(ctx);
        this.estrategias = estrategias;
    }


    @NonNull
    @Override
    public com.adeabel.mir.activity.ui.estrategia.AdapterEstrategias.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.estrategias_list, parent,false);
        return  new com.adeabel.mir.activity.ui.estrategia.AdapterEstrategias.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.adeabel.mir.activity.ui.estrategia.AdapterEstrategias.ViewHolder holder, int position) {
        String nombre = estrategias.get(position).getNombre_estrategia();
        String id = Integer.toString(estrategias.get(position).getId_estrategia());
        holder.nombreEstrategia.setText(nombre);
    }

    @Override
    public int getItemCount() {
        return estrategias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreEstrategia;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreEstrategia = itemView.findViewById(R.id.tv_listar_estrategias);
            cardView = itemView.findViewById(R.id.cardview_estrategia);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence[] dialogItems = {"Editar", "Borrar"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                    dialog.setTitle(estrategias.get(getAdapterPosition()).getNombre_estrategia()).setItems(dialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case 0:
                                    Intent intent = new Intent(view.getContext().getApplicationContext(), ActualizarEstrategiaActivity.class)
                                            .putExtra("nombre", estrategias.get(getAdapterPosition()).nombre_estrategia)
                                            .putExtra("id", estrategias.get(getAdapterPosition()).id_estrategia);

                                    view.getContext().startActivity(intent);
                                    break;
                                case 1:
                                    borrarEstrategia(Integer.toString(estrategias.get(getAdapterPosition()).getId_estrategia()), view);
                                    estrategias.remove(getAdapterPosition());
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

    private void borrarEstrategia(String id, View view) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/estrategias/borrar_estrategia.php", new Response.Listener<String>() {
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
                params.put("id_estrategia", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
}
