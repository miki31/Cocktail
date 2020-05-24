package com.devlight.test.cocktail.dao;

import com.devlight.test.cocktail.bean.model.Cocktail;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CocktailDAO {

    @Query("SELECT * FROM cocktail")
    List<Cocktail> getAll();

    @Query("SELECT * FROM cocktail WHERE mIdDrink = :mIdDrink")
    Cocktail getById(String mIdDrink);

    @Insert
    void insert(Cocktail element);

    @Update
    void update(Cocktail element);

    @Delete
    void delete(Cocktail element);

}
