package com.assi.islam.mytaxi.dagger.module;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.constants.Constants;
import com.assi.islam.mytaxi.utility.ResourceUtil;
import com.google.maps.GeoApiContext;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by islam assi
 *
 * provides {@link GeoApiContext}
 */

@Module
public class GeoApiModule {

    @Provides
    @Singleton
    GeoApiContext getGeoApiContext(){

        return new GeoApiContext.Builder()
                .queryRateLimit(3)
                .apiKey(ResourceUtil.bindString(R.string.google_maps_key))
                .connectTimeout(Constants.GEO_API_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.GEO_API_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.GEO_API_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
}
