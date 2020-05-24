package com.devlight.test.cocktail.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devlight.test.cocktail.R;
import com.devlight.test.cocktail.bean.model.Cocktail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailsFragmentCocktail extends Fragment {

    private Cocktail mCocktail;

    private ImageView mImgCocktail;
    private TextView mTvName;


    public DetailsFragmentCocktail(){}

    public static DetailsFragmentCocktail newInstance(Cocktail cocktail){
        DetailsFragmentCocktail fragmentCocktail = new DetailsFragmentCocktail();

        fragmentCocktail.mCocktail = cocktail;

        return fragmentCocktail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null){
            // get parameters from bundle
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragm_cocktail_details,
                container, false);

        // find view elements
        mImgCocktail = view.findViewById(R.id.img_detail);
        mTvName = view.findViewById(R.id.tv_detail_name);


        updateForm();

        return view;
    }

    private void updateForm(){
        if (mCocktail == null)
            return;


        Glide.with(getActivity())
                .load(mCocktail.getStrDrinkThumb())
                .into(mImgCocktail);
        mTvName.setText(mCocktail.getStrDrink());
    }
}
