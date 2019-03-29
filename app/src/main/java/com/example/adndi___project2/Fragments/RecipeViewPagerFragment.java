package com.example.adndi___project2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adndi___project2.DataBase.RecipeData;
import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.R;
import com.example.adndi___project2.ViewModel.GetRecipeViewModel;
import com.example.adndi___project2.ViewModel.GetRecipeViewModelFactory;

public class RecipeViewPagerFragment extends Fragment {

    private int mRecipeId = 0;

    private TextView titleTV;

    public RecipeViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_first_page, container, false);

        recipeViewModel();

        titleTV = rootView.findViewById(R.id.tv_detail_servings);

        return rootView;
    }

    public void setRecipe(int recipeId) {
        mRecipeId = recipeId;
    }

    private void recipeViewModel() {
        RecipeDatabase mDb = RecipeDatabase.getInstance(getContext());

        GetRecipeViewModelFactory factory = new GetRecipeViewModelFactory(mDb, mRecipeId);
        final GetRecipeViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetRecipeViewModel.class);

        viewModel.getRecipe().observe(this, new Observer<RecipeData>() {
            @Override
            public void onChanged(@Nullable RecipeData recipeData) {
                titleTV.setText(recipeData.getRecipeName());
            }
        });
    }
}
