package com.devlight.test.cocktail.bean.model;

import com.devlight.test.cocktail.dao.CocktailDAO;

import java.util.List;

public class CocktailModel {

    private CocktailDAO mCocktailDAO;

    public CocktailModel(CocktailDAO cocktailDAO) {
        mCocktailDAO = cocktailDAO;
    }

    public List<Cocktail> getAll(){
        return mCocktailDAO.getAll();
    }

    public void insert(Cocktail cocktail) {
        Cocktail cocktail1 = mCocktailDAO.getById(cocktail.getIdDrink());

        if (cocktail1 == null)
            mCocktailDAO.insert(cocktail);
    }
}
