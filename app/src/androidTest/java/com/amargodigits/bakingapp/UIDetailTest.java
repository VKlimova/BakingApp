package com.amargodigits.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

/*
Detail activity test. Open MainActivity, click first item in the list of recipes,
 click the first item in the steps list,
 checks that the step_text_view has the text "Intro"
 */


@RunWith(AndroidJUnit4.class)
public class UIDetailTest {
@Rule public ActivityTestRule<MainActivity> mActivityTestRule
        = new ActivityTestRule<>(MainActivity.class);
@Test
public void clickSmgh_ChangeSmth(){
    onView(withId(R.id.baking_grid_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.step_grid_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withId(R.id.step_text_view)).check(matches(withText(containsString("Intro"))));

}

}
