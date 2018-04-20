package com.example.dante.poke;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

public class CuadroDialogo {



    public CuadroDialogo(Context context,int numero,String nombre) {

        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.cuadro_dialogo);

        ImageView cerrar = (ImageView) dialogo.findViewById(R.id.imgx);
        ImageView imgpokemon = (ImageView) dialogo.findViewById(R.id.imgpokemon);
        TextView textpokemon = (TextView) dialogo.findViewById(R.id.textpokemon);

        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + numero + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgpokemon);

            textpokemon.setText(nombre+" "+"#"+numero);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }
}
