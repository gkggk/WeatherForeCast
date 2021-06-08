package com.example.weatherforecast;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.acs.accelerate.utils.NetworkHelper;
import com.example.weatherforecast.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void checkSnackBar_visibility() {
        if (new NetworkHelper().isNetworkConnectionAvailable()) {
            onView(withId(R.id.snackbar_text))
                    .check(doesNotExist());
        } else {
            onView(withId(R.id.snackbar_text))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void checkSnackBar_visibility_onAction() throws InterruptedException {
        if (!new NetworkHelper().isNetworkConnectionAvailable()) {
            onView(withId(R.id.snackbar_text))
                    .check(matches(isDisplayed()));
            Thread.sleep(300); //adding a delay for the snack bar to finish transition
            onView(withId(R.id.snackbar_action))
                    .perform(click());
            onView(withId(R.id.snackbar_text))
                    .check(doesNotExist());
        }
    }

}
