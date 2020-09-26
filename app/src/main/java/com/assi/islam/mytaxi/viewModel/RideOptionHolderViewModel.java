package com.assi.islam.mytaxi.viewModel;

import com.assi.islam.mytaxi.model.Coordinate;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.model.Vehicle;
import com.assi.islam.mytaxi.repository.VehiclesRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by islam assi
 *
 * view model for {@link com.assi.islam.mytaxi.ui.viewHolder.RideOptionCardViewHolder}
 */
public class RideOptionHolderViewModel {

    private RideOptionCardViewModel cardViewModel;

    public RideOptionHolderViewModel(RideOptionCardViewModel cardViewModel) {
        this.cardViewModel = cardViewModel;
    }

    public RideOptionCardViewModel getCardViewModel() {
        return cardViewModel;
    }

    public void setCardViewModel(RideOptionCardViewModel rideOptionCardViewModel) {
        this.cardViewModel = rideOptionCardViewModel;
    }
}
