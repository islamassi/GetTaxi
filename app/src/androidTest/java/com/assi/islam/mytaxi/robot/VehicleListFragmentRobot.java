package com.assi.islam.mytaxi.robot;

import android.view.View;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.matcher.RideOptionDistanceSortMatcher;
import com.assi.islam.mytaxi.util.TestUtility;

import org.hamcrest.Matcher;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.assi.islam.mytaxi.util.TestUtility.switchIconViewEnabled;

/**
 * Created by islam on 06,April,2019
 */
public class VehicleListFragmentRobot extends ScreenRobot<VehicleListFragmentRobot> {


    public VehicleListFragmentRobot selectDistanceSort(){

        onView(withId(R.id.distanceSortIcon))
                .perform(click());

        sleep(500);

        return this;
    }

    public VehicleListFragmentRobot checkDistanceFilterEnabled(boolean enabled){

        onView(withId(R.id.distanceSortIcon))
                .check(matches(switchIconViewEnabled(enabled)));

        return this;
    }

    public VehicleListFragmentRobot selectDurationSort(){

        onView(withId(R.id.durationSortIcon))
                .perform(click());

        sleep(500);

        return this;
    }

    public VehicleListFragmentRobot checkDurationFilterEnabled(boolean enabled){

        onView(withId(R.id.durationSortIcon))
                .check(matches(switchIconViewEnabled(enabled)));

        return this;
    }

    public VehicleListFragmentRobot checkListSortedByDistance(int listSize){

        ViewInteraction recyclerViewInteraction = onView(withId(R.id.vehiclesRecyclerView));

        Matcher<View> matcher = new RideOptionDistanceSortMatcher();

        for (int i = 0; i < listSize; i++) {

            ViewInteraction viewInteraction = recyclerViewInteraction
                    .perform(RecyclerViewActions.scrollToPosition(i));
            //too fast scrolling
            sleep(100);
            viewInteraction.check(matches(TestUtility.atPosition(i, matcher)));

        }

        return this;
    }
}
