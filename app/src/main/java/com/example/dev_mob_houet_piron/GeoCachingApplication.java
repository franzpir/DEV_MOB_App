package com.example.dev_mob_houet_piron;

import android.app.Application;

import com.example.dev_mob_houet_piron.data.room.GeoCachingDatabase;

public class GeoCachingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GeoCachingDatabase.initDatabase(getBaseContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        GeoCachingDatabase.disconnectDatabase();
    }

}
