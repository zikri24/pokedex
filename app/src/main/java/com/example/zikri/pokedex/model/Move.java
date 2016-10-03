package com.example.zikri.pokedex.model;

/**
 * Created by Zikri on 26/09/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Move {
    @SerializedName("move")
    @Expose
    private Move_ move;

    public Move_ getMove() {
        return move;
    }

    public void setMove(Move_ move) {
        this.move = move;
    }

}