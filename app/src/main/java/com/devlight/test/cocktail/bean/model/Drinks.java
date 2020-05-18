package com.devlight.test.cocktail.bean.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Drinks {

    @SerializedName("drinks")
    @Expose
    private List<Cocktail> mCocktails;

    public List<Cocktail> getCocktails() {
        return mCocktails;
    }

    public void setCocktails(List<Cocktail> cocktails) {
        mCocktails = cocktails;
    }
}
