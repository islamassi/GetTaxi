package com.assi.islam.mytaxi.flow;

import android.Manifest;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.manager.IdlingResourceManager;
import com.assi.islam.mytaxi.robot.VehicleListFragmentRobot;
import com.assi.islam.mytaxi.ui.activity.MainActivity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.assi.islam.mytaxi.manager.IdlingResourceManager.SERVICES_TIMED_OUT;

/**
 *
 * Flow for testing Vehicle list
 *
 * NOTE: This flow is incomplete is not complete and some areas are not implemented in the best way due to time limitations
 */
@RunWith(AndroidJUnit4.class)
public class VehiclesListFlow {

    private CountingIdlingResource servicesIdlingResources;

    @Rule
    public GrantPermissionRule fineLocationPermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void testSetup(){

        // counting idling resource for api services
        servicesIdlingResources = IdlingResourceManager.getInstance().getServicesIdlingResource();

        IdlingPolicies.setMasterPolicyTimeout(SERVICES_TIMED_OUT , TimeUnit.SECONDS);

        IdlingPolicies.setIdlingResourceTimeout(SERVICES_TIMED_OUT , TimeUnit.SECONDS);

        IdlingRegistry.getInstance().register(servicesIdlingResources);
    }

    /**
     * This method tests sort toggles.
     *
     * NOTE: this method implementation is not complete and it is not implemented in the best way due to time limitations
     */
    @Test
    public void testSortToggles() {

        VehicleListFragmentRobot vehicleListFragmentRobot =
                new VehicleListFragmentRobot()
                .checkDistanceFilterEnabled(false)
                .checkDurationFilterEnabled(false)
                .selectDurationSort()
                .checkDurationFilterEnabled(true)
                .checkDistanceFilterEnabled(false)
                .selectDistanceSort()
                .checkDistanceFilterEnabled(true)
                .checkDurationFilterEnabled(false);

        int listSize = ((RecyclerView)mActivityTestRule.getActivity().findViewById(R.id.vehiclesRecyclerView)).getAdapter().getItemCount();

        vehicleListFragmentRobot
                .checkListSortedByDistance(listSize);
    }
}
