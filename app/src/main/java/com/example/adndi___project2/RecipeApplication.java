package com.example.adndi___project2;

import android.app.Application;

import timber.log.Timber;

public class RecipeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
