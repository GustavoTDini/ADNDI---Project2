package com.example.adndi___project2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adndi___project2.AppAdaptersAndViewHolders.StepsViewPagerAdapter;
import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeSteps;
import com.example.adndi___project2.R;
import com.example.adndi___project2.ViewModel.GetStepsViewModel;
import com.example.adndi___project2.ViewModel.GetStepsViewModelFactory;

import java.util.List;

import timber.log.Timber;


public class RecipeViewPager extends Fragment {

    private int mRecipeId = 0;

    private List<RecipeSteps> mSteps;

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;


    public RecipeViewPager() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_steps_pager_fragment, container, false);

        Timber.d("RecipeID: %s", mRecipeId);

        stepsViewModel(rootView);

        return rootView;
    }


    private void setViewPager(View view, List<RecipeSteps> steps) {
        mPager = view.findViewById(R.id.vp_details);
        pagerAdapter = new StepsViewPagerAdapter(getFragmentManager());
        mPager.setAdapter(pagerAdapter);
        Timber.d("Steps %s", mSteps);
        ((StepsViewPagerAdapter) pagerAdapter).setSteps(steps.size() + 1);
        ((StepsViewPagerAdapter) pagerAdapter).getRecipeId(mRecipeId);
    }

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

    private void stepsViewModel(final View view) {

        RecipeDatabase mDb = RecipeDatabase.getInstance(getContext());

        GetStepsViewModelFactory factory = new GetStepsViewModelFactory(mDb, mRecipeId, 0);
        final GetStepsViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetStepsViewModel.class);

        viewModel.getSteps().observe(this, new Observer<List<RecipeSteps>>() {
            @Override
            public void onChanged(@Nullable List<RecipeSteps> recipeSteps) {
                setViewPager(view, recipeSteps);

            }
        });
    }

    public void setViewPagerPage(int i) {
        mPager.setCurrentItem(i);
    }

}
