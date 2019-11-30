package com.assi.islam.mytaxi.dagger.module;

import com.assi.islam.mytaxi.viewModel.MainActivityViewModel;
import com.assi.islam.mytaxi.viewModel.RideOptionHolderViewModel;
import com.assi.islam.mytaxi.viewModel.VehicleListViewModel;
import com.assi.islam.mytaxi.viewModel.VehiclesMapViewModel;
import com.assi.islam.mytaxi.viewModel.VehiclesSharedViewModel;
import com.assi.islam.mytaxi.viewModel.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by islam assi
 *
 * injects view model classes
 */
@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(VehiclesMapViewModel.class)
    abstract ViewModel vehiclesMapViewModel(VehiclesMapViewModel vehiclesMapViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VehicleListViewModel.class)
    abstract ViewModel vehiclesListViewModel(VehicleListViewModel vehicleListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RideOptionHolderViewModel.class)
    abstract ViewModel rideOptionCardViewModel(RideOptionHolderViewModel rideOptionHolderViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel mainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VehiclesSharedViewModel.class)
    abstract ViewModel vehiclesSHaredViewModel(VehiclesSharedViewModel sharedViewModel);

}