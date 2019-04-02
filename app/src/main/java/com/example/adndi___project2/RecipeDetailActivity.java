package com.example.adndi___project2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.adndi___project2.DataBase.RecipeData;
import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.Fragments.RecipeIngredientsFragment;
import com.example.adndi___project2.Fragments.RecipeStepsFragment;
import com.example.adndi___project2.Fragments.RecipeViewPagerFragment;
import com.example.adndi___project2.RecipeUtilities.DataUtilities;
import com.example.adndi___project2.ViewModel.GetRecipeViewModel;
import com.example.adndi___project2.ViewModel.GetRecipeViewModelFactory;

import timber.log.Timber;


public class RecipeDetailActivity extends AppCompatActivity {

    private int mRecipeId = 0;
    private boolean mTwoPane;
    private RecipeDatabase mDb;
    FragmentManager fragmentManager;
    ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);

        mTwoPane = getResources().getBoolean(R.bool.tablet);

        toolbar = getSupportActionBar();

        mDb = RecipeDatabase.getInstance(this);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(DataUtilities.ID_INTENT_EXTRA)) {
            mRecipeId = getIntent().getIntExtra(DataUtilities.ID_INTENT_EXTRA, 0);
        }

        detailsViewModel();

        fragmentManager = getSupportFragmentManager();
        Bundle arguments = new Bundle();
        arguments.putInt( DataUtilities.ID_INTENT_EXTRA, mRecipeId );

        RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();
        ingredientsFragment.setArguments( arguments );
        fragmentManager.beginTransaction()
                .add(R.id.fl_ingredient_list_container, ingredientsFragment)
                .commit();

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        stepsFragment.setArguments( arguments );
        fragmentManager.beginTransaction()
                .add(R.id.fl_steps_list_container, stepsFragment)
                .commit();

        if (mTwoPane) {
            RecipeViewPagerFragment detailsViewPager = new RecipeViewPagerFragment();
            detailsViewPager.setArguments(arguments);
            fragmentManager.beginTransaction().add(R.id.fl_detail_container, detailsViewPager)
                    .commit();
        }
    }


    private void detailsViewModel() {

        GetRecipeViewModelFactory factory = new GetRecipeViewModelFactory(mDb, mRecipeId);
        final GetRecipeViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetRecipeViewModel.class);

        viewModel.getRecipe().observe(this, new Observer<RecipeData>() {
            @Override
            public void onChanged(@Nullable RecipeData recipeData) {
                String recipeName = recipeData.getRecipeName();
                Timber.d("recipeName: %s", recipeName);
                toolbar.setTitle(recipeData.getRecipeName());
            }
        });
    }

}
