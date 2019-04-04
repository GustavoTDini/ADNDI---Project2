package com.example.adndi___project2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.adndi___project2.app_adapters.StepsViewPagerAdapter;
import com.example.adndi___project2.database.RecipeData;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeSteps;
import com.example.adndi___project2.recipe_utilities.DataUtilities;
import com.example.adndi___project2.view_model.GetRecipeViewModel;
import com.example.adndi___project2.view_model.GetRecipeViewModelFactory;
import com.example.adndi___project2.view_model.GetStepsViewModel;
import com.example.adndi___project2.view_model.GetStepsViewModelFactory;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.List;

import timber.log.Timber;

/**
 * Classe da RecipeDetailActivity, ele será mostrado caso esteja com um telefone e dependendo do tablet
 * em modo retrato
 */
public class RecipeStepsActivity extends AppCompatActivity {

    private int mRecipeId = 0;
    private int mInitialStepOrder = 0;

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    ActionBar toolbar;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_steps_pager_fragment);

        Intent intentThatStartedThisActivity = getIntent();

        toolbar = getSupportActionBar();

        if (intentThatStartedThisActivity.hasExtra(DataUtilities.ID_INTENT_EXTRA)) {
            mRecipeId = getIntent().getIntExtra(DataUtilities.ID_INTENT_EXTRA, 0);
            mInitialStepOrder = getIntent().getIntExtra(DataUtilities.PAGER_ORDER_EXTRA, 0);
            Timber.d( "currentPage %s", mInitialStepOrder );
        }

        stepsViewModel();

    }

    /**
     * Metodo que cria o viewPager
     */
    private void setViewPager(List<RecipeSteps> steps) {
        mPager = findViewById(R.id.vp_details);
        FragmentManager fragmentManager = getSupportFragmentManager();
        pagerAdapter = new StepsViewPagerAdapter(fragmentManager);
        mPager.setAdapter(pagerAdapter);

        // Create an object of page transformer
        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
        bookFlipPageTransformer.setEnableScale(true);
        bookFlipPageTransformer.setScaleAmountPercent(10f);

        mPager.setPageTransformer(true, bookFlipPageTransformer);

        ((StepsViewPagerAdapter) pagerAdapter).setSteps(steps.size() + 1);
        ((StepsViewPagerAdapter) pagerAdapter).getRecipeId(mRecipeId);
        mPager.setCurrentItem(mInitialStepOrder);
    }

    /**
     * ViemModel para receber a lista de passos da Receita e o titulo da receita
     */
    private void stepsViewModel() {

        RecipeDatabase mDb = RecipeDatabase.getInstance(getApplicationContext());

        GetStepsViewModelFactory stepFactory = new GetStepsViewModelFactory(mDb, mRecipeId, 0);
        final GetStepsViewModel stepViewModel
                = ViewModelProviders.of(this, stepFactory).get(GetStepsViewModel.class);

        stepViewModel.getSteps().observe(this, new Observer<List<RecipeSteps>>() {
            @Override
            public void onChanged(@Nullable List<RecipeSteps> recipeSteps) {
                setViewPager(recipeSteps);
            }
        });

        GetRecipeViewModelFactory recipeFactory = new GetRecipeViewModelFactory(mDb, mRecipeId);
        final GetRecipeViewModel recipeViewModel
                = ViewModelProviders.of(this, recipeFactory).get(GetRecipeViewModel.class);

        recipeViewModel.getRecipe().observe(this, new Observer<RecipeData>() {
            @Override
            public void onChanged(@Nullable RecipeData recipe) {
                toolbar.setTitle(recipe.getRecipeName());
            }
        });
    }
}
