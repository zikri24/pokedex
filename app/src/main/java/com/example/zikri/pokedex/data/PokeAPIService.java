package com.example.zikri.pokedex.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zikri on 25/09/2016.
 * this class only create an instance of the retrofit to call the pokeAPI
 */
public class PokeAPIService {

    public PokeAPI createRetrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokeAPI service = retrofit.create(PokeAPI.class);
        return service;
    }
}
