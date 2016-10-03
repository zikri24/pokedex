package com.example.zikri.pokedex.model;

/**
 * Created by Zikri on 20/09/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Pokemon {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("sprites")
    @Expose
    private Sprites sprite;


//New attributes
    @SerializedName("height")
    @Expose
    private Double height;

    @SerializedName("weight")
    @Expose
    private Double weight;

    @SerializedName("order")
    @Expose
    private Integer order;

    @SerializedName("abilities")
    @Expose
    private List<Ability> abilities = new ArrayList<Ability>();

    @SerializedName("moves")
    @Expose
    private List<Move> moves = new ArrayList<Move>();

    @SerializedName("species")
    @Expose
    private Species species;

    @SerializedName("stats")
    @Expose
    private List<Stat> stats = new ArrayList<Stat>();

    @SerializedName("types")
    @Expose
    private List<Type> types = new ArrayList<Type>();


    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public Sprites getSprite() {
        return sprite;
    }

    public void setSprite(Sprites sprite) {
        this.sprite = sprite;

    }
}
