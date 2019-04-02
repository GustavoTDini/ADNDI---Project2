package com.example.adndi___project2.Widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeIngredients;
import com.example.adndi___project2.R;
import com.example.adndi___project2.RecipeUtilities.AppExecutors;

import java.util.List;

public class RecipeWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mRecipeId;
    private List<RecipeIngredients> mIngredients;

    public RecipeWidgetViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
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
        if (mIngredients != null) {
            return mIngredients.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients != null) {
            RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.widget_content);
            remoteView.setTextViewText(R.id.tv_widget_content, mIngredients.get(position).getIngredientName());
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
        if (mIngredients != null) {
            return mIngredients.get(position).getRecipeId();
        }
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void getIngredients() {
        final RecipeDatabase mDb = RecipeDatabase.getInstance(mContext);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mIngredients = mDb.IngredientDao().loadWidgetIngredients(mRecipeId);
            }
        });
    }

    public void getRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

}
