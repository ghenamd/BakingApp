package com.example.android.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityBasicTest {
    /* Instead of idlingResource we ca use ActivityMonitor*/
    //private IdlingResource mIdlingResource;

    Instrumentation.ActivityMonitor mMonitor =
            getInstrumentation().addMonitor(MainActivity.class.getName(),null,false);
    @Rule
    public ActivityTestRule<MainActivity> mTestRule =
            new ActivityTestRule<>(MainActivity.class);
    @Before
    public void registerIdlingResource(){
        /* Instead of idlingResource we ca use ActivityMonitor*/
//        mIdlingResource = mTestRule.getActivity().getIdlingResource();
//        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void checkIfCorrectStepIsDisplayed() {
        //Find view
        //Perform action
        //Check if it does what we expected
        onView(withId(R.id.recipe_fragment_recyclerView)).perform(RecyclerViewActions.scrollToPosition(1)).
                perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        Activity mainActivity = getInstrumentation().waitForMonitorWithTimeout(mMonitor,3000);
        onView(withId(R.id.ingretients_title_textview)).check(matches(isDisplayed()));
    }
    @After
    public void unregisterIdlingResource(){
        /* Instead of idlingResource we ca use ActivityMonitor*/
//        if (mIdlingResource!=null){
//            IdlingRegistry.getInstance().unregister(mIdlingResource);
//        }
    }
}
