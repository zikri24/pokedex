package com.example.zikri.pokedex.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import com.example.zikri.pokedex.R;
import com.example.zikri.pokedex.adapter.PokemonAdapter;
import com.example.zikri.pokedex.data.DatabaseHelper;
import com.example.zikri.pokedex.data.ImageConverter;
import com.example.zikri.pokedex.data.PokeAPI;
import com.example.zikri.pokedex.data.PokeAPIService;
import com.example.zikri.pokedex.model.Pokemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements PokemonAdapter.ItemClickCallback {

    //Declaring the main activity fields
    public static final String POKEMON_NAME = "Pokemon Name";
    private RecyclerView recView;
    private PokeAPIService pokeAPIService;
    private Pokemon pokemon;
    private ImageConverter imageConverter;
    private DatabaseHelper myDB;
    private Cursor cursor;
    private PokemonAdapter adapter;
    private List<Pokemon> pokemons;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //allowing network connections
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting a reference to the progress bar and make it visible
        pb = (ProgressBar) findViewById(R.id.mainProgressBar);
        //pb.setVisibility(View.VISIBLE);

        //initializing an image converter class in to download the pokemon image
        imageConverter = new ImageConverter();
        //Creating an instance of the database
        myDB = new DatabaseHelper(this);
       //myDB.deleteAllPokemons();
        //retrieving all the pokemon form the database
        cursor = myDB.getAllPokemons();
        //get the number of the pokemons stored in the database
        int count = cursor.getCount();
        //if we have less than 150 pokemons, keep calling more until the number of the pokemon in the database reaches 150
        if (count < 151) {
            pb.setVisibility(View.VISIBLE);
            //creating an instance of the pokeAPI service
            pokeAPIService = new PokeAPIService();
            PokeAPI service = pokeAPIService.createRetrofitService();

            //download 150 Pokemons
            for (int i = count + 1; i < 151; i++) {
                //calling the endpoint @GET("pokemon-form/{id}") to download the pokemon by id
                Call<Pokemon> call = service.getPokemon(i);
                call.enqueue(new Callback<Pokemon>() {
                    //on success
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        //creating a new pokemon and collect the data from the response object
                        pokemon = new Pokemon();
                        pokemon.setId(response.body().getId());
                        pokemon.setName(response.body().getName());
                        byte[] image = null;
                        try {
                            image = imageConverter.downloadImage(response.body().getSprite().getFrontDefault());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pokemon.setImage(image);
                        //store the pokemon in the database
                        myDB.insterPokemon(pokemon.getId(), pokemon.getName(), pokemon.getImage());
                        /*Intent intent = getIntent();
                        finish();
                        startActivity(intent);*/
                    }
                    @Override
                    //on failure
                    public void onFailure(Call<Pokemon> call, Throwable t) {
                        t.getCause();
                    }
                });
            }
        }
        //setup the recycler view
        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        pokemons = new ArrayList<>();
        pokemon = new Pokemon();
        //read all the pokemons from the database
        cursor = myDB.getAllPokemons();
        while (cursor.moveToNext()) {
            pokemon = new Pokemon();
            pokemon.setId(cursor.getInt(0));
            pokemon.setName(cursor.getString(1));
            byte[] blob = cursor.getBlob(2);
            pokemon.setImage(blob);
            pokemons.add(pokemon);
        }
        //load the data to the recyclerView
        adapter = new PokemonAdapter(pokemons, this);
        recView.setAdapter(adapter);
        pb.setVisibility(View.INVISIBLE);
        adapter.setItemClickCallback(this);

    }

    //passing the pokemon name to the detail activity on item clicked
    @Override
    public void onItemClick(int p) {
        Pokemon pokemon = pokemons.get(p);
        Intent intent = new Intent(this, Details.class);
        intent.putExtra(POKEMON_NAME, pokemon.getName());
        startActivity(intent);
    }

    //changing the orientation of the device
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
