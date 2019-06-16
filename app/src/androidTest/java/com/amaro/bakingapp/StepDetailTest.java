package com.amaro.bakingapp;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.viewpager.widget.ViewPager;

import com.amaro.bakingapp.model.Step;
import com.amaro.bakingapp.view.StepDetailActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

public class StepDetailTest {

    @Rule
    public ActivityTestRule<StepDetailActivity> activityTestRule =
            new ActivityTestRule<StepDetailActivity>(StepDetailActivity.class, false, false);

    @Before
    public void setUpTest() {
        Intent i = new Intent();
        i.putParcelableArrayListExtra(StepDetailActivity.EXTRA_STEPS, getData());
        this.activityTestRule.launchActivity(i);
    }

    @Test
    public void checkButtonsView_isDisplayed() {
        onView(withId(R.id.bt_next)).check(matches(isDisplayed()));
        onView(withId(R.id.bt_previous)).check(matches(isDisplayed()));
    }

    @Test
    public void checkStepVideoPlayer_isDisplayed() {
        onView(allOf(withId(R.id.player), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void checkStepContent_isDisplayed() {
        onView(allOf(withId(R.id.title_step), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(allOf(withId(R.id.content_step), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void checkPage_swipeLeft() {
        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withId(R.id.pager)).check(matches(inPage(1)));
    }

    @Test
    public void checkButton_swipeLeft() {
        onView(withId(R.id.bt_next)).perform(click());
        onView(withId(R.id.pager)).check(matches(inPage(1)));
    }

    private ArrayList<Step> getData() {
        ArrayList<Step> steps = new ArrayList<Step>();
        for(int i = 0; i < 3; i++) {
            Step step = new Step();
            step.setId(i);
            step.setShortDescription("Short Description "+i);
            step.setDescription("Description "+i);
            step.setVideoURL("https://www.youtube.com/watch?v=iZh4uQYDlsA");

            steps.add(step);
        }
        return steps;
    }

    @NonNull
    public static Matcher<View> inPage(final int page) {

        return new BoundedMatcher<View, ViewPager>(ViewPager.class) {

            @Override
            public void describeTo(final Description description) {
                description.appendText("in page: " + page);
            }

            @Override
            public boolean matchesSafely(final ViewPager viewPager) {
                return viewPager.getCurrentItem() == page;
            }
        };
    }

}
