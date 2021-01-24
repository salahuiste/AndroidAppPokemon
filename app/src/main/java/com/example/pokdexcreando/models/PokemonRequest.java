package com.example.pokdexcreando.models;

import java.util.ArrayList;
import java.util.List;

public class PokemonRequest {
    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
