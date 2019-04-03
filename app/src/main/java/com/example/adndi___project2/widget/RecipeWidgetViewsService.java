package com.example.adndi___project2.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeWidgetViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetViewsFactory(this.getApplicationContext(), intent);
    }
}