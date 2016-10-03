package com.example.zikri.pokedex.model;

/**
 * Created by Zikri on 26/09/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Type {
    @SerializedName("slot")
    @Expose
    private Integer slot;
    @SerializedName("type")
    @Expose
    private Type_ type;


    public Integer getSlot() {
        return slot;
    }


    public void setSlot(Integer slot) {
        this.slot = slot;
    }


    public Type_ getType() {
        return type;
    }


    public void setType(Type_ type) {
        this.type = type;
    }

}
