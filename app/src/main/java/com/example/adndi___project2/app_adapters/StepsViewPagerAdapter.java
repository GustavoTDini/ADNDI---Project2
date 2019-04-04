package com.example.adndi___project2.app_adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.adndi___project2.fragments.FirstPageViewPagerFragment;
import com.example.adndi___project2.fragments.StepViewPagerFragment;
import com.example.adndi___project2.recipe_utilities.DataUtilities;

import timber.log.Timber;

/**
 * Classe de ViewHolder que irá definir as view da viewPager do fragmento que irá mostrar os detahes
 * e videos dos passos da receita
 */
public class StepsViewPagerAdapter extends FragmentStatePagerAdapter{

    private int mSteps = 0;

    private int mRecipeId;

    public StepsViewPagerAdapter(FragmentManager fm) {
        super( fm );
    }

    /**
     * getItem irá criar as varias view, a primeira sempre mostrará o nome da receita e a foto,
     * as demais mostraram os passos
     */
    @Override
    public Fragment getItem(int i) {
        Bundle arguments = new Bundle();
        arguments.putInt(DataUtilities.ID_INTENT_EXTRA, mRecipeId);
        arguments.putInt(DataUtilities.PAGER_ORDER_EXTRA, i - 1);
        if (i == 0) {
            FirstPageViewPagerFragment firstPageViewPagerFragment = new FirstPageViewPagerFragment();
            firstPageViewPagerFragment.setArguments(arguments);
            return firstPageViewPagerFragment;
        } else {
            StepViewPagerFragment stepsViewPagerFragment = new StepViewPagerFragment();
            stepsViewPagerFragment.setArguments( arguments );
            return stepsViewPagerFragment;
        }
    }

    @Override
    public int getCount() {
        Timber.d("viewPager: %s", mSteps);
        return mSteps;
    }

    /**
     * Método que pega o numero de passos desta receita e o define o numero de views a ser populado
     *
     * @param stepsNumber numero de passos da receita
     */
    public void setSteps(int stepsNumber){
        mSteps = stepsNumber;
        notifyDataSetChanged();
        Timber.d("viewPager: %s", mSteps);
    }

    /**
     * Método que recebe o Id da receita
     *
     * @param recipeId Id da receita
     */
    public void getRecipeId(int recipeId) {
        mRecipeId = recipeId;
        notifyDataSetChanged();
        Timber.d("viewPager: %s", mRecipeId);
    }
}
