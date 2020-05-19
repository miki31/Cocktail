package com.devlight.test.cocktail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.devlight.test.cocktail.bean.model.Cocktail;
import com.devlight.test.cocktail.bean.model.Drinks;
import com.devlight.test.cocktail.network.NetworkService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textid);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            //TODO TEST
            testREST();
        });



    }

    private void testREST(){

        NetworkService.getInstance()
                .getJSONApi()
                .getCocktails("margarita")
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


}
