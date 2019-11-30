package com.assi.islam.mytaxi.viewModel;

import android.util.Log;

import com.assi.islam.mytaxi.utility.VehicleListDiffCallback;
import com.assi.islam.mytaxi.model.DiffResultModel;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.model.SortModel;
import com.assi.islam.mytaxi.repository.VehiclesRepository;
import com.assi.islam.mytaxi.utility.RideOptionComparator;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel for {@link com.assi.islam.mytaxi.ui.fragment.VehicleListFragment}
 *
 */

public class VehicleListViewModel extends ViewModel {

    private static final String TAG = VehicleListViewModel.class.getSimpleName();

    private Disposable lastListDisposal;

    // LiveData containing calculated diffResults and the new list after being calculated
    private MutableLiveData<DiffResultModel<RideOption>> processedDiffResultLiveData = new MutableLiveData<>();

    private SortModel sortModel = new SortModel(SortModel.SortType.NO_SORT);

    private VehiclesRepository vehiclesRepository;

    @Inject
    public VehicleListViewModel(VehiclesRepository vehiclesRepository) {

        this.vehiclesRepository = vehiclesRepository;
    }

    public void sort( List<RideOption> newList, List<RideOption> oldList){

        sort(sortModel.getSortType(), newList, oldList);
    }

    public void sort(SortModel.SortType sortType , List<RideOption> list){

        sort(sortType, list, list);
    }

    /**
     * sorting list and calculating {@link androidx.recyclerview.widget.DiffUtil.DiffResult}
     *
     * This is done in a computation thread
     */
    public void sort(SortModel.SortType sortType , List<RideOption> newList, List<RideOption> oldList){

        this.sortModel.setSortType(sortType);

        if (lastListDisposal != null)
            lastListDisposal.dispose();

        lastListDisposal = Observable.just(newList)
                .flatMap(Observable::fromIterable)
                .toSortedList(new RideOptionComparator(sortModel))
                .map(rideOptionList -> new DiffResultModel<>(
                        rideOptionList,
                        DiffUtil.calculateDiff(new VehicleListDiffCallback(rideOptionList, oldList)))
                ).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(diffResultModel -> {
                    if (diffResultModel!= null && diffResultModel.getNewList() != null){
                        processedDiffResultLiveData.setValue(diffResultModel);
                    }
                }, throwable -> {
                    Log.e(TAG, "Error sorting");
                });

    }

    public MutableLiveData<DiffResultModel<RideOption>> getProcessedDiffResultLiveData() {
        return processedDiffResultLiveData;
    }

    public SortModel getSortModel() {
        return sortModel;
    }

    public void setSortModel(SortModel sortModel) {
        this.sortModel = sortModel;
    }
}
