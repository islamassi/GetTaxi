package com.assi.islam.mytaxi;

import android.app.Application;
import android.content.Context;

/**
 * Created by islam assi
 */
public class MyTaxiApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }
}
