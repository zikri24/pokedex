package com.example.zikri.pokedex.data;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.example.zikri.pokedex.model.Pokemon;

import java.util.List;

/**
 * Created by Zikri on 20/09/2016.
 * only two end point
 * one to download by id and the other to get the pokemon by name
 */

public interface PokeAPI {
    //call by id
    @GET("pokemon-form/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);

    //call by name
    @GET("pokemon/{name}")
    Call<Pokemon> getPokemonByName(@Path("name") String name);

}
