package com.example.pokdexcreando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokdexcreando.models.PokeService;
import com.example.pokdexcreando.models.PokemonDetails;
import com.example.pokdexcreando.models.PokemonType;
import com.example.pokdexcreando.models.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.pokdexcreando.MainActivity.EXTRA_CREATOR;
import static com.example.pokdexcreando.MainActivity.EXTRA_LIKES;
import static com.example.pokdexcreando.MainActivity.EXTRA_URL;

public class DetailActivity extends AppCompatActivity {


    private static final String TAG = "PODEKX";
    private String[] urlpokemon;
    private  int numberpokemon;
    private Button btn_details;
    private TextView pokeName;
    private Map<String, String> types_color=new HashMap<String,String>();
    private LinearLayout ln_ly;
    private PokeService pokemonservice = new Retrofit.Builder()
            .baseUrl(PokeService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Button button = findViewById(R.id.backbutton);
        btn_details=findViewById(R.id.button_viewdetails);
        pokeName=findViewById(R.id.texte1view);
        colors_types();
        ln_ly=findViewById(R.id.details_lnly);
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String likecount = intent.getStringExtra(EXTRA_LIKES);
        String creatorname = intent.getStringExtra(EXTRA_CREATOR);
        ImageView imageView = findViewById(R.id.imageview);
        TextView textView1= findViewById(R.id.texte1view);
        TextView textView2 = findViewById(R.id.text2view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "Clicked";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                pokeByName(pokemonservice,textView1.getText().toString());
                pokeTypeByName(pokemonservice,textView1.getText().toString());
            }
        });

        Log.e(TAG,imageUrl);
        urlpokemon= imageUrl.split("/");
         numberpokemon = Integer.parseInt(urlpokemon[urlpokemon.length - 1]);
        Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+numberpokemon+".png").centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

       //Glide.with(this).load(imageUrl).fitCenter().into(imageView);
        textView1.setText(creatorname);
       //  textView2.setText("likes"+imageUrl);
    }
    public void pokeByName(PokeService pokeService,String name) {
        TextView textViewHeight=findViewById(R.id.textView8);
        TextView textViewWeight=findViewById(R.id.textView6);
        TextView textView_Height=findViewById(R.id.textView9);
        TextView textView_Weight=findViewById(R.id.textView7);
        if(name.equals("")==false) {
                    Call<PokemonDetails> pokeCall = pokeService.getPokemonByName(name);
                    pokeCall.enqueue(new Callback<PokemonDetails>() {
                        @Override
                        public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                            PokemonDetails foundPoke = response.body();

                            // check if the body isn't null
                            if (foundPoke != null) {
                                System.out.println("Height : "+foundPoke.getHeight());
                                System.out.println("Weight : "+foundPoke.getWeight());
                                Double height = Double.parseDouble(foundPoke.getHeight()) / 10;
                                textViewHeight.setText(height.toString()+"m");
                                Double weight = Double.parseDouble(foundPoke.getWeight()) / 10;
                                textViewWeight.setText(weight.toString()+"kg");
                                textView_Height.setVisibility(0);
                                textView_Weight.setVisibility(0);
                            }
                        }

                        @Override
                        public void onFailure(Call<PokemonDetails> call, Throwable t) {
                            // TOAST THE FAILURE
                            Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
    public void pokeTypeByName(PokeService pokeService,String name) {
        System.out.println("Calling pokemon type api...."+name);
        System.out.println("Id of pokemon : "+numberpokemon);
        TextView textViewType=findViewById(R.id.textView5);
        TextView textView_Type=findViewById(R.id.textView4);
        if(name.equals("")==false) {
            Call<PokemonType> pokeCall = pokeService.getTypeByName(name);
            pokeCall.enqueue(new Callback<PokemonType>() {
                @Override
                public void onResponse(Call<PokemonType> call, Response<PokemonType> response) {
                    PokemonType foundPoke = response.body();
                    System.out.println("not found....");
                   // System.out.println(foundPoke);
                    // check if the body isn't null
                    if (foundPoke != null) {
                        //type on recupère le premier element du tableau
                        // car un pokemon peut avoir plusieurs types
                        List<Type> types=foundPoke.getTypes();
                        String type=types.get(0).getType().getName().toString();
                        //on change la couleur du background
                        ln_ly.setBackgroundColor(Color.parseColor(types_color.get(type)));
                        System.out.println("I JUST GOT THE TYPE OF YOUR POKEMON : "+ type);
                        textView_Type.setText(type);
                        textViewType.setVisibility(0);
                    }

                }

                @Override
                public void onFailure(Call<PokemonType> call, Throwable t) {
                    // TOAST THE FAILURE
                    Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void colors_types() {
        String[] types={"normal","fighting","flying","poison","ground","rock","bug","ghost","steel","fire","water","grass","electric","psychic","ice","dragon","dark","fairy","unknown","shadow"};
        String[] colors={"#858b94","#79a2e0","#64c9d9","#64c9d9","#64d964","#a8d964","#c4d964","#d5d964","#d9c464","#d9c464","#d9b464","#d9ac64","#d99764","#d97b64","#b264d9","#858b94","#d964c2","#f589ba","#ad89f5","#979fad"};
        for(int i=0;i<types.length;i++){
            types_color.put(types[i],colors[i]);
        }
    }
    }