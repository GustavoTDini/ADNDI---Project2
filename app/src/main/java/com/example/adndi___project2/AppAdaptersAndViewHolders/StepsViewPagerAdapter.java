package com.example.adndi___project2.AppAdaptersAndViewHolders;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.adndi___project2.Fragments.RecipeViewPagerFragment;
import com.example.adndi___project2.Fragments.StepViewPagerFragment;

import timber.log.Timber;

public class StepsViewPagerAdapter extends FragmentStatePagerAdapter{

    private int mSteps = 0;

    private int mRecipeId;

    public StepsViewPagerAdapter(FragmentManager fm) {
        super( fm );
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            RecipeViewPagerFragment recipeViewPagerFragment = new RecipeViewPagerFragment();
            recipeViewPagerFragment.setRecipe(mRecipeId);
            return recipeViewPagerFragment;
        } else {
            StepViewPagerFragment stepsViewPagerFragment = new StepViewPagerFragment();
            stepsViewPagerFragment.setRecipe(mRecipeId);
            stepsViewPagerFragment.setOrder(i - 1);
            return stepsViewPagerFragment;
        }

    }

    @Override
    public int getCount() {
        Timber.d("viewPager: %s", mSteps);
        return mSteps;
    }

    public void setSteps(int stepsNumber){
        mSteps = stepsNumber;
        notifyDataSetChanged();
        Timber.d("viewPager: %s", mSteps);
    }

    public void getRecipeId(int recipeId) {
        mRecipeId = recipeId;
        notifyDataSetChanged();
        Timber.d("viewPager: %s", mRecipeId);
    }
}
