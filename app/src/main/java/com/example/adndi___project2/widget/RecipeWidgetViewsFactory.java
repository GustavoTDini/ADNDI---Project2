package com.example.adndi___project2.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.adndi___project2.R;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeIngredients;
import com.example.adndi___project2.recipe_utilities.AppExecutors;
import com.example.adndi___project2.recipe_utilities.DataUtilities;

import java.util.List;

import timber.log.Timber;

/**
 * Classe do viewFactory do Widget
 */
public class RecipeWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context mContext;
    private List<RecipeIngredients> mIngredients;


    public RecipeWidgetViewsFactory(Context context, Intent intent) {
        mContext = context;
        Timber.d("Recipe on Widget Create ViewFactory: %s", intent);
    }


    @Override
    public void onCreate() {
        Timber.d("onCreate %s", mIngredients);
        getIngredients();
    }

    @Override
    public void onDataSetChanged() {
        getIngredients();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        Timber.d("getCount");
        if (mIngredients != null) {
            Timber.d("ViewFactory getCount %s", mIngredients.size());
            return mIngredients.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Timber.d("getViewsAt %s", mIngredients);
        if (mIngredients != null) {
            RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_content);
            remoteView.setTextViewText(R.id.tv_widget_name, mIngredients.get(position).getIngredientName());
            remoteView.setTextViewText(R.id.tv_widget_measure, mIngredients.get(position).getIngredientMeasure());
            remoteView.setTextViewText(R.id.tv_widget_quantity, String.valueOf(mIngredients.get(position).getIngredientQuantity()));
            Timber.d("getViewsAt %s", remoteView);
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

    /**
     * recupera os Ingredientes de acordo com o Id que está no SharedPreferences
     */
    private void getIngredients() {
        SharedPreferences prefs = mContext.getSharedPreferences(DataUtilities.ID_SHARED_PREFERENCE, 0);
        final int recipeId = prefs.getInt(DataUtilities.ID_SHARED_EXTRA, -1);
        Timber.d("Factory recipeId %s", recipeId);
        if (recipeId != -1) {
            final RecipeDatabase mDb = RecipeDatabase.getInstance(mContext);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mIngredients = mDb.IngredientDao().loadWidgetIngredients(recipeId);
                    Timber.d("WidgetIngredients: %s", mIngredients);
                }
            });
        }
    }
}
