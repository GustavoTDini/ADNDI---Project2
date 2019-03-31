package com.example.adndi___project2.AppAdaptersAndViewHolders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.adndi___project2.Fragments.RecipeViewPagerFragment;
import com.example.adndi___project2.Fragments.StepViewPagerFragment;
import com.example.adndi___project2.RecipeUtilities.DataUtilities;

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
            Bundle arguments = new Bundle();
            arguments.putInt( DataUtilities.ID_INTENT_EXTRA, mRecipeId );
            recipeViewPagerFragment.setArguments( arguments );
            return recipeViewPagerFragment;
        } else {
            StepViewPagerFragment stepsViewPagerFragment = new StepViewPagerFragment();
            Bundle arguments = new Bundle();
            arguments.putInt( DataUtilities.ID_INTENT_EXTRA, mRecipeId );
            arguments.putInt( DataUtilities.PAGER_ORDER_EXTRA, i-1 );
            stepsViewPagerFragment.setArguments( arguments );
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
