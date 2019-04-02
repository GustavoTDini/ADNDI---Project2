package com.example.adndi___project2.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeAppWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetViewsFactory(this.getApplicationContext());
    }
}
