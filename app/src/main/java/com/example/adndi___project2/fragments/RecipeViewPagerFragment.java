package com.example.adndi___project2.fragments;

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

import com.example.adndi___project2.R;
import com.example.adndi___project2.app_adapters.StepsViewPagerAdapter;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeSteps;
import com.example.adndi___project2.recipe_utilities.DataUtilities;
import com.example.adndi___project2.view_model.GetStepsViewModel;
import com.example.adndi___project2.view_model.GetStepsViewModelFactory;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.List;

import timber.log.Timber;


public class RecipeViewPagerFragment extends Fragment {

    private final int DEFAULT_CURRENT_PAGE = -1;

    private int mRecipeId = 0;

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    private int currentPage = DEFAULT_CURRENT_PAGE;


    public RecipeViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recipe_steps_pager_fragment, container, false);

        Timber.d("RecipeID: %s", mRecipeId);

        if (getArguments() != null && getArguments().containsKey( DataUtilities.ID_INTENT_EXTRA )) {
            mRecipeId = getArguments().getInt( DataUtilities.ID_INTENT_EXTRA );
        }

        if (getArguments() != null && getArguments().containsKey(DataUtilities.PAGER_ORDER_EXTRA)) {
            currentPage = getArguments().getInt(DataUtilities.PAGER_ORDER_EXTRA);
        }

        stepsViewModel(rootView);

        return rootView;
    }


    private void setViewPager(View view, List<RecipeSteps> steps) {
        mPager = view.findViewById(R.id.vp_details);
        pagerAdapter = new StepsViewPagerAdapter(getFragmentManager());
        mPager.setAdapter(pagerAdapter);
        ((StepsViewPagerAdapter) pagerAdapter).setSteps(steps.size() + 1);
        ((StepsViewPagerAdapter) pagerAdapter).getRecipeId(mRecipeId);

        // Create an object of page transformer
        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
        bookFlipPageTransformer.setEnableScale(true);
        bookFlipPageTransformer.setScaleAmountPercent(10f);
        mPager.setPageTransformer(true, bookFlipPageTransformer);

        if (currentPage != DEFAULT_CURRENT_PAGE) {
            mPager.setCurrentItem(currentPage, true);
        }

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

}
