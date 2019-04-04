package com.example.adndi___project2;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

/**
 * Classe da main activity, implementa a interface RecyclerAdapterOnClickHandler
 */
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

    // URL aonde está o JSon
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

    /**
     * setupRecyclerView, define as configurações e povoa o RecyclerView com as receitas
     */
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
        // Cria o shared preferences e atualiza o widget baseas no RecipeId atual
        createSharedPreferenceId(thisRecipeId);
        updateWidget();
        Class destinationClass = RecipeDetailActivity.class;
        Intent detailsIntent = new Intent(context, destinationClass);
        detailsIntent.putExtra(DataUtilities.ID_INTENT_EXTRA, thisRecipeId);
        startActivity(detailsIntent);
    }

    /**
     * ViemModel para receber os dados da Receita
     */
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

    /**
     * metodo para configurar o SpeedDial
     */
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
                        Toast.makeText(getApplicationContext(), "Add new recipe to be added in next iteration!", Toast.LENGTH_LONG).show();
                        return false;
                    case R.id.action_erase_all:
                        deleteAllCache();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    /**
     * metodo para criar o Sample Recipes, faz um teste de conexão antes
     */
    private void createSampleRecipes() {
        if(NetworkUtilities.testConnection( getApplicationContext() )){
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
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Método para criar a sharedpreferences e coloca o RecipeId
     */
    private void createSharedPreferenceId(int recipeId) {
        SharedPreferences.Editor editor = getSharedPreferences(DataUtilities.ID_SHARED_PREFERENCE, 0).edit();
        editor.putInt(DataUtilities.ID_SHARED_EXTRA, recipeId);
        editor.apply();
    }

    /**
     * Método para criar atualizar o Widget - Cria um intent com a ação ACTION_APPWIDGET_UPDATE
     */
    public void updateWidget() {
        Intent widgetIntent = new Intent(this, RecipeAppWidget.class);
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(widgetIntent);
    }

    /**
     * Método para apagar o banco de dados, primeiro pergunta ao usuario se ele deseja realmente apagar
     */
    private void deleteAllCache() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Data Exclusion")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DataUtilities.eraseAllData(getApplicationContext());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }


}
