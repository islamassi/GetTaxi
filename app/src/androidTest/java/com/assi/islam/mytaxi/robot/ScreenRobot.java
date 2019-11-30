package com.assi.islam.mytaxi.robot;

import android.util.Log;

import androidx.annotation.IdRes;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class ScreenRobot<T extends ScreenRobot> {


    public T clickOnView(@IdRes int resourceId){

        onView(withId(resourceId)).perform(click());

        return (T)this;
    }

    public T checkDisplayed(@IdRes int resourceId){

        onView(withId(resourceId)).check(matches(isDisplayed()));

        return (T)this;
    }

    public T checkDisplayed(String text){

        onView(allOf(withText(text), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(isDisplayed()));

        return (T)this;
    }

    public  T sleep(int milli) {

        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (T) this;
    }

    public T pressBack(){

        Espresso.pressBack();

        return (T)this;

    }


}
