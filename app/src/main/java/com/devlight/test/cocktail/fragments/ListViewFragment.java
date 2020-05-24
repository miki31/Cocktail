package com.devlight.test.cocktail.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devlight.test.cocktail.R;
import com.devlight.test.cocktail.bean.model.Cocktail;
import com.devlight.test.cocktail.bean.model.Drinks;
import com.devlight.test.cocktail.network.NetworkService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewFragment extends Fragment {

    Drinks mDrinks;
    TextView mTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testREST();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);

        mTextView = (TextView) v.findViewById(R.id.textid);

        return v;
    }

    private void testREST(){

        NetworkService.getInstance()
                .getJSONApi()
                .getCocktails("margarita")
                .enqueue(new Callback<Drinks>() {
                    @Override
                    public void onResponse(@NonNull Call<Drinks> call, @NonNull Response<Drinks> response) {
                        mDrinks = response.body();
                        List<Cocktail> cocktails = mDrinks.getCocktails();

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
