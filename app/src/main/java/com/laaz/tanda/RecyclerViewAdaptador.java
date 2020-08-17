package com.laaz.tanda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewNameGroup, textViewFecha;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameGroup=(TextView)itemView.findViewById(R.id.textViewNameGroup);
            textViewFecha=(TextView)itemView.findViewById(R.id.textViewFecha);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
        }
    }
    public List<GrupoModelo> grupoLista;
    public Context mCtx;

    public RecyclerViewAdaptador(Context mCtx, List<GrupoModelo> grupoLista) {
        this.mCtx= mCtx;
        this.grupoLista = grupoLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GrupoModelo grupoModelo = grupoLista.get(position);

        holder.textViewNameGroup.setText(grupoLista.get(position).getTextViewNameGroup());
        holder.textViewFecha.setText(grupoLista.get(position).getTextViewFecha());
        //holder.imageView.setImageResource(grupoLista.get(position).getImageView());
        Glide.with(mCtx)
                .load(grupoModelo.getImageView())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return grupoLista.size();
    }
}
