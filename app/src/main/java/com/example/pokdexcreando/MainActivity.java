package com.example.pokdexcreando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pokdexcreando.models.Pokemon;
import com.example.pokdexcreando.models.PokemonRequest;
import com.example.pokdexcreando.pokeapi.PokeapiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.OnItemClickListener {
   private Retrofit retrofit;
    public ArrayList<Pokemon> listpokemons;
    private  PokeapiService service;
    private static final String TAG="POKEDEX";
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewgeneration;
    private  PokeapiService service2;

    private PokemonAdapter pokemonAdapter;
    private GenerationAdapter generationAdapter;
    private int offset;
    private int limit;
    private boolean Paracar;
    public List<Pokemon> dataListitem = new ArrayList<>();
    public  static  final String EXTRA_URL = "imageurl";
    public  static  final String EXTRA_CREATOR="creatorname";
    public  static  final String EXTRA_LIKES="likecount";
    public    ArrayList<Pokemon> listpokemon;
    public   ArrayList<Pokemon> listgeneration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //generation
        recyclerViewgeneration = findViewById(R.id.rv_generation);
        generationAdapter=new GenerationAdapter(this);
        recyclerViewgeneration.setAdapter(generationAdapter);
        //GridLayoutManager layoutManagergeneration= new GridLayoutManager(this,8);
        recyclerViewgeneration.setLayoutManager(new LinearLayoutManager(this
        ,LinearLayoutManager.HORIZONTAL,false));
        //end
       recyclerView= findViewById(R.id.recyler_view);
        pokemonAdapter=new PokemonAdapter(this);

        recyclerView.setAdapter(pokemonAdapter);
        //test profile
      pokemonAdapter.setOnItemClickListener(MainActivity.this);
        //end
        GridLayoutManager layoutManager= new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
     /*  recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
          public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy> 0){
                    int visibmeItemCount=layoutManager.getChildCount();
                    int totalItemCount= layoutManager.getItemCount();
                    int pastVisibleItems= layoutManager.findFirstVisibleItemPosition();
                    if(Paracar){
                        if((visibmeItemCount+pastVisibleItems)>=totalItemCount){
                            Log.d(TAG,"end scrolling");
                            Paracar=false;
                            offset+=20;
                            obtenirDate(offset);

                        }
                    }
                }
            }
        });*/

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Paracar=true;
        offset=20;
        limit=100;
        //obtenirDate();
       /* ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Post> call = apiInterface.getPost(1);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d(TAG,"lwalida:"+response.body().toString());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }
    private void obtenirDate(){*/
            obtenirDate(offset);
            obtenirGeneration();

    }
    private void obtenirGeneration(){

      service2 = retrofit.create(PokeapiService.class);
      Call <PokemonRequest> pokemonapiservice2= service2.obtenirGeneration();
      pokemonapiservice2.enqueue(new Callback<PokemonRequest>() {
          @Override
          public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {

              if (response.isSuccessful()){
                  PokemonRequest pokemonRequest = response.body();
                  listgeneration = pokemonRequest.getResults();
                  Log.d(TAG,"pokemon:"+pokemonRequest.getResults().get(0));
                  Log.d(TAG,"jawaddd:"+listgeneration.get(0).getName());

                  // pokemonAdapter.addListpokem(listpokemon);
                  generationAdapter.adListGeneration(listgeneration);

              }
              else{
                  Log.d(TAG,"error");

              }

          }

          @Override
          public void onFailure(Call<PokemonRequest> call, Throwable t) {
              Log.d(TAG,"error:"+t.getMessage());

          }
      });



    }
    private void obtenirDate(int offset){
         service= retrofit.create(PokeapiService.class);

        Call <PokemonRequest> pokemonapiservice= service.obtenirListPokemon(limit,offset);
        pokemonapiservice.enqueue(new Callback<PokemonRequest>() {

            @Override
            public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                Paracar=true;
                // List<Pokemon> list = new ArrayList<Pokemon>();
                //list = response.body().getResults() ;
              //  Log.d(TAG,"lwalida:"+response.body().getResults());

              if (response.isSuccessful()){
                  PokemonRequest pokemonRequest = response.body();
                  listpokemon = pokemonRequest.getResults();
                  Log.d(TAG,"pokemon:"+pokemonRequest.getResults().get(0));

                 // pokemonAdapter.addListpokem(listpokemon);
                  pokemonAdapter.adListPokemon(listpokemon);
              }
                else{
                    Log.d(TAG,"error");

                }
            }

            @Override
            public void onFailure(Call<PokemonRequest> call, Throwable t) {
                Log.d(TAG,"error:"+t.getMessage());

            }
        });


    }

    @Override
    public void onItemClcik(int position) {
        Intent detailtIntent = new Intent( this,DetailActivity.class);
        Pokemon p = listpokemon.get(position);
        //Log.e(TAG, String.valueOf(listpokemon.get(0))+"lwalida");
       detailtIntent.putExtra(EXTRA_URL,p.getUrl());
       detailtIntent.putExtra(EXTRA_CREATOR,p.getName());
       detailtIntent.putExtra(EXTRA_LIKES,p.getNumber());
       startActivity(detailtIntent );
        Log.e(TAG,"hello jawad"+position);


    }
}