package com.example.adndi___project2.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;
/**
 * Classe de RemoteViewsService do Widget
 */
public class RecipeWidgetViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new com.example.adndi___project2.widget.RecipeWidgetViewsFactory(this.getApplicationContext(), intent);
    }
}