package com.example.adndi___project2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adndi___project2.DataBase.RecipeData;
import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.R;
import com.example.adndi___project2.RecipeUtilities.DataUtilities;
import com.example.adndi___project2.ViewModel.GetRecipeViewModel;
import com.example.adndi___project2.ViewModel.GetRecipeViewModelFactory;

public class FirstPageViewPagerFragment extends Fragment {

    private int mRecipeId = 0;

    private TextView mServingsTV;
    private TextView mTitleTV;
    private ImageView mRecipeImageIV;

    public FirstPageViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_first_page, container, false);

        if (getArguments() != null && getArguments().containsKey(DataUtilities.ID_INTENT_EXTRA)) {
            mRecipeId = getArguments().getInt(DataUtilities.ID_INTENT_EXTRA);
        }

        mTitleTV = rootView.findViewById(R.id.tv_recipe_title_vp);
        mServingsTV = rootView.findViewById(R.id.tv_recipe_servings_vp);
        mRecipeImageIV = rootView.findViewById(R.id.iv_recipe_image_vp);

        recipeViewModel();

        return rootView;
    }

    private void recipeViewModel() {
        RecipeDatabase mDb = RecipeDatabase.getInstance(getContext());

        GetRecipeViewModelFactory factory = new GetRecipeViewModelFactory(mDb, mRecipeId);
        final GetRecipeViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetRecipeViewModel.class);

        viewModel.getRecipe().observe(this, new Observer<RecipeData>() {
            @Override
            public void onChanged(@Nullable RecipeData recipeData) {
                mTitleTV.setText(recipeData.getRecipeName());
                mServingsTV.setText(String.valueOf(recipeData.getRecipeServings()));
                Glide.with(getContext()).load(recipeData.getRecipeImageUrl()).into(mRecipeImageIV);
            }
        });
    }
}
