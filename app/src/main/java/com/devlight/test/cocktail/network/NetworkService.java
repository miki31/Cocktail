package com.devlight.test.cocktail.network;

import com.devlight.test.cocktail.network.rest.JsonPlaceHolderAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// singleton object
public class NetworkService {

    private static NetworkService sNetworkServiceInstance;

    // TODO change after testing
    private static final String BASE_URL = "https://www.thecocktaildb.com";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (sNetworkServiceInstance == null)
            sNetworkServiceInstance = new NetworkService();

        return sNetworkServiceInstance;
    }

    public JsonPlaceHolderAPI getJSONApi() {
        return mRetrofit.create(JsonPlaceHolderAPI.class);
    }
}
