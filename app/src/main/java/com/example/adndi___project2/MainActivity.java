package com.example.adndi___project2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.adndi___project2.app_adapters.RecipesRecyclerAdapter;
import com.example.adndi___project2.app_adapters.RecyclerAdapterOnClickHandler;
import com.example.adndi___project2.database.RecipeData;
import com.example.adndi___project2.recipe_utilities.AppExecutors;
import com.example.adndi___project2.recipe_utilities.DataUtilities;
import com.example.adndi___project2.recipe_utilities.JsonUtilities;
import com.example.adndi___project2.recipe_utilities.NetworkUtilities;
import com.example.adndi___project2.view_model.RecipeViewModel;
import com.example.adndi___project2.widget.RecipeAppWidget;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.io.IOException;
import java.util.List;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements RecyclerAdapterOnClickHandler {

    // int com o total de View do grid em Modo paisagem
    private static final int LANDSCAPE_SPAM_PHONE = 2;

    // int com o total de View do grid em Modo retrato
    private static final int PORTRAIT_SPAM_PHONE = 1;

    // int com o total de View do grid em Modo paisagem
    private static final int LANDSCAPE_SPAM_TABLET = 3;

    // int com o total de View do grid em Modo retrato
    private static final int PORTRAIT_SPAM_TABLET = 2;

    List<RecipeData> mRecipesList;

    RecyclerView mRecyclerView;

    private String mRecipesUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_recipes_list);
        mRecyclerView = findViewById(R.id.rv_recipes_list);
        SpeedDialView speedDialView = findViewById(R.id.speed_dial);
        createSpeedDialMenu(speedDialView);

        observeRecipesViewModel();

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<RecipeData> recipesList) {
        // Recebimento da orientação e definição do gridSpam para a correta exibição da View
        int gridSpam = PORTRAIT_SPAM_PHONE;
        int orientation = getResources().getConfiguration().orientation;
        boolean tablet = getResources().getBoolean(R.bool.tablet);
        if (tablet) {
            gridSpam = PORTRAIT_SPAM_TABLET;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridSpam = LANDSCAPE_SPAM_TABLET;
            }
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridSpam = LANDSCAPE_SPAM_PHONE;
        }


        // definição e inicialização do GridLayoutManager com o MovieGridAdapter no mRecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(this, gridSpam);
        recyclerView.setLayoutManager(layoutManager);

        RecipesRecyclerAdapter mRecipesAdapter = new RecipesRecyclerAdapter(MainActivity.this);
        mRecipesAdapter.setRecipeData(recipesList);
        recyclerView.setAdapter(mRecipesAdapter);
    }

    @Override
    public void onClick(int position) {
        Context context = this;
        int thisRecipeId = mRecipesList.get(position).getRecipeId();
        Class destinationClass = RecipeDetailActivity.class;
        RecipeAppWidget.updateWidget(context, thisRecipeId);
        Intent detailsIntent = new Intent(context, destinationClass);
        detailsIntent.putExtra(DataUtilities.ID_INTENT_EXTRA, thisRecipeId);
        startActivity(detailsIntent);
    }

    private void observeRecipesViewModel() {
        RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<RecipeData>>() {
            @Override
            public void onChanged(@Nullable List<RecipeData> recipes) {
                Timber.d(recipes.toString());
                mRecipesList = recipes;
                setupRecyclerView(mRecyclerView, recipes);
            }
        });
    }

    private void createSpeedDialMenu(SpeedDialView speedDialView) {
        speedDialView.inflate(R.menu.fab_itens);
        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.action_add_sample:
                        createSampleRecipes();
                        return false;
                    case R.id.action_add_recipe:
                        Toast.makeText(getApplicationContext(), "Adding New Recipe", Toast.LENGTH_LONG).show();
                        return false;
                    case R.id.action_erase_all:
                        DataUtilities.eraseAllData(getApplicationContext());
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    private void createSampleRecipes() {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                String recipesJson = "";
                try {
                    recipesJson = NetworkUtilities.getResponseFromHttpUrl(mRecipesUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JsonUtilities.createDataBaseSample(recipesJson, getApplicationContext());
            }
        });
    }

}
