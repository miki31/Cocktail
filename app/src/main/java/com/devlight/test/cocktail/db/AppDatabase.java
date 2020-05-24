package com.devlight.test.cocktail.db;

import com.devlight.test.cocktail.bean.model.Cocktail;
import com.devlight.test.cocktail.dao.CocktailDAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cocktail.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CocktailDAO mCocktailDAO();

}
