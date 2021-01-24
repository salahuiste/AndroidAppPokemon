package com.example.pokdexcreando.pokeapi;

import com.example.pokdexcreando.models.Pokemon;
import com.example.pokdexcreando.models.PokemonRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeapiService {


    @GET("/api/v2/pokemon")
    public Call<PokemonRequest> obtenirListPokemon(@Query("limit") int limit,@Query("offset") int offset);


    @GET("/api/v2/generation")
    public Call<PokemonRequest> obtenirGeneration();


}
