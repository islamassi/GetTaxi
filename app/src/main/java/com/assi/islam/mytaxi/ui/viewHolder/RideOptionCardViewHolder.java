package com.assi.islam.mytaxi.ui.viewHolder;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.assi.islam.mytaxi.databinding.RideOptionViewHolderBinding;
import com.assi.islam.mytaxi.interfaces.OnRecyclerItemClickListener;
import com.assi.islam.mytaxi.manager.LocationUpdateManager;
import com.assi.islam.mytaxi.utility.ResourceUtil;
import com.assi.islam.mytaxi.viewModel.RideOptionHolderViewModel;
import com.google.android.material.internal.ViewUtils;

import androidx.recyclerview.widget.RecyclerView;

import static com.assi.islam.mytaxi.utility.ResourceUtil.dpToPx;

/**
 * Created by islam assi
 */
public class RideOptionCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RideOptionViewHolderBinding mBinding;
    private Context mContext;
    private RideOptionHolderViewModel mViewModel;
    private OnRecyclerItemClickListener clickListener;

    public RideOptionCardViewHolder(RideOptionViewHolderBinding mBinding, Context mContext, OnRecyclerItemClickListener clickListener) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
        this.mContext = mContext;
        this.clickListener = clickListener;
        mBinding.getRoot().setOnClickListener(this);
    }

    public void bind(RideOptionHolderViewModel viewModel){
        this.mViewModel = viewModel;
        mBinding.unbind();
        mBinding.setViewModel(mViewModel);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onClick(View v) {
        clickListener.recyclerItemClicked(v, this.getLayoutPosition());
    }
}
