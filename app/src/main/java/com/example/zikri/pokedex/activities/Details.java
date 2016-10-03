package com.example.zikri.pokedex.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zikri.pokedex.R;
import com.example.zikri.pokedex.data.ImageConverter;
import com.example.zikri.pokedex.data.PokeAPI;
import com.example.zikri.pokedex.data.PokeAPIService;
import com.example.zikri.pokedex.model.Pokemon;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details extends AppCompatActivity {

    //declaring private fields for the details activity
    Context context = this;
    private ImageConverter imageConverter;
    private PokeAPIService pokeAPIService;
    private TextView pokemonNameTextView;
    private TextView numberText;
    private Pokemon pokemon;
    private ImageView frontImageView;
    private ImageView backImageView;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView orderTextView;
    private TextView heightLabel;
    private TextView weightLabel;
    private TextView orderLabel;
    private TextView abilityOneText;
    private TextView abilityTwoText;
    private TextView typeOneText;
    private TextView typeTwoText;
    private TextView speciesText;
    private TextView speedStats;
    private TextView defenceStats;
    private TextView attackStats;
    private TextView SpDefenceStats;
    private TextView SpAttackStats;
    private TextView hpStats;
    private TextView moveOneText;
    private TextView moveTwoText;
    private TextView moveThreeText;
    private TextView moveFourText;
    private TextView moveFiveText;
    private ListView movesListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> moves;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //retrieving the pokemon name from the intent
        Intent intent = getIntent();
        final String pokemonName = intent.getStringExtra(MainActivity.POKEMON_NAME);
        //setting up the progress bar and make it invisible
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //getting references of all our controls
        pokemonNameTextView = (TextView) findViewById(R.id.nameTextView);
        numberText = (TextView) findViewById(R.id.numberTextView);
        frontImageView = (ImageView) findViewById(R.id.front_image);
        backImageView = (ImageView) findViewById(R.id.back_image);
        heightLabel = (TextView) findViewById(R.id.height_label);
        weightLabel = (TextView) findViewById(R.id.weight_label);
        orderLabel = (TextView) findViewById(R.id.order_label);
        heightTextView = (TextView) findViewById(R.id.heightText);
        weightTextView = (TextView) findViewById(R.id.weightText);
        orderTextView = (TextView) findViewById(R.id.orderText);
        abilityOneText = (TextView) findViewById(R.id.ability_one);
        abilityTwoText = (TextView) findViewById(R.id.ability_two);
        typeOneText = (TextView) findViewById(R.id.type_one);
        typeTwoText = (TextView) findViewById(R.id.type_two);
        speciesText = (TextView) findViewById(R.id.species);
        //movesListView = (ListView) findViewById(R.id.moves_list);
        speedStats = (TextView) findViewById(R.id.speedText);
        defenceStats = (TextView) findViewById(R.id.defenceText);
        attackStats = (TextView) findViewById(R.id.attackText);
        SpDefenceStats = (TextView) findViewById(R.id.spDefenceText);
        SpAttackStats = (TextView) findViewById(R.id.spAttackText);
        hpStats = (TextView) findViewById(R.id.hpText);
        moveOneText = (TextView) findViewById(R.id.moveOneText);
        moveTwoText = (TextView) findViewById(R.id.moveTowText);
        moveThreeText = (TextView) findViewById(R.id.moveThreeText);
        moveFourText = (TextView) findViewById(R.id.moveFourText);
        moveFiveText = (TextView) findViewById(R.id.moveFiveText);

        //make the progress bar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating an PokeAPI service to call each pokemon
        pokeAPIService = new PokeAPIService();
        PokeAPI service = pokeAPIService.createRetrofitService();
        //calling the end point @GET("pokemon/{name}" and passing the name to it to get th e pokemon details
        Call<Pokemon> call = service.getPokemonByName(pokemonName);
        call.enqueue(new Callback<Pokemon>() {
                         @Override
                         public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                             progressBar.setVisibility(View.VISIBLE);
                             //creating an image converter instance to download the pokemon front and back images
                             imageConverter = new ImageConverter();
                             byte[] frontImage;
                             byte[] backImage;
                             Bitmap frontBmp = null;
                             Bitmap backBmp = null;
                             try {
                                 frontImage = imageConverter.downloadImage(response.body().getSprite().getFrontDefault());
                                 backImage = imageConverter.downloadImage(response.body().getSprite().getBackDefault());
                                 frontBmp = BitmapFactory.decodeByteArray(frontImage, 0, frontImage.length);
                                 backBmp = BitmapFactory.decodeByteArray(backImage, 0, backImage.length);
                             } catch (IOException e) {
                                 frontBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.my_launche);
                                 backBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.my_launche);
                             }
                             //creating a pokemon instance and copy the response object to it
                             pokemon = new Pokemon();
                             pokemon.setName(response.body().getName());
                             pokemon.setId(response.body().getId());
                             pokemon.setHeight(response.body().getHeight());
                             pokemon.setWeight(response.body().getWeight());
                             pokemon.setOrder(response.body().getOrder());
                             pokemon.setAbilities(response.body().getAbilities());
                             pokemon.setMoves(response.body().getMoves());
                             pokemon.setSpecies(response.body().getSpecies());
                             pokemon.setStats(response.body().getStats());
                             pokemon.setTypes(response.body().getTypes());

                             //displaying the pokemon attributes in the controls
                             frontImageView.setImageBitmap(frontBmp);
                             backImageView.setImageBitmap(backBmp);
                             pokemonNameTextView.setText(pokemon.getName());
                             String formatted = String.format("#%03d", pokemon.getId());
                             numberText.setText(formatted);
                             setTitle(pokemon.getName());
                             heightLabel.setText("HEIGHT: ");
                             heightTextView.setText(String.valueOf(pokemon.getHeight() / 10) + "m");
                             heightTextView.setTypeface(null, Typeface.BOLD);
                             weightLabel.setText("WEIGHT: ");
                             weightTextView.setTypeface(null, Typeface.BOLD);
                             weightTextView.setText(String.valueOf(pokemon.getWeight() / 10) + "kg");
                             orderLabel.setText("ORDER: ");
                             orderTextView.setText(String.valueOf(pokemon.getOrder()));
                             orderTextView.setTypeface(null, Typeface.BOLD);
                             abilityOneText.setText(pokemon.getAbilities().get(0).getAbility().getName());
                             abilityOneText.setBackgroundResource(R.drawable.rounded_corner);
                             if ((pokemon.getAbilities().size() > 1)) {
                                 abilityTwoText.setText(pokemon.getAbilities().get(1).getAbility().getName());
                                 abilityTwoText.setBackgroundResource(R.drawable.rounded_corner);
                             }
                             typeOneText.setText(pokemon.getTypes().get(0).getType().getName());
                             typeOneText.setBackgroundResource(R.drawable.rounded_corner);
                             if ((pokemon.getTypes().size() > 1)) {
                                 typeTwoText.setText(pokemon.getTypes().get(1).getType().getName());
                                 typeTwoText.setBackgroundResource(R.drawable.rounded_corner);
                             }
                             speciesText.setText(pokemon.getSpecies().getName());
                             speciesText.setBackgroundResource(R.drawable.rounded_corner);
                             moves = new ArrayList<>();
                             //get and display the top 5 moves
                             for (int i = 0; i < pokemon.getMoves().size(); i++) {
                                 moves.add(pokemon.getMoves().get(i).getMove().getName());
                             }
                             moveOneText.setText(moves.get(0));
                             moveOneText.setBackgroundResource(R.drawable.rounded_corner_purple);
                             moveTwoText.setText(moves.get(1));
                             moveTwoText.setBackgroundResource(R.drawable.rounded_corner_purple);
                             moveThreeText.setText(moves.get(2));
                             moveThreeText.setBackgroundResource(R.drawable.rounded_corner_purple);
                             moveFourText.setText(moves.get(3));
                             moveFourText.setBackgroundResource(R.drawable.rounded_corner_purple);
                             moveFiveText.setText(moves.get(4));
                             moveFiveText.setBackgroundResource(R.drawable.rounded_corner_purple);

                             //display stats
                             speedStats.setText(String.valueOf(pokemon.getStats().get(0).getBaseStat()));
                             speedStats.setBackgroundResource(R.drawable.rounded_corner_gold);
                             defenceStats.setText(String.valueOf(pokemon.getStats().get(3).getBaseStat()));
                             defenceStats.setBackgroundResource(R.drawable.rounded_corner_gold);
                             attackStats.setText(String.valueOf(pokemon.getStats().get(2).getBaseStat()));
                             attackStats.setBackgroundResource(R.drawable.rounded_corner_gold);
                             SpDefenceStats.setText(String.valueOf(pokemon.getStats().get(1).getBaseStat()));
                             SpDefenceStats.setBackgroundResource(R.drawable.rounded_corner_gold);
                             SpAttackStats.setText(String.valueOf(pokemon.getStats().get(4).getBaseStat()));
                             SpAttackStats.setBackgroundResource(R.drawable.rounded_corner_gold);
                             hpStats.setText(String.valueOf(pokemon.getStats().get(5).getBaseStat()));
                             hpStats.setBackgroundResource(R.drawable.rounded_corner_gold);

                             progressBar.setVisibility(View.INVISIBLE);
                         }

                         @Override
                         public void onFailure(Call<Pokemon> call, Throwable t) {
                             t.getCause();
                         }
                     }
        );
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
