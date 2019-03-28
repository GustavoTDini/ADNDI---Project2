package com.example.adndi___project2.AppAdaptersAndViewHolders;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.adndi___project2.Fragments.RecipeStepsDetailsFragment;
import com.example.adndi___project2.R;

public class StepsViewPagerAdapter extends FragmentStatePagerAdapter{

    private int mSteps = 0;

    public StepsViewPagerAdapter(FragmentManager fm) {
        super( fm );
    }

    @Override
    public Fragment getItem(int i) {
//        if (i == 0){
//        }
        return new RecipeStepsDetailsFragment();
    }

    @Override
    public int getCount() {
        return mSteps;
    }

    public void setSteps(int stepsNumber){
        mSteps = stepsNumber;
    }
}
