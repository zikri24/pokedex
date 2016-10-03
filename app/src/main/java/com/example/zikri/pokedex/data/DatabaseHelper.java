package com.example.zikri.pokedex.data;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.ContextCompat;

import java.sql.Blob;

/**
 * Created by Zikri on 21/09/2016.
 */


public class DatabaseHelper extends SQLiteOpenHelper {
    //Database name, table and columns
    private static final String DATABASE_NAME = "pokemon.db";
    public final static String POKEMON_TABLE = "Pokemons";
    public final static String POKEMON_ID = "pokemonId";
    public final static String POKEMON_NAME = "name";
    public final static String POKEMON_SPRITE = "sprite";
    private static final int DATABASE_VERSION = 1;

    //Creating the table
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + POKEMON_TABLE + " (" + POKEMON_ID + " INTEGER PRIMARY KEY, "
            + POKEMON_NAME + " TEXT NOT NULL, "
            + POKEMON_SPRITE + " BLOB )";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + POKEMON_TABLE);
        onCreate(sqLiteDatabase);
    }

    //inserting a pokemon to the database table
    public boolean insterPokemon(int id, String name, byte[] sprite) {
        boolean inserted = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POKEMON_ID, id);
        contentValues.put(POKEMON_NAME, name);
        contentValues.put(POKEMON_SPRITE, sprite);
        long result = db.insert(POKEMON_TABLE, null, contentValues);
        if (result == -1) {
            inserted = false;
        } else {
            inserted = true;
        }
        return inserted;
    }

    //reading all the pokemons from the database
    public Cursor getAllPokemons() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + POKEMON_TABLE, null);
        return result;
    }

    //delete all the pokemon from the database
    public void deleteAllPokemons() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + POKEMON_TABLE);
    }

}
