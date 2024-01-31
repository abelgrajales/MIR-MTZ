package com.adeabel.mir.activity.ui.ejes;

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
import com.adeabel.mir.modelos.Ejes;
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

public class AdapterEjes extends RecyclerView.Adapter<AdapterEjes.ViewHolder>{
    LayoutInflater inflater;
    ArrayList<Ejes> ejes;

    public AdapterEjes(Context ctx, ArrayList<Ejes> ejes){
        this.inflater = LayoutInflater.from(ctx);
        this.ejes = ejes;
    }

    public void updateAdapter(ArrayList<Ejes> ejes){
        this.ejes = ejes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ejes_list, parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = ejes.get(position).getNombre_eje();
        String id = Integer.toString(ejes.get(position).getId_eje());

        holder.nombreEje.setText(nombre);
    }

    @Override
    public int getItemCount() {
        return ejes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreEje;
        CardView cardView;
        //ImageView delete;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreEje = itemView.findViewById(R.id.tv_listar_ejes);
            cardView = itemView.findViewById(R.id.cardview_eje);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence[] dialogItems = {"Editar", "Borrar"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                    dialog.setTitle(ejes.get(getAdapterPosition()).getNombre_eje()).setItems(dialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i){
                                case 0:
                                    Intent intent = new Intent(view.getContext().getApplicationContext(), ActualizarEjes_Activity.class)
                                            .putExtra("nombre", ejes.get(getAdapterPosition()).nombre_eje)
                                            .putExtra("id", ejes.get(getAdapterPosition()).id_eje);

                                    view.getContext().startActivity(intent);
                                    break;
                                case 1:
                                    System.out.println("Hola mundo");
                                    borrarEje(Integer.toString(ejes.get(getAdapterPosition()).getId_eje()), view);
                                    ejes.remove(getAdapterPosition());
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

    private void borrarEje(String id, View view) {
            StringRequest request = new StringRequest(Request.Method.POST, "https://adeabel.000webhostapp.com/mir/ejes/borrar_eje.php", new Response.Listener<String>() {
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
                    params.put("id_eje", id);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(request);
    }
}
