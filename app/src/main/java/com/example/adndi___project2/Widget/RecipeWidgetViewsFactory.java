package com.example.adndi___project2.Widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeSteps;
import com.example.adndi___project2.R;
import com.example.adndi___project2.RecipeUtilities.AppExecutors;

import java.util.List;

public class RecipeWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mRecipeId;
    private List<RecipeSteps> mSteps;

    public RecipeWidgetViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        getSteps();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mSteps != null) {
            return mSteps.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mSteps != null) {
            RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.widget_content);
            remoteView.setTextViewText(R.id.tv_widget_content, mSteps.get(position).getStepDescription());
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
        if (mSteps != null) {
            return mSteps.get(position).getRecipeId();
        }
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void getSteps() {
        final RecipeDatabase mDb = RecipeDatabase.getInstance(mContext);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mSteps = mDb.StepsDao().loadWidgetSteps(mRecipeId);
            }
        });
    }

    public void getRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

}
