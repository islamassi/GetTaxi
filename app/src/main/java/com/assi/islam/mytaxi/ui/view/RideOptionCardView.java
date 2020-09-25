package com.assi.islam.mytaxi.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.databinding.RideOptionCardLayoutBinding;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.viewModel.RideOptionCardViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

/**
 * Created by islam assi
 *
 * ride option card
 */
public class RideOptionCardView extends FrameLayout {

    private Context mContext;
    private RideOptionCardLayoutBinding mBinding;
    private RideOptionCardViewModel mViewModel;

    public RideOptionCardView(Context context) {
        super(context);
        init(context,null, 0);
    }

    public RideOptionCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RideOptionCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public RideOptionCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext =context;
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = RideOptionCardLayoutBinding.inflate(inflater, this, true);
    }

    public RideOptionCardLayoutBinding getBinding() {
        return mBinding;
    }

    public RideOptionCardViewModel getViewModel() {
        return mViewModel;
    }

    public void setViewModel(RideOptionCardViewModel mViewModel) {
        this.mViewModel = mViewModel;
        mBinding.headingImage.setRotation((float) mViewModel.getRideOption().getVehicle().getHeading() *-1);
        mBinding.setViewModel(mViewModel);
    }
}
