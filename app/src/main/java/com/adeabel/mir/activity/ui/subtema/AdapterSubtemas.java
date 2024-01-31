package com.adeabel.mir.activity.ui.subtema;

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

public class AdapterSubtemas extends RecyclerView.Adapter<com.adeabel.mir.activity.ui.subtema.AdapterSubtemas.ViewHolder>{
    LayoutInflater inflater;
    ArrayList<Subtemas> subtemas;

    public AdapterSubtemas(Context ctx, ArrayList<Subtemas> subtemas){
        this.inflater = LayoutInflater.from(ctx);
        this.subtemas = subtemas;
    }

    public void updateAdapter(ArrayList<Subtemas> subtemas){
        this.subtemas = subtemas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public com.adeabel.mir.activity.ui.subtema.AdapterSubtemas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.subtemas_list, parent,false);
        return  new com.adeabel.mir.activity.ui.subtema.AdapterSubtemas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.adeabel.mir.activity.ui.subtema.AdapterSubtemas.ViewHolder holder, int position) {
        String nombre = subtemas.get(position).getNombre_subtema();
        String id = Integer.toString(subtemas.get(position).getId_subtema());
        holder.nombreSubtema.setText(nombre);
    }

    @Override
    public int getItemCount() {
        return subtemas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreSubtema;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreSubtema = itemView.findViewById(R.id.tv_listar_subtemas);
            cardView = itemView.findViewById(R.id.cardview_subtema);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence[] dialogItems = {"Editar", "Borrar"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                    dialog.setTitle(subtemas.get(getAdapterPosition()).getNombre_subtema()).setItems(dialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case 0:
                                    Intent intent = new Intent(view.getContext().getApplicationContext(), ActualizarSubtemasActivity.class)
                                            .putExtra("nombre", subtemas.get(getAdapterPosition()).nombre_subtema)
                                            .putExtra("id", subtemas.get(getAdapterPosition()).id_subtema);

                                    view.getContext().startActivity(intent);
                                    break;
                                case 1:
                                    borrarSubtema(Integer.toString(subtemas.get(getAdapterPosition()).getId_subtema()), view);
                                    subtemas.remove(getAdapterPosition());
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

    private void borrarSubtema(String id, View view) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/subtemas/borrar_subtema.php", new Response.Listener<String>() {
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
                params.put("id_subtema", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
}
