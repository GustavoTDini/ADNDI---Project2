package com.example.adndi___project2.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adndi___project2.AppAdaptersAndViewHolders.RecyclerAdapterOnClickHandler;
import com.example.adndi___project2.AppAdaptersAndViewHolders.StepsRecyclerAdapter;
import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeSteps;
import com.example.adndi___project2.R;
import com.example.adndi___project2.ViewModel.GetStepsViewModel;
import com.example.adndi___project2.ViewModel.GetStepsViewModelFactory;

import java.util.List;

import timber.log.Timber;

public class RecipeStepsFragment extends Fragment implements RecyclerAdapterOnClickHandler {

    private int mRecipeId = 0;

    private RecipeDatabase mDb;

    public RecipeStepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_steps_fragment, container, false);

        mDb = RecipeDatabase.getInstance(getContext());

        final RecyclerView recyclerView = rootView.findViewById(R.id.rv_steps_list);

        Timber.d("RecyclerView: %s", recyclerView.toString());

        stepsViewModel(recyclerView);

        return rootView;
    }

    private void stepsViewModel(final RecyclerView recyclerView) {

        GetStepsViewModelFactory factory = new GetStepsViewModelFactory(mDb, mRecipeId);
        final GetStepsViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetStepsViewModel.class);

        viewModel.getSteps().observe(this, new Observer<List<RecipeSteps>>() {
            @Override
            public void onChanged(@Nullable List<RecipeSteps> recipeSteps) {
                setupRecyclerView(recyclerView, recipeSteps);

            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<RecipeSteps> recipesStep) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        StepsRecyclerAdapter mStepsAdapter = new StepsRecyclerAdapter(this);
        mStepsAdapter.setRecipeData(recipesStep);
        recyclerView.setAdapter(mStepsAdapter);
    }

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

    @Override
    public void onClick(int position) {

    }
}
