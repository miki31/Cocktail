package com.devlight.test.cocktail.network.rest;

import com.devlight.test.cocktail.bean.model.Cocktail;
import com.devlight.test.cocktail.bean.model.Drinks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI {

    @GET("/api/json/v1/1/search.php")
    public Call<Drinks> getCocktails(@Query("s") String s);

}
