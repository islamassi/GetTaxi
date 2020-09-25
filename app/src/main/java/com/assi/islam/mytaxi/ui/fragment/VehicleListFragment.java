package com.assi.islam.mytaxi.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.model.DiffResultModel;
import com.assi.islam.mytaxi.model.SortModel;
import com.assi.islam.mytaxi.ui.activity.MainActivity;
import com.assi.islam.mytaxi.ui.adapter.VehicleListAdapter;
import com.assi.islam.mytaxi.dagger.component.DaggerAppComponent;
import com.assi.islam.mytaxi.databinding.VehicleListFragmentBinding;
import com.assi.islam.mytaxi.interfaces.OnRecyclerItemClickListener;
import com.assi.islam.mytaxi.model.ApiError;
import com.assi.islam.mytaxi.model.Resource;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.viewModel.VehicleListViewModel;
import com.assi.islam.mytaxi.viewModel.VehiclesSharedViewModel;
import com.assi.islam.mytaxi.viewModel.ViewModelFactory;
import com.github.zagum.switchicon.SwitchIconView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by islam assi
 *
 * Fragment with ride options available to the user
 */
public class VehicleListFragment extends Fragment implements
        OnRecyclerItemClickListener,
        Observer<Resource<List<RideOption>, ApiError>> {

    private VehicleListViewModel mViewModel;
    private VehiclesSharedViewModel mSharedViewModel;
    private VehicleListFragmentBinding mBinding;
    @Inject
    VehicleListAdapter mAdapter;
    @Inject
    ViewModelFactory mViewModelFactory;
    private Toast errorToast;

    public static VehicleListFragment newInstance() {
        return new VehicleListFragment();
    }

    /**
     * notified when diffResults for a new list is calculated and sorted (if sorting enabled).
     */
    private  Observer<DiffResultModel<RideOption>> processedDiffResultObserver = diffResultModel -> {
        mAdapter.notifyWithDiff(diffResultModel);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.vehicle_list_fragment, container, false);
        DaggerAppComponent.create().inject(this);
        errorToast = Toast.makeText(getActivity(), R.string.server_error , Toast.LENGTH_LONG);
        mBinding.setLifecycleOwner(this);
        mAdapter.setClickListener(this);
        mBinding.vehiclesRecyclerView.setAdapter(mAdapter);
        initSortLayout();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // fragment scope
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(VehicleListViewModel.class);

        // activity scope
        mSharedViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(VehiclesSharedViewModel.class);

        if (mViewModel.getProcessedDiffResultLiveData().getValue() == null)
            mSharedViewModel.requestHamburgRideOptions();

        mBinding.setViewModel(mViewModel);
        mSharedViewModel.getRideOptionListResourceLiveData().observe(getViewLifecycleOwner(), this);
        mViewModel.getProcessedDiffResultLiveData().observe(getViewLifecycleOwner(), processedDiffResultObserver);
        mBinding.swipeToRefresh.setOnRefreshListener(mSharedViewModel::requestHamburgRideOptions);
        mBinding.swipeToRefresh.setRefreshing(true);
    }

    private void initSortLayout() {
        mBinding.durationSortLayout.setOnClickListener(v -> {
            switchToggles(mBinding.durationSortIcon, mBinding.distanceSortIcon);
            if (mBinding.durationSortIcon.isIconEnabled())
                mViewModel.sort( SortModel.SortType.DURATION ,mAdapter.getRideOptionList());
            else
                mViewModel.getSortModel().setSortType(SortModel.SortType.NO_SORT);
        });
        mBinding.distanceSortLayout.setOnClickListener(v -> {
            switchToggles(mBinding.distanceSortIcon, mBinding.durationSortIcon);
            if (mBinding.distanceSortIcon.isIconEnabled())
                mViewModel.sort( SortModel.SortType.DISTANCE ,mAdapter.getRideOptionList(), mAdapter.getRideOptionList());
            else
                mViewModel.getSortModel().setSortType(SortModel.SortType.NO_SORT);
        });
    }

    private void switchToggles(SwitchIconView selectedToggle, SwitchIconView otherToggle){
        selectedToggle.switchState(true);
        otherToggle.setIconEnabled(false, true);
    }

    @Override
    public void recyclerItemClicked(View view, int position) {
        mSharedViewModel.getSelectedRideOptionLiveData().setValue(mAdapter.getRideOptionList().get(position));
        ((MainActivity)getActivity()).showVehiclesMapFragment();
    }

    /**
     * listen to the {@link RideOption} list resource that is being requested
     *
     * @param resource
     */
    @Override
    public void onChanged(Resource<List<RideOption>, ApiError> resource) {
        // resource finished loading successfully or still loading
        if (resource.status == Resource.Status.SUCCESS || resource.status == Resource.Status.LOADING){
            mViewModel.sort( resource.data, mAdapter.getRideOptionList());
            if (resource.status == Resource.Status.SUCCESS) {
                mBinding.swipeToRefresh.setRefreshing(false);
            }else{
                mBinding.swipeToRefresh.setRefreshing(true);
            }
        }else if(resource.status == Resource.Status.ERROR){
            mBinding.swipeToRefresh.setRefreshing(false);
            errorToast.show();
        }
    }
}
