package com.example.adndi___project2.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adndi___project2.R;
import com.example.adndi___project2.RecipeStepsActivity;
import com.example.adndi___project2.app_adapters.RecyclerAdapterOnClickHandler;
import com.example.adndi___project2.app_adapters.StepsRecyclerAdapter;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeSteps;
import com.example.adndi___project2.recipe_utilities.DataUtilities;
import com.example.adndi___project2.view_model.GetStepsViewModel;
import com.example.adndi___project2.view_model.GetStepsViewModelFactory;

import java.util.List;

import timber.log.Timber;

/**
 * Classe de Fragment que implementa a lista de passos nos detalhes
 */
public class RecipeStepsFragment extends Fragment implements RecyclerAdapterOnClickHandler {

    private int mRecipeId = 0;

    private Boolean mTwoPane;

    public RecipeStepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_steps_fragment, container, false);

        mTwoPane = getResources().getBoolean(R.bool.tablet);

        if (getArguments() != null && getArguments().containsKey( DataUtilities.ID_INTENT_EXTRA )) {
            mRecipeId = getArguments().getInt( DataUtilities.ID_INTENT_EXTRA );
        }

        final RecyclerView recyclerView = rootView.findViewById(R.id.rv_steps_list);

        Timber.d("RecyclerView: %s", recyclerView.toString());

        stepsViewModel(recyclerView);

        return rootView;
    }

    /**
     * ViemModel para receber a lista de passos da Receita
     */
    private void stepsViewModel(final RecyclerView recyclerView) {

        RecipeDatabase mDb = RecipeDatabase.getInstance(getContext());

        GetStepsViewModelFactory factory = new GetStepsViewModelFactory(mDb, mRecipeId, 0);
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

    /**
     * Override do onCLick, a depender se é um tablet ou telefone, irá ou abrir o
     * viewpager na pagina do respectivo passo, ou abrir a RecipeStepsActivity
     */
    @Override
    public void onClick(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        if (mTwoPane) {
            RecipeViewPagerFragment detailsViewPager = new RecipeViewPagerFragment();
            Bundle arguments = new Bundle();
            arguments.putInt( DataUtilities.ID_INTENT_EXTRA, mRecipeId );
            arguments.putInt( DataUtilities.PAGER_ORDER_EXTRA, position + 1 );
            detailsViewPager.setArguments(arguments);
            Timber.d("DetailsFragment: %s", detailsViewPager.toString());
            fragmentManager.beginTransaction().replace(R.id.fl_detail_container, detailsViewPager)
                    .commit();
        } else {
            Context context = getContext();
            Class destinationClass = RecipeStepsActivity.class;
            Intent stepsIntent = new Intent(context, destinationClass);
            stepsIntent.putExtra(DataUtilities.ID_INTENT_EXTRA, mRecipeId);
            stepsIntent.putExtra(DataUtilities.PAGER_ORDER_EXTRA, position + 1);
            startActivity(stepsIntent);
        }
    }
}
