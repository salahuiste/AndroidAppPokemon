package com.example.pokdexcreando.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeService {
    String BASE_URL = "https://pokeapi.co/api/v2/";

    @GET("pokemon/{name}")
    Call<PokemonDetails> getPokemonByName(@Path("name") String name);
    @GET("pokemon/{name}")
    Call<PokemonType> getTypeByName(@Path("name") String name);
}
