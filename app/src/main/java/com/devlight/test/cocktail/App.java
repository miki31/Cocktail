package com.devlight.test.cocktail;

import android.app.Application;

import com.devlight.test.cocktail.bean.model.Cocktail;
import com.devlight.test.cocktail.dao.CocktailDAO;
import com.devlight.test.cocktail.db.AppDatabase;

import java.util.List;

import androidx.room.Room;

public class App extends Application {
    private static App instance;

    private static final String DATABASE_NAME = "CocktailsCatalog";

    private AppDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        createDB();

        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    private void createDB(){
        mDatabase = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                DATABASE_NAME
        ).build();
    }

    public void initDB() {
        CocktailDAO eDAO = mDatabase.mCocktailDAO();
    }

    public AppDatabase getDatabase() {
        return mDatabase;
    }
}
