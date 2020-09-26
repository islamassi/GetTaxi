package com.assi.islam.mytaxi.ui.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.databinding.RideOptionCardLayoutBinding;
import com.assi.islam.mytaxi.manager.LocationUpdateManager;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.viewModel.RideOptionCardViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import static com.assi.islam.mytaxi.utility.ResourceUtil.dpToPx;

/**
 * Created by islam assi
 *
 * ride option card
 */
public class RideOptionCardView extends FrameLayout {

    private Context mContext;
    private RideOptionCardLayoutBinding mBinding;
    private RideOptionCardViewModel mViewModel;
    private AnimatorSet set = new AnimatorSet();
    private int animDuration = 500;

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
        mBinding.setViewModel(mViewModel);
        mBinding.headingImage.setRotation(0);
        mBinding.headingImage.animate().rotation(((float) mViewModel.getRideOption().getVehicle().getHeading() *-1)).start();
        if (mViewModel.getRideOption().getDirections() != null && !mViewModel.getRideOption().getDirections().isEmpty())
            animateCard();
        else
            animateCarX();
    }

    public void animateCard() {
        set.end();
        float itemX = dpToPx(mContext, 20);
        float cardX = dpToPx(mContext, 70);

        ObjectAnimator cardAnim = ObjectAnimator.ofFloat(mBinding.getRoot(), "translationX", -cardX, 0f)
                .setDuration(animDuration);
        ObjectAnimator timeAnim = ObjectAnimator.ofFloat(mBinding.durationTextView, "translationX", itemX, 0f)
                .setDuration(animDuration);
        ObjectAnimator distanceAnim = ObjectAnimator.ofFloat(mBinding.distanceTextView, "translationX", itemX, 0f)
                .setDuration(animDuration);
        ObjectAnimator headingAnim = ObjectAnimator.ofFloat(mBinding.headingTextView, "translationX", itemX, 0f)
                .setDuration(animDuration);
        ObjectAnimator carX2Anim = ObjectAnimator.ofFloat(mBinding.coverImage, "translationX",  cardX,0f)
                .setDuration(animDuration);

        mBinding.coverImage.setAlpha(0.1f);
        mBinding.coverImage.animate().alpha(0.75f).setDuration(animDuration*4).start();

        set = new AnimatorSet();
        set.play(cardAnim).with(timeAnim).with(distanceAnim).with(headingAnim).with(carX2Anim)
                .before(getCarAnimation(itemX, itemX));
        set.start();
    }

    public void animateCarX(){
        float itemXAnim = dpToPx(mContext, 20);
        mBinding.coverImage.setTranslationY(dpToPx(mContext, 5));
        ObjectAnimator
                .ofFloat(mBinding.coverImage, "translationX",  itemXAnim)
                .setDuration(animDuration*2).start();
    }
    private AnimatorSet getCarAnimation(float x, float y){
        ObjectAnimator carXAnim = ObjectAnimator.ofFloat(mBinding.coverImage, "translationX",  x)
                .setDuration(animDuration*2);
        ObjectAnimator carYAnim = ObjectAnimator.ofFloat(mBinding.coverImage, "translationY",  y)
                .setDuration(animDuration*2);
        carYAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        carXAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet carAnim = new AnimatorSet();
        carAnim.playTogether(carXAnim,carYAnim);
        return carAnim;
    }
}
