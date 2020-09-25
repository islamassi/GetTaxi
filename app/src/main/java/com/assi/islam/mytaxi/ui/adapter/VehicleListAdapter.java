package com.assi.islam.mytaxi.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.databinding.RideOptionViewHolderBinding;
import com.assi.islam.mytaxi.interfaces.OnRecyclerItemClickListener;
import com.assi.islam.mytaxi.model.DiffResultModel;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.ui.viewHolder.RideOptionCardViewHolder;
import com.assi.islam.mytaxi.viewModel.RideOptionCardViewModel;
import com.assi.islam.mytaxi.viewModel.RideOptionHolderViewModel;
import com.assi.islam.mytaxi.viewModel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by islam assi
 *
 * The adapter responsible for showing ride options list
 */
public class VehicleListAdapter extends RecyclerView.Adapter<RideOptionCardViewHolder> {

    private List<RideOption> rideOptionList = new ArrayList<>();

    // used to create view models for view holders
    private ViewModelFactory mViewModelFactory;

    // listener callback
    private OnRecyclerItemClickListener clickListener;

    @Inject
    public VehicleListAdapter(ViewModelFactory mViewModelFactory) {
        this.mViewModelFactory = mViewModelFactory;
    }

    @NonNull
    @Override
    public RideOptionCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RideOptionViewHolderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ride_option_view_holder,
                parent,
                false);
        return new RideOptionCardViewHolder(binding, parent.getContext(), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RideOptionCardViewHolder holder, int position) {
        RideOptionHolderViewModel viewModel = ViewModelProviders.of((FragmentActivity) holder.getContext(), mViewModelFactory).get(RideOptionHolderViewModel.class);
        viewModel.setCardViewModel(new RideOptionCardViewModel(rideOptionList.get(position)));
        holder.bind(viewModel);
    }

    @Override
    public int getItemCount() {
        return rideOptionList.size();
    }

    public List<RideOption> getRideOptionList() {
        return rideOptionList;
    }

    public void setClickListener(OnRecyclerItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * apply list changes using {@link DiffUtil}
     * @param diffResultModel Calculated diff results
     */
    public void notifyWithDiff(DiffResultModel<RideOption> diffResultModel){
        this.rideOptionList.clear();
        this.rideOptionList.addAll(diffResultModel.getNewList());
        diffResultModel.getDiffResult().dispatchUpdatesTo(VehicleListAdapter.this);
    }
}
