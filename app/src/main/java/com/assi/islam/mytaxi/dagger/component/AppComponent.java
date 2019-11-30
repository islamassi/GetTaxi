package com.assi.islam.mytaxi.dagger.component;

import com.assi.islam.mytaxi.ui.activity.MainActivity;
import com.assi.islam.mytaxi.dagger.module.GeoApiModule;
import com.assi.islam.mytaxi.dagger.module.NetModule;
import com.assi.islam.mytaxi.dagger.module.ViewModelModule;
import com.assi.islam.mytaxi.ui.fragment.VehicleListFragment;
import com.assi.islam.mytaxi.ui.fragment.VehiclesMapFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by islam assi
 */
@Singleton
@Component(modules = {NetModule.class, ViewModelModule.class, GeoApiModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(VehiclesMapFragment vehiclesMapFragment);

    void inject(VehicleListFragment vehicleListFragment);

}
