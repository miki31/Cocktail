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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devlight.test.cocktail.App;
import com.devlight.test.cocktail.R;
import com.devlight.test.cocktail.bean.model.Cocktail;
import com.devlight.test.cocktail.bean.model.CocktailModel;
import com.devlight.test.cocktail.bean.model.Drinks;
import com.devlight.test.cocktail.dao.CocktailDAO;
import com.devlight.test.cocktail.network.NetworkService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewFragment extends Fragment {
    private static final String TAG = "tag_ list cocktails Fragment";

    private CocktailModel model;

    private Drinks mDrinks;
    FloatingActionButton fab;

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CocktailDAO dao = App.getInstance().getDatabase().mCocktailDAO();
        model = new CocktailModel(dao);

        initDB();

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);

        mRecyclerView = v.findViewById(R.id.list_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        setupAdapter();

        fab = v.findViewById(R.id.fab);

        return v;
    }

    private void initDB() {
        new Thread(() -> {

            App.getInstance().initDB();

            mDrinks = new Drinks();
            mDrinks.setCocktails(model.getAll());
        }).start();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_main_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        fab.setOnClickListener(view -> {
            searchItem.expandActionView();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("LongLogTag")
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Query Text Submit: " + query);
                fetchQueryREST(query);
                return false;
            }

            @SuppressLint("LongLogTag")
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Query Text change: " + newText);
                return false;
            }
        });

        searchView.setOnSearchClickListener(v -> {
            Log.d(TAG, "setOnSearchClickListener()");
        });
    }

    private void fetchQueryREST(String strQuery){
        NetworkService.getInstance()
                .getJSONApi()
                .getCocktails(strQuery)
                .enqueue(new Callback<Drinks>() {
                    @Override
                    public void onResponse(@NonNull Call<Drinks> call, @NonNull Response<Drinks> response) {
                        if (!response.isSuccessful())
                            return;
                        mDrinks = response.body();

                        setupAdapter();

                        if (mDrinks == null)
                            return;
                        if (mDrinks.getCocktails() == null)
                            return;
                        if (mDrinks.getCocktails().size() == 0)
                            return;
                        List<Cocktail> cocktails = mDrinks.getCocktails();
                    }

                    @Override
                    public void onFailure(Call<Drinks> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    private void setupAdapter() {
        if (isAdded()) {
            mRecyclerView.setAdapter(new ImagePhotoAdapter(mDrinks));
        }
    }


    private class ImagePhotoHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{
        private Cocktail mCocktail;

        private ImageView mImgItemCocktail;
        private TextView mTvNameItemCocktail;

        public ImagePhotoHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ImagePhotoHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_cocktail,
                    parent, false));

            itemView.setOnClickListener(this);

            mImgItemCocktail = (ImageView) itemView.findViewById(R.id.img_item_cocktail);
            mTvNameItemCocktail = (TextView) itemView.findViewById(R.id.tv_name_item_cocktail);
        }

        public void bind( Cocktail cocktail){
            mCocktail = cocktail;

            Glide.with(getActivity())
                    .load(cocktail.getStrDrinkThumb())
                    .into(mImgItemCocktail);

            mTvNameItemCocktail.setText(cocktail.getStrDrink());
        }

        @Override
        public void onClick(View v) {
            new Thread(() -> {
                model.insert(mCocktail);
            }).start();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            DetailsFragmentCocktail detailsFragmentCocktail =
                    DetailsFragmentCocktail.newInstance(mCocktail);

//            Bundle args = new Bundle();

            fragmentTransaction.replace(R.id.fragment_container, detailsFragmentCocktail);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }

    private class ImagePhotoAdapter extends RecyclerView.Adapter<ImagePhotoHolder> {
        private Drinks mDrinks;

        public ImagePhotoAdapter(Drinks drinks) {
            mDrinks = drinks;
        }

        @NonNull
        @Override
        public ImagePhotoHolder onCreateViewHolder(
                @NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new ImagePhotoHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(
                @NonNull ImagePhotoHolder holder, int position) {
            if (mDrinks == null)
                return;
            if (mDrinks.getCocktails() == null)
                return;
            if (mDrinks.getCocktails().size() == 0)
                return;
            Cocktail cocktail = mDrinks.getCocktails().get(position);
            holder.bind(cocktail);
        }

        @Override
        public int getItemCount() {
            if (mDrinks == null)
                return 0;
            if (mDrinks.getCocktails() == null)
                return 0;
            return mDrinks.getCocktails().size();
        }

        public Drinks getDrinks() {
            return mDrinks;
        }

        public void setDrinks(Drinks drinks) {
            mDrinks = drinks;
        }
    }
}
