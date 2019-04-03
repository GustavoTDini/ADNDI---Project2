package com.example.adndi___project2.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.adndi___project2.R;
import com.example.adndi___project2.recipe_utilities.DataUtilities;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {

    static int mRecipeId;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int recipeId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_widget);
        Intent intent = new Intent(context, RecipeAppWidget.class);
        intent.putExtra(DataUtilities.ID_INTENT_EXTRA, recipeId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.lv_widget_list, intent);
        views.setEmptyView(R.id.lv_widget_list, android.R.id.empty);
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_widget_list);
    }

    public static void updateWidget(Context context, int recipeId) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, RecipeAppWidget.class));
        mRecipeId = recipeId;
        Timber.d("Recipe on Widget: %s", recipeId);
        context.sendBroadcast(intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, mRecipeId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action != null && action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, RecipeAppWidget.class);
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(cn), R.id.lv_widget_list);
        }
        super.onReceive(context, intent);
    }
}

