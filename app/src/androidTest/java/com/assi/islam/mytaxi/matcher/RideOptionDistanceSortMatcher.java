package com.assi.islam.mytaxi.matcher;

import android.view.View;
import android.widget.TextView;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.ui.view.RideOptionCardView;
import com.assi.islam.mytaxi.ui.viewHolder.RideOptionCardViewHolder;

import org.hamcrest.Description;

import androidx.test.espresso.matcher.BoundedMatcher;

/**
 * Created by islam on 06,April,2019
 */
public class RideOptionDistanceSortMatcher extends BoundedMatcher<View, RideOptionCardView> {

    double previousValue = -1;

    public RideOptionDistanceSortMatcher() {
        super(RideOptionCardView.class);
    }

    @Override
    protected boolean matchesSafely(RideOptionCardView rideOptionCardView) {

        TextView distanceText = rideOptionCardView.findViewById(R.id.distanceTextView);

        double cardDistance = Double.parseDouble(distanceText.getText().toString().replaceAll("[^0-9]", ""));

        boolean isMatch =  cardDistance >= previousValue;

        previousValue = cardDistance;

        return isMatch;
    }

    @Override
    public void describeTo(Description description) {

        description.appendText("check RideOption sorted by distance with previous distance:"+ previousValue);
    }
}
