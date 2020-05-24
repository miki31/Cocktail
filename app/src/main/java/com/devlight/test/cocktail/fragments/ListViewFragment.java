package com.devlight.test.cocktail.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devlight.test.cocktail.R;
import com.devlight.test.cocktail.bean.model.Cocktail;
import com.devlight.test.cocktail.bean.model.Drinks;
import com.devlight.test.cocktail.network.NetworkService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewFragment extends Fragment {
    private static final String TAG = "tag_ list cocktails Fragment";

    Drinks mDrinks;
    TextView mTextView;
    FloatingActionButton fab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
//        testREST();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);

        mTextView = (TextView) v.findViewById(R.id.textid);

        fab = v.findViewById(R.id.fab);


        return v;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_main_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        fab.setOnClickListener(view -> {
            //TODO TEST
            testREST("margarita");
            searchItem.expandActionView();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("LongLogTag")
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Query Text Submit: " + query);
                mTextView.setText("onQueryTextSubmit(): "+query);
                testREST(query);
                return false;
            }

            @SuppressLint("LongLogTag")
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Query Text change: " + newText);
                mTextView.setText("onQueryTextChange(): " + newText);
                return false;
            }
        });

        searchView.setOnSearchClickListener(v -> {
            Log.d(TAG, "setOnSearchClickListener()");
//            searchView.setQuery(query, false);
        });
    }

    private void testREST(String strQuery){

        NetworkService.getInstance()
                .getJSONApi()
                .getCocktails(strQuery)
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
