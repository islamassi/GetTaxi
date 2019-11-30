package com.assi.islam.mytaxi.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.github.zagum.switchicon.SwitchIconView;
import com.google.android.material.appbar.AppBarLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.BoundedMatcher;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static com.google.android.gms.common.internal.Asserts.checkNotNull;


/**
 * Created by Islam Assi
 *
 * helper methods token mainly from : https://medium.com/mindorks/some-useful-custom-espresso-matchers-in-android-33f6b9ca2240
 */
public class TestUtility {

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    public static ViewAction collapseAppBarLayout() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(AppBarLayout.class);
            }

            @Override
            public String getDescription() {
                return "Collapse App Bar Layout";
            }

            @Override
            public void perform(UiController uiController, View view) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                appBarLayout.setExpanded(false, true);
                uiController.loopMainThreadUntilIdle();
            }
        };
    }

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    /**
     *
     * @param position position of the target viewHolder
     * @param itemMatcher a matcher to be applied to the selected view (targetViewId) in the ViewHolder
     * @param targetViewId a specific view in the target viewHolder
     * @return
     */
    public static Matcher<View> recyclerViewAtPositionOnView(final int position, final Matcher<View> itemMatcher, @NonNull final int targetViewId) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has view id " + itemMatcher + " at position " + position);
            }

            @Override
            public boolean matchesSafely(final RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                View targetView = viewHolder.itemView.findViewById(targetViewId);
                return itemMatcher.matches(targetView);
            }
        };
    }


    public static Matcher<View> switchIconViewEnabled(boolean enabled){

        return new BoundedMatcher<View, SwitchIconView>(SwitchIconView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("SwitchIconView enabled:" + enabled);

            }

            @Override
            protected boolean matchesSafely(SwitchIconView item) {
                return item.isIconEnabled() == enabled;
            }
        };
    }
}
