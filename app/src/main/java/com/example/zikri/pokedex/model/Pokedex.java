package com.example.zikri.pokedex.model;

import java.util.List;

/**
 * Created by Zikri on 22/09/2016.
 */
public class Pokedex {
    private List<Pokemon> pokemonList;

    public Pokedex() {
    }

    public Pokedex(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }
}
