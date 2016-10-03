package com.example.zikri.pokedex.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zikri.pokedex.R;
import com.example.zikri.pokedex.data.ImageConverter;
import com.example.zikri.pokedex.model.Pokemon;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Zikri on 25/09/2016.
 */
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonHolder> {
    //crating the private fields for the RecyclerView adapter
    private List<Pokemon> pokemons;
    private LayoutInflater inflater;
    private ImageConverter imageConverter;
    private ItemClickCallback itemClickCallback;

    //declaring interface for the on click event
    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    //setting up the adapter with a list of pokemons
    public PokemonAdapter(List<Pokemon> pokemons, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.pokemons = pokemons;
    }

    //assigning the layout of the pokemon item in the list
    @Override
    public PokemonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new PokemonHolder(view);
    }

    //bind the pokemon object with the view controls
    @Override
    public void onBindViewHolder(PokemonHolder holder, int position) {
        imageConverter = new ImageConverter();
        Pokemon pokemon = pokemons.get(position);
        byte[] blob = pokemon.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        holder.image.setImageBitmap(bmp);
        holder.name.setText(pokemon.getName());
        String formatted = String.format("#%03d", pokemon.getId());
        holder.id.setText(formatted);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    //setting up the view control with the pokemon attributes
    class PokemonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView name;
        private TextView id;
        private View container;

        public PokemonHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.poke_icon);
            name = (TextView) itemView.findViewById(R.id.poke_name);
            id = (TextView) itemView.findViewById(R.id.poke_number);
            container = itemView.findViewById(R.id.cont_item_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.cont_item_root)
                itemClickCallback.onItemClick(getAdapterPosition());
        }
    }
}
