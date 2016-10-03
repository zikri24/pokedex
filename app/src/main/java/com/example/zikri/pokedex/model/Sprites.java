package com.example.zikri.pokedex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Zikri on 21/09/2016.
 */
public class Sprites {

    @SerializedName("back_default")
    @Expose
    private String backDefault;

    @SerializedName("front_default")
    @Expose
    private String frontDefault;
    @SerializedName("front_shiny")
    @Expose
    private String frontShiny;

    public String getFrontDefault() {
        return frontDefault;
    }

    public void setFrontDefault(String frontDefault) {
        this.frontDefault = frontDefault;
    }

    public String getFrontShiny() {
        return frontShiny;
    }

    public void setFrontShiny(String frontShiny) {
        this.frontShiny = frontShiny;
    }

    public Sprites() {
    }

    public Sprites(String backDefault) {
        this.backDefault = backDefault;
    }

    public String getBackDefault() {
        return backDefault;
    }




    public void setBackDefault(String backDefault) {
        this.backDefault = backDefault;
    }

}
