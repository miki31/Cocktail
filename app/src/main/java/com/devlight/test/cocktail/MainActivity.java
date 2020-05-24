package com.devlight.test.cocktail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.devlight.test.cocktail.bean.model.Cocktail;
import com.devlight.test.cocktail.bean.model.Drinks;
import com.devlight.test.cocktail.network.NetworkService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textid);

        handleIntent(getIntent());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            //TODO TEST
            testREST("margarita");
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);

            testREST(query);
        }
    }

    private void testREST(String strSearch){

        NetworkService.getInstance()
                .getJSONApi()
                .getCocktails(strSearch)
                .enqueue(new Callback<Drinks>() {
                    @Override
                    public void onResponse(@NonNull Call<Drinks> call, @NonNull Response<Drinks> response) {
                        Drinks drinks = response.body();
                        List<Cocktail> cocktails = drinks.getCocktails();

                        final String[] listNames = {""};

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            cocktails.forEach(
                                    cocktail->{
                                        listNames[0] = listNames[0] + cocktail.getStrDrink() + "\n";
                                    }
                            );
                        } else {
                            for (Cocktail cocktail :
                                    cocktails) {
                                listNames[0] = listNames[0] + cocktail.getStrDrink() + "\n";
                            }
                        }

                        mTextView.setText(listNames[0]);
                    }

                    @Override
                    public void onFailure(Call<Drinks> call, Throwable t) {
                        mTextView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        return true;
    }
}
