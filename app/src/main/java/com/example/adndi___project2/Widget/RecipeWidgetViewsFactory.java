package com.example.adndi___project2.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.adndi___project2.R;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeIngredients;
import com.example.adndi___project2.recipe_utilities.AppExecutors;
import com.example.adndi___project2.recipe_utilities.DataUtilities;

import java.util.List;

import timber.log.Timber;

public class RecipeWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context mContext;
    private int mRecipeId;
    private List<RecipeIngredients> mIngredients;


    public RecipeWidgetViewsFactory(Context context, Intent intent) {
        mContext = context;
        mRecipeId = intent.getIntExtra(DataUtilities.ID_INTENT_EXTRA, -1);
        Timber.d("Recipe on Widget Create ViewFactory: %s", intent);
    }


    @Override
    public void onCreate() {

        Timber.d("Recipe on Widget Create ViewFactory: %s", mRecipeId);
    }

    @Override
    public void onDataSetChanged() {
        getIngredients(mRecipeId);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (mIngredients != null) {
            return mIngredients.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients != null) {
            RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_list_content);
            remoteView.setTextViewText(R.id.tv_ingredient_name, mIngredients.get(position).getIngredientName());
            remoteView.setTextViewText(R.id.tv_ingredient_measure, mIngredients.get(position).getIngredientMeasure());
            remoteView.setTextViewText(R.id.tv_ingredient_quantity, String.valueOf(mIngredients.get(position).getIngredientQuantity()));
            return remoteView;
        }
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void getIngredients(final int recipeId) {
        if (mRecipeId != -1) {
            final RecipeDatabase mDb = RecipeDatabase.getInstance(mContext);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mIngredients = mDb.IngredientDao().loadWidgetIngredients(recipeId);
                }
            });
        }

    }

    public void getRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }
}
