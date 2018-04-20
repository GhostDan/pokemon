package com.example.dante.poke;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class AdapterPoke extends RecyclerView.Adapter<AdapterPoke.ViewHolder> {
    private ArrayList<Pokemon> dataset;
    private Context context;

    private int numero;
    private String nombre;
    private MediaPlayer mp;
    public AdapterPoke(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.nombreTextView.setText(p.getName());

        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);

    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);
            mp = MediaPlayer.create(context,R.raw.sound);


            fotoImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mp.start();
                    nombre = nombreTextView.getText().toString();
                    for(int i =0;i<dataset.size();i++){
                        Pokemon p = dataset.get(i);
                        if(p.getName().equals(nombre)){
                            numero=p.getNumber();
                        }
                    }

                    new CuadroDialogo(context,numero,nombre);

                }
            });
        }
    }
}
