package com.example.adndi___project2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.adndi___project2.AppAdaptersAndViewHolders.StepsViewPagerAdapter;
import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeSteps;
import com.example.adndi___project2.RecipeUtilities.DataUtilities;
import com.example.adndi___project2.ViewModel.GetStepsViewModel;
import com.example.adndi___project2.ViewModel.GetStepsViewModelFactory;

import java.util.List;

import timber.log.Timber;

public class RecipeStepsActivity extends AppCompatActivity {

    private int mRecipeId = 0;
    private int mInitialStepOrder = 0;

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_steps_pager_fragment);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(DataUtilities.ID_INTENT_EXTRA)) {
            mRecipeId = getIntent().getIntExtra(DataUtilities.ID_INTENT_EXTRA, 0);
            mInitialStepOrder = getIntent().getIntExtra(DataUtilities.PAGER_ORDER_EXTRA, 0);
            Timber.d( "currentPage %s", mInitialStepOrder );
        }

        stepsViewModel();

    }


    private void setViewPager(List<RecipeSteps> steps) {
        mPager = findViewById(R.id.vp_details);
        FragmentManager fragmentManager = getSupportFragmentManager();
        pagerAdapter = new StepsViewPagerAdapter(fragmentManager);
        mPager.setAdapter(pagerAdapter);
        ((StepsViewPagerAdapter) pagerAdapter).setSteps(steps.size() + 1);
        ((StepsViewPagerAdapter) pagerAdapter).getRecipeId(mRecipeId);
        mPager.setCurrentItem(mInitialStepOrder);
    }

    private void stepsViewModel() {

        RecipeDatabase mDb = RecipeDatabase.getInstance(getApplicationContext());

        GetStepsViewModelFactory factory = new GetStepsViewModelFactory(mDb, mRecipeId, 0);
        final GetStepsViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetStepsViewModel.class);

        viewModel.getSteps().observe(this, new Observer<List<RecipeSteps>>() {
            @Override
            public void onChanged(@Nullable List<RecipeSteps> recipeSteps) {
                setViewPager(recipeSteps);

            }
        });
    }
}
