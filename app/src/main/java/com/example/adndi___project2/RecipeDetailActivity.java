package com.example.adndi___project2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adndi___project2.DataBase.RecipeData;
import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.Fragments.RecipeIngredientsFragment;
import com.example.adndi___project2.Fragments.RecipeStepsFragment;
import com.example.adndi___project2.RecipeUtilities.DataUtilities;
import com.example.adndi___project2.ViewModel.GetRecipeViewModel;
import com.example.adndi___project2.ViewModel.GetRecipeViewModelFactory;

import timber.log.Timber;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    TextView mRecipeTitleTv;
    private int mRecipeId = 0;
    private RecipeData mRecipe;
    private boolean mTwoPane;
    private RecipeDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);

        mRecipeTitleTv = findViewById(R.id.tv_recipe_title);


        mDb = RecipeDatabase.getInstance(this);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(DataUtilities.ID_INTENT_EXTRA)) {
            mRecipeId = getIntent().getIntExtra(DataUtilities.ID_INTENT_EXTRA, 0);
            Timber.d("onCreate: %s", String.valueOf(mRecipeId));
            Toast.makeText(this, String.valueOf(mRecipeId), Toast.LENGTH_LONG).show();
        }

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        detailsViewModel();

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();
        ingredientsFragment.setRecipeId(mRecipeId);
        fragmentManager.beginTransaction()
                .add(R.id.fl_ingredient_list_container, ingredientsFragment)
                .commit();

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        stepsFragment.setRecipeId(mRecipeId);
        fragmentManager.beginTransaction()
                .add(R.id.fl_steps_list_container, stepsFragment)
                .commit();

    }


    private void detailsViewModel() {

        GetRecipeViewModelFactory factory = new GetRecipeViewModelFactory(mDb, mRecipeId);
        final GetRecipeViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetRecipeViewModel.class);

        viewModel.getRecipe().observe(this, new Observer<RecipeData>() {
            @Override
            public void onChanged(@Nullable RecipeData recipeData) {
                mRecipe = recipeData;
                mRecipeTitleTv.setText(mRecipe.getRecipeName());
            }
        });
    }

}
