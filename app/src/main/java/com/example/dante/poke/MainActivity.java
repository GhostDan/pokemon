package com.example.dante.poke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableRow;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView Rv;
    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";
    private AdapterPoke adapterPoke;
    private int offset;

    private boolean reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Rv = (RecyclerView) findViewById(R.id.Rv);
        adapterPoke = new AdapterPoke(this);
        Rv.setAdapter(adapterPoke);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        Rv.setLayoutManager(layoutManager);
        Rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (reload) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {

                            reload = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });
        retrofit = new Retrofit.Builder().baseUrl("http://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        reload=true;
        offset = 0;
        obtenerDatos(offset);
    }
    public void obtenerDatos(int offset){
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon(20,offset);

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                reload = true;
                if(response.isSuccessful()){
                    PokemonRespuesta pokeRespuesta = response.body();
                    ArrayList<Pokemon> listaPokemones = pokeRespuesta.getResults();

                    adapterPoke.adicionarListaPokemon(listaPokemones);

                }else{
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                reload = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });

    }
}
