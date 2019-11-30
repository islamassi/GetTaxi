package com.assi.islam.mytaxi.manager;


import androidx.test.espresso.idling.CountingIdlingResource;

/**
 * Created by islam
 *
 * Manages IdlingResources
 */
public class IdlingResourceManager {

    public static final int SERVICES_TIMED_OUT = 155;

    private static final IdlingResourceManager ourInstance = new IdlingResourceManager();

    private CountingIdlingResource servicesIdlingResource = new CountingIdlingResource("Background_Service_Call");

    public static IdlingResourceManager getInstance() {
        return ourInstance;
    }

    private IdlingResourceManager() {
    }

    public CountingIdlingResource getServicesIdlingResource() {
        return servicesIdlingResource;
    }
}
