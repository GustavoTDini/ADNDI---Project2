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

import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeSteps;
import com.example.adndi___project2.R;
import com.example.adndi___project2.RecipeUtilities.DataUtilities;
import com.example.adndi___project2.ViewModel.GetStepsViewModel;
import com.example.adndi___project2.ViewModel.GetStepsViewModelFactory;

import timber.log.Timber;

public class StepViewPagerFragment extends Fragment {

    private int mRecipeId = 0;

    private int mStepOrder;

    private TextView titleTV;

    public StepViewPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.steps_details_content, container, false);

        if (getArguments() != null && getArguments().containsKey( DataUtilities.ID_INTENT_EXTRA )) {
            mRecipeId = getArguments().getInt( DataUtilities.ID_INTENT_EXTRA );
        }

        if (getArguments() != null && getArguments().containsKey( DataUtilities.PAGER_ORDER_EXTRA )) {
            mStepOrder = getArguments().getInt( DataUtilities.PAGER_ORDER_EXTRA );
        }

        stepsViewModel();

        titleTV = rootView.findViewById(R.id.tv_step_details_description);

        return rootView;
    }

    private void stepsViewModel() {
        RecipeDatabase mDb = RecipeDatabase.getInstance(getContext());

        GetStepsViewModelFactory factory = new GetStepsViewModelFactory(mDb, mRecipeId, mStepOrder);
        final GetStepsViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetStepsViewModel.class);

        viewModel.getStepsOrder().observe(this, new Observer<RecipeSteps>() {
            @Override
            public void onChanged(@Nullable RecipeSteps recipeStep) {
                Timber.d("StepViewPager: viewModel -> %s", recipeStep);
                titleTV.setText(recipeStep.getStepDescription());
            }
        });
    }

}
